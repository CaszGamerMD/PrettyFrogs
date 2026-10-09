package com.casz.prettyfrogs.mixin;

import com.casz.prettyfrogs.PrettyFrogsItems;
import com.casz.prettyfrogs.control.FrogControlAccess;
import java.util.Comparator;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.cubemob.AbstractCubeMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Lets one player ride and directly steer a vanilla frog while holding the
 * Frog Controller. Uses vanilla mounted movement and jumping protocol.
 * Croak/tongue are validated on the server; the tongue never trusts client
 * target coordinates or entity IDs.
 */
@Mixin(Frog.class)
public abstract class FrogControlMixin extends Animal
        implements PlayerRideableJumping, FrogControlAccess {
    @Unique private int prettyfrogs$actionTimer;
    @Unique private int prettyfrogs$attackCooldown;
    @Unique private int prettyfrogs$targetId = -1;
    @Unique private boolean prettyfrogs$tongueActive;
    @Unique private int prettyfrogs$jumpCooldown;

    protected FrogControlMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Unique
    private boolean prettyfrogs$isControlActive() {
        Entity passenger = this.getFirstPassenger();
        // The controller item is required to START possession in the
        // interaction handler, not to keep it. Changing the hotbar slot
        // shouldn't eject the camera or disable controls mid-jump.
        return !this.isBaby() && passenger instanceof Player;
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        Entity passenger = getFirstPassenger();
        if (prettyfrogs$isControlActive() && passenger instanceof Player player) {
            return player;
        }
        return super.getControllingPassenger();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return !this.isVehicle() && !this.isBaby()
                && passenger instanceof Player player
                && (player.getMainHandItem().is(PrettyFrogsItems.FROG_CONTROLLER)
                    || player.getOffhandItem().is(PrettyFrogsItems.FROG_CONTROLLER));
    }

    @Override
    protected Vec3 getRiddenInput(Player controller, Vec3 selfInput) {
        if (!prettyfrogs$isControlActive()) return selfInput;
        float forward = controller.zza;
        float sideways = controller.xxa * 0.75F;
        if (forward < 0.0F) forward *= 0.5F;
        return new Vec3(sideways, 0.0, forward);
    }

    @Override
    protected float getRiddenSpeed(Player controller) {
        return prettyfrogs$isControlActive()
                ? (this.isInWater() ? 0.20F : 0.27F)
                : (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    @Override
    protected void tickRidden(Player controller, Vec3 riddenInput) {
        if (!prettyfrogs$isControlActive()) return;
        this.getNavigation().stop();
        this.setRot(controller.getYRot(), controller.getXRot());
        this.yRotO = this.yBodyRot = this.yHeadRot = getYRot();
    }

    @Override
    public boolean canJump() {
        return prettyfrogs$isControlActive() && this.isAlive();
    }

    @Unique
    private void prettyfrogs$jump(int charge) {
        if (charge <= 0 || !canJump() || !this.onGround() || prettyfrogs$jumpCooldown > 0) {
            return;
        }
        double strength = 0.56 + Math.min(100, charge) * 0.0024;
        Vec3 current = getDeltaMovement();
        setDeltaMovement(current.x, Math.max(strength, current.y), current.z);
        this.needsSync = true;
        prettyfrogs$jumpCooldown = 10;
        Frog frog = (Frog)(Object)this;
        frog.setPose(Pose.LONG_JUMPING);
    }

    @Override
    public void onPlayerJump(int charge) {
        // The local controlled vehicle predicts the jump for responsiveness.
        if (level().isClientSide()) prettyfrogs$jump(charge);
    }

    @Override
    public void handleStartJump(int charge) {
        // Called by the vanilla mounted-jump packet on the authoritative server.
        if (!level().isClientSide()) prettyfrogs$jump(charge);
    }

    @Override
    public void handleStopJump() { }

    @Inject(method = "customServerAiStep", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$disableFreeRoamingWhileControlled(ServerLevel level, CallbackInfo ci) {
        if (prettyfrogs$isControlActive()) {
            this.getNavigation().stop();
            ci.cancel();
        }
    }

    @Override
    public void prettyfrogs$controlledCroak() {
        if (level().isClientSide() || !prettyfrogs$isControlActive() || prettyfrogs$actionTimer != 0) return;
        prettyfrogs$actionTimer = 16;
        prettyfrogs$tongueActive = false;
        this.setPose(Pose.CROAKING);
        level().playSound(null, blockPosition(), SoundEvents.FROG_AMBIENT, SoundSource.NEUTRAL, 0.85F, 1.0F);
    }

    @Override
    public void prettyfrogs$controlledTongue() {
        if (!(level() instanceof ServerLevel server) || !prettyfrogs$isControlActive()
                || prettyfrogs$actionTimer > 0 || prettyfrogs$attackCooldown > 0) return;

        Frog frog = (Frog)(Object)this;
        Vec3 center = position().add(0.0, 0.45, 0.0);
        Vec3 forward = getLookAngle();
        List<LivingEntity> candidates = server.getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(3.5),
                e -> e.isAlive() && e != frog && !(e instanceof Player) && !e.isPassengerOfSameVehicle(frog)
                        && frog.hasLineOfSight(e));
        LivingEntity best = candidates.stream()
                .filter(e -> e.position().add(0, e.getBbHeight() * 0.5, 0)
                        .subtract(center).lengthSqr() <= 3.5 * 3.5)
                .filter(e -> e.position().add(0, e.getBbHeight() * 0.5, 0)
                        .subtract(center).normalize().dot(forward) > 0.55)
                .min(Comparator.comparingDouble(e -> e.distanceToSqr(frog)))
                .orElse(null);

        prettyfrogs$targetId = best == null ? -1 : best.getId();
        prettyfrogs$actionTimer = 11;
        prettyfrogs$attackCooldown = 16;
        prettyfrogs$tongueActive = true;
        if (best != null) frog.setTongueTarget(best);
        frog.setPose(Pose.USING_TONGUE);
        server.playSound(null, blockPosition(), SoundEvents.FROG_TONGUE, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void prettyfrogs$finishControlledActions(CallbackInfo ci) {
        if (level().isClientSide()) return;
        if (prettyfrogs$jumpCooldown > 0) prettyfrogs$jumpCooldown--;
        if (prettyfrogs$attackCooldown > 0) prettyfrogs$attackCooldown--;
        Frog frog = (Frog)(Object)this;
        if (prettyfrogs$jumpCooldown == 0 && frog.getPose() == Pose.LONG_JUMPING && frog.onGround()) {
            frog.setPose(Pose.STANDING);
        }
        if (prettyfrogs$actionTimer <= 0) return;
        prettyfrogs$actionTimer--;

        if (prettyfrogs$tongueActive && prettyfrogs$actionTimer == 5
                && level() instanceof ServerLevel server) {
            Entity e = server.getEntity(prettyfrogs$targetId);
            if (e instanceof LivingEntity target && target.isAlive()
                    && target.distanceToSqr(frog) <= 3.5 * 3.5
                    && frog.hasLineOfSight(target) && prettyfrogs$isControlActive()) {
                if (target instanceof AbstractCubeMob cube && cube.getSize() == 1
                        && Frog.canEat(target)) {
                    frog.doHurtTarget(server, target);
                    frog.playEatingSound();
                    if (!target.isAlive()) target.remove(Entity.RemovalReason.KILLED);
                } else {
                    target.hurtServer(server, damageSources().mobAttack(frog), 2.0F);
                }
            }
        }
        if (prettyfrogs$actionTimer == 0) {
            if (prettyfrogs$tongueActive) frog.eraseTongueTarget();
            prettyfrogs$tongueActive = false;
            prettyfrogs$targetId = -1;
            if (frog.getPose() == Pose.CROAKING || frog.getPose() == Pose.USING_TONGUE) {
                frog.setPose(Pose.STANDING);
            }
        }
    }
}
