package com.casz.prettyfrogs.mixin.client;

import com.casz.prettyfrogs.client.FrogPossessionCamera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Suppress human hand / held-item rendering only while viewing from a
 * possessed frog. Hook GameRenderer's hand render pass rather than the
 * version-sensitive ItemInHandRenderer method that failed at launch.
 */
@Mixin(GameRenderer.class)
public abstract class GameRendererPossessionHandsMixin {
    @Inject(method = "renderItemInHand", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$hideHumanHandsInFrogPov(
            CameraRenderState camera, float partialTick,
            Matrix4fc modelView, CallbackInfo ci) {
        if (FrogPossessionCamera.isPossessing(Minecraft.getInstance().player)) {
            ci.cancel();
        }
    }
}
