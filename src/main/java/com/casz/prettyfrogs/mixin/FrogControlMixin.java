package com.casz.prettyfrogs.mixin;

import com.casz.prettyfrogs.PrettyFrogsItems;
import com.casz.prettyfrogs.control.FrogControlAccess;
import java.util.Comparator;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LongJumpUtil;
import net.minecraft.util.Mth;
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
 * Controls a vanilla frog's movement while internally preserving its rider
 * for synchronization, but uses a single-hop packet instead of a mount jump.
 * Croak/tongue are validated on the server; the tongue never trusts client
 * target coordinates or entity IDs.
 */
@Mixin(Frog.class)
public abstract class FrogControlMixin extends Animal
        implements FrogControlAccess {
    @Unique private int prettyfrogs$actionTimer;
    @Unique private int prettyfrogs$croakPoseTicks;
    @Unique private int prettyfrogs$attackCooldown;
    @Unique private int prettyfrogs$targetId = -1;
    @Unique private boolean prettyfrogs$tongueActive;
    @Unique private int prettyfrogs$jumpCooldown;
    @Unique private boolean prettyfrogs$inLongJump;
    @Unique private boolean prettyfrogs$clientSwimUp;
    @Unique private boolean prettyfrogs$clientSwimDown;

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
        float sideways = controller.xxa * 0.85F;
        if (forward < 0.0F) forward *= 0.5F;

        if (this.isInWater()) {
            // The vanilla ridden input used Y=0, preventing the frog from
            // swimming vertically. Reuse Frog.travelInWater's genuine water
            // physics, with forward pitch + jump/shift to steer in 3D.
            boolean up = prettyfrogs$clientSwimUp;
            boolean down = prettyfrogs$clientSwimDown;
            if (controller instanceof ServerPlayer serverPlayer) {
                // Native riding inputs already sync to the server; no extra
                // C2S packet or client-trusted motion is necessary.
                up = serverPlayer.getLastClientInput().jump();
                down = serverPlayer.getLastClientInput().shift();
            }
            double pitch = Math.sin(Math.toRadians(controller.getXRot()));
            double y = -pitch * Math.max(0.0F, forward) * 0.95
                    + (up ? 0.9 : 0.0) - (down ? 0.9 : 0.0);
            return new Vec3(sideways, Mth.clamp(y, -1.0, 1.0), forward);
        }

        // A real frog leap is ballistic; stop rider input fighting the jump.
        if (prettyfrogs$inLongJump) return Vec3.ZERO;
        return new Vec3(sideways, 0.0, forward);
    }

    @Override
    public void prettyfrogs$setSwimInputs(boolean up, boolean down) {
        prettyfrogs$clientSwimUp = up;
        prettyfrogs$clientSwimDown = down;
    }

    @Override
    protected float getRiddenSpeed(Player controller) {
        return prettyfrogs$isControlActive()
                ? (this.isInWater() ? 0.095F : 0.115F)
                : (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    @Override
    protected void tickRidden(Player controller, Vec3 riddenInput) {
        if (!prettyfrogs$isControlActive()) return;
        this.getNavigation().stop();
        this.setRot(controller.getYRot(), controller.getXRot());
        this.yRotO = this.yBodyRot = this.yHeadRot = getYRot();
    }

    /**
     * Use the same collision-aware projectile solver as FrogAi's
     * LongJumpToRandomPos, directed by the player instead of random AI.
     * This is a proper FORWARD frog leap rather than player jumpFromGround.
     * In water, Space is continuous swim-up, not a jump.
     */
    @Override
    public void prettyfrogs$controlledHop() {
        if (!prettyfrogs$isControlActive() || !this.isAlive()
                || this.isInWater() || !this.onGround()
                || prettyfrogs$inLongJump || prettyfrogs$jumpCooldown > 0) {
            return;
        }
        Frog frog = (Frog)(Object)this;
        Player controller = (Player)this.getFirstPassenger();
        float yaw = controller.getYRot() * (float)(Math.PI / 180.0);
        Vec3 facing = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
        Vec3 strafe = new Vec3(facing.z, 0.0, -facing.x);
        double forward = controller.zza;
        double side = controller.xxa * 0.75;
        Vec3 direction = facing.scale(forward).add(strafe.scale(side));
        if (direction.lengthSqr() < 0.001) direction = facing;
        direction = direction.normalize();
        // ~2.5-block horizontal leap, with the actual frog animation, arc,
        // landing resistance and sounds. The vanilla solver checks obstacles.
        Vec3 target = frog.position().add(direction.scale(2.65));
        float maxVelocity = (float)(this.getAttributeValue(Attributes.JUMP_STRENGTH) * 3.5714288F);
        var launch = LongJumpUtil.calculateJumpVectorForAngle(frog, target, maxVelocity, 45, true);
        if (launch.isEmpty()) {
            // No clear frog-sized arc: don't clip through low ceilings.
            return;
        }
        this.setDeltaMovement(launch.get());
        this.setDiscardFriction(true);
        this.needsSync = true;
        prettyfrogs$inLongJump = true;
        prettyfrogs$jumpCooldown = 12;
        frog.setPose(Pose.LONG_JUMPING);
        if (!level().isClientSide()) {
            level().playSound(null, frog, SoundEvents.FROG_LONG_JUMP,
                    SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
    }

    @Inject(method = "customServerAiStep", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$disableFreeRoamingWhileControlled(ServerLevel level, CallbackInfo ci) {
        if (prettyfrogs$isControlActive()) {
            this.getNavigation().stop();
            ci.cancel();
        }
    }

    @Override
    public void prettyfrogs$controlledCroak() {
        if (level().isClientSide() || !prettyfrogs$isControlActive()) return;
        // Croaking has NO tongue/jump cooldown. Each click or held-use pulse
        // makes an audible sound, even if a tongue attack is in progress.
        if (!prettyfrogs$tongueActive) {
            prettyfrogs$croakPoseTicks = 7;
            this.setPose(Pose.CROAKING);
        }
        level().playSound(null, this.getX(), this.getY() + 0.35D, this.getZ(),
                SoundEvents.FROG_AMBIENT, SoundSource.PLAYERS, 2.3F, 1.0F);
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
                e -> e.isAlive() && e != frog && !e.isPassengerOfSameVehicle(frog)
                        && frog.hasLineOfSight(e)
                        // A possessed frog can attack another player, but not
                        // its controller, spectators, creative-invulnerable players,
                        // teammates protected by friendly-fire rules or players
                        // when server PvP is disabled.
                        && (!(e instanceof Player victim)
                            || (getFirstPassenger() instanceof ServerPlayer controller
                                && controller != victim
                                && !victim.isSpectator()
                                && !victim.getAbilities().invulnerable
                                && controller.canHarmPlayer(victim))));
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
        prettyfrogs$croakPoseTicks = 0;
        if (best != null) frog.setTongueTarget(best);
        frog.setPose(Pose.USING_TONGUE);
        server.playSound(null, blockPosition(), SoundEvents.FROG_TONGUE, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    @Unique
    private void prettyfrogs$finishJumpPose() {
        Frog frog = (Frog)(Object)this;
        if (prettyfrogs$inLongJump && (frog.onGround() || frog.isInWater())) {
            prettyfrogs$inLongJump = false;
            this.setDiscardFriction(false);
            if (frog.onGround()) {
                Vec3 movement = frog.getDeltaMovement();
                frog.setDeltaMovement(movement.x * 0.1, movement.y, movement.z * 0.1);
                if (!level().isClientSide()) {
                    level().playSound(null, frog, SoundEvents.FROG_STEP,
                            SoundSource.NEUTRAL, 1.0F, 1.0F);
                }
            }
            if (frog.getPose() == Pose.LONG_JUMPING) frog.setPose(Pose.STANDING);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void prettyfrogs$finishControlledActions(CallbackInfo ci) {
        if (prettyfrogs$jumpCooldown > 0) prettyfrogs$jumpCooldown--;
        if (level().isClientSide()) {
            prettyfrogs$finishJumpPose();
            return;
        }
        if (prettyfrogs$attackCooldown > 0) prettyfrogs$attackCooldown--;
        Frog frog = (Frog)(Object)this;
        if (prettyfrogs$croakPoseTicks > 0 && --prettyfrogs$croakPoseTicks == 0
                && frog.getPose() == Pose.CROAKING) {
            frog.setPose(Pose.STANDING);
        }
        prettyfrogs$finishJumpPose();
        if (prettyfrogs$actionTimer <= 0) return;
        prettyfrogs$actionTimer--;

        if (prettyfrogs$tongueActive && prettyfrogs$actionTimer == 5
                && level() instanceof ServerLevel server) {
            Entity e = server.getEntity(prettyfrogs$targetId);
            if (e instanceof LivingEntity target && target.isAlive()
                    && target.distanceToSqr(frog) <= 3.5 * 3.5
                    && frog.hasLineOfSight(target) && prettyfrogs$isControlActive()) {
                if (target instanceof Player victim) {
                    // Revalidate PvP authorization when the tongue actually
                    // connects; the rules or riding state might have changed
                    // since target selection five ticks earlier. Attribution to
                    // the controlling player also lets vanilla PvP checks apply.
                    if (getFirstPassenger() instanceof ServerPlayer controller
                            && controller != victim
                            && !victim.isSpectator()
                            && !victim.getAbilities().invulnerable
                            && controller.canHarmPlayer(victim)) {
                        victim.hurtServer(server, damageSources().playerAttack(controller), 2.0F);
                    }
                } else if (target instanceof AbstractCubeMob cube && cube.getSize() == 1
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
