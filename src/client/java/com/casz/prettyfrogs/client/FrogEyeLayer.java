package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.PrettyFrogs;
import com.casz.prettyfrogs.frog.FrogFormRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.frog.FrogModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.FrogRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

public final class FrogEyeLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier EYES = PrettyFrogs.id("textures/entity/frog/frog_eyes.png");
    private static final Identifier RGB_EYES = PrettyFrogs.id("textures/entity/frog/frog_eyes_rgb.png");
    private final FrogEyeModel model;

    public FrogEyeLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        model = new FrogEyeModel(modelSet.bakeLayer(FrogEyeModel.LAYER_LOCATION));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        Identifier form = access.prettyfrogs$getForm();
        // Most frogs now use the eyes painted directly on their own 48x48
        // textures; the generic eye overlay was covering that custom artwork.
        // Retain only the intentionally animated RGB-eyes form and Sculk's
        // dedicated bright-teal glowing eye effect.
        boolean rgbEyes = form.equals(FrogFormRegistry.RAINBOW_EYES);
        boolean sculkEyes = form.equals(FrogFormRegistry.SCULK);
        if (state.isInvisible || (!rgbEyes && !sculkEyes)) {
            return;
        }

        Identifier texture = rgbEyes ? RGB_EYES : EYES;
        int color = -1;
        RenderType type = RenderTypes.entityCutout(texture);
        int light = lightCoords;
        int overlay = LivingEntityRenderer.getOverlayCoords(state, 0.0F);

        if (rgbEyes) {
            float hue = (state.ageInTicks * 0.018F) % 1.0F;
            color = ARGB.opaque(Mth.hsvToRgb(hue, 0.9F, 1.0F));
            type = RenderTypes.eyes(texture);
            light = 0xF000F0;
            overlay = OverlayTexture.NO_OVERLAY;
        } else if (sculkEyes) {
            color = 0xFF39F6E8;
            type = RenderTypes.eyes(texture);
            light = 0xF000F0;
            overlay = OverlayTexture.NO_OVERLAY;
        }

        collector.order(5).submitModel(
                model, state, poseStack, type,
                light, overlay, color, null, state.outlineColor, null);
    }
}
