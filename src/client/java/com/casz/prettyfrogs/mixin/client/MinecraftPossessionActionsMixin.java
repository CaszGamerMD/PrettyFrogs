package com.casz.prettyfrogs.mixin.client;

import com.casz.prettyfrogs.client.FrogControllerKeys;
import com.casz.prettyfrogs.client.FrogPossessionCamera;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Redirect standard mouse attack/use to the controlled frog.
 * These hooks also stop vanilla mining, melee, block use, and held-item use
 * during possession, without affecting normal play outside frog control.
 */
@Mixin(Minecraft.class)
public abstract class MinecraftPossessionActionsMixin {
    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$tongueOnAttack(CallbackInfoReturnable<Boolean> cir) {
        Minecraft client = (Minecraft)(Object)this;
        if (FrogPossessionCamera.isPossessing(client.player)) {
            FrogControllerKeys.tongue();
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "continueAttack", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$stopMiningWhilePossessing(boolean attacking, CallbackInfo ci) {
        Minecraft client = (Minecraft)(Object)this;
        if (FrogPossessionCamera.isPossessing(client.player)) {
            ci.cancel();
        }
    }

    @Inject(method = "startUseItem", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$croakOrReturnOnUse(CallbackInfo ci) {
        Minecraft client = (Minecraft)(Object)this;
        if (FrogPossessionCamera.isPossessing(client.player)) {
            FrogControllerKeys.croakOrExit();
            ci.cancel();
        }
    }
}
