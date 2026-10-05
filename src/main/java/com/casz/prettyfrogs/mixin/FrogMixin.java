package com.casz.prettyfrogs.mixin;

import com.casz.prettyfrogs.frog.FrogFormRegistry;
import com.casz.prettyfrogs.frog.PrettyFrogAccess;
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
