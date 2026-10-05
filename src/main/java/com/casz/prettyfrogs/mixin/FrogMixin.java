package com.casz.prettyfrogs.mixin;

import com.casz.prettyfrogs.frog.FrogFormRegistry;
import com.casz.prettyfrogs.frog.PrettyFrogAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Frog.class)
public abstract class FrogMixin implements PrettyFrogAccess {
    @Unique
    private static final String PRETTYFROGS_SAVE_KEY = "PrettyFrogsForm";

    @Unique
    private static final EntityDataAccessor<String> PRETTYFROGS_FORM =
            SynchedEntityData.defineId(Frog.class, EntityDataSerializers.STRING);

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void prettyfrogs$defineFormData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(PRETTYFROGS_FORM, FrogFormRegistry.NORMAL.toString());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void prettyfrogs$elementalParticles(CallbackInfo ci) {
        Frog frog = (Frog)(Object)this;
        if (!frog.level().isClientSide()) {
            return;
        }

        Identifier form = prettyfrogs$getForm();
        var random = frog.getRandom();
        double x = frog.getX() + (random.nextDouble() - 0.5) * frog.getBbWidth();
        double y = frog.getY() + random.nextDouble() * frog.getBbHeight();
        double z = frog.getZ() + (random.nextDouble() - 0.5) * frog.getBbWidth();

        if (form.equals(FrogFormRegistry.MAGMA) && random.nextInt(3) == 0) {
            frog.level().addParticle(ParticleTypes.SMALL_FLAME, x, y, z, 0.0, 0.01, 0.0);
            if (random.nextInt(4) == 0) {
                frog.level().addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.01, 0.0);
            }
        } else if (form.equals(FrogFormRegistry.WATER) && random.nextInt(6) == 0) {
            frog.level().addParticle(ParticleTypes.DRIPPING_WATER, x, frog.getY() + 0.1, z, 0.0, 0.0, 0.0);
        } else if (form.equals(FrogFormRegistry.ICE) && random.nextInt(5) == 0) {
            frog.level().addParticle(ParticleTypes.SNOWFLAKE, x, y, z, 0.0, 0.005, 0.0);
        } else if (form.equals(FrogFormRegistry.MUDDY)
                && frog.getDeltaMovement().horizontalDistanceSqr() > 0.001
                && frog.onGround()
                && random.nextInt(2) == 0) {
            frog.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.MUD.defaultBlockState()), x, frog.getY() + 0.02, z, 0.0, 0.015, 0.0);
        }
    }

    @Override
    public Identifier prettyfrogs$getForm() {
        Frog frog = (Frog)(Object)this;
        Identifier parsed = Identifier.tryParse(frog.getEntityData().get(PRETTYFROGS_FORM));
        return parsed == null ? FrogFormRegistry.NORMAL : FrogFormRegistry.get(parsed).id();
    }

    @Override
    public void prettyfrogs$setForm(Identifier form) {
        Frog frog = (Frog)(Object)this;
        frog.getEntityData().set(PRETTYFROGS_FORM, FrogFormRegistry.get(form).id().toString());
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void prettyfrogs$saveForm(ValueOutput output, CallbackInfo ci) {
        output.putString(PRETTYFROGS_SAVE_KEY, prettyfrogs$getForm().toString());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void prettyfrogs$loadForm(ValueInput input, CallbackInfo ci) {
        input.getString(PRETTYFROGS_SAVE_KEY)
                .map(Identifier::tryParse)
                .ifPresent(this::prettyfrogs$setForm);
    }
}
