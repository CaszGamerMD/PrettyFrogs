package com.casz.prettyfrogs.mixin.client;

import com.casz.prettyfrogs.client.FrogPossessionCamera;
import com.casz.prettyfrogs.client.FrogTongueFirstPerson;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.SubmitNodeStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
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
    @Shadow @Final private FeatureRenderDispatcher featureRenderDispatcher;
    @Shadow @Final private SubmitNodeStorage submitNodeStorage;
    @Inject(method = "renderItemInHand", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$hideHumanHandsInFrogPov(
            CameraRenderState camera, float partialTick,
            Matrix4fc modelView, CallbackInfo ci) {
        if (FrogPossessionCamera.isPossessing(Minecraft.getInstance().player)) {
            // The normal pass is cancelled so hands and held items cannot
            // render. Flush preceding world submits, then submit the real
            // vanilla frog tongue model in the 3D first-person hand pass.
            featureRenderDispatcher.renderAllFeatures(submitNodeStorage);
            FrogTongueFirstPerson.submit(partialTick, modelView,
                    submitNodeStorage);
            featureRenderDispatcher.renderAllFeatures(submitNodeStorage);
            ci.cancel();
        }
    }
}
