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
    // Owned by PrettyFrogs: avoids brittle @Shadow fields on GameRenderer.
    private static final SubmitNodeStorage prettyfrogs$tongueNodes = new SubmitNodeStorage();
    @Inject(method = "renderItemInHand", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$hideHumanHandsInFrogPov(
            CameraRenderState camera, float partialTick,
            Matrix4fc modelView, CallbackInfo ci) {
        if (FrogPossessionCamera.isPossessing(Minecraft.getInstance().player)) {
            // Suppress human arms and render only the frog tongue in a
            // standalone collector. The vanilla world pass has already
            // flushed its submits before this first-person hand pass.
            FrogTongueFirstPerson.submit(partialTick, modelView,
                    prettyfrogs$tongueNodes);
            featureRenderDispatcher.renderAllFeatures(prettyfrogs$tongueNodes);
            ci.cancel();
        }
    }
}
