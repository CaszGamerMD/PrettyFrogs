package com.casz.prettyfrogs.mixin.client;

import com.casz.prettyfrogs.client.FrogPossessionCamera;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Frog-eye POV should not display human arms, armor or held items. */
@Mixin(ItemInHandRenderer.class)
public abstract class FrogPossessionHandsMixin {
    @Inject(method = "renderHandsWithItems", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$hideHumanHands(float frameInterp, PoseStack poseStack,
            SubmitNodeCollector collector, LocalPlayer player, int lightCoords, CallbackInfo ci) {
        if (FrogPossessionCamera.isPossessing(player)) {
            ci.cancel();
        }
    }
}
