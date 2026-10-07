package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.PrettyFrogs;
import com.casz.prettyfrogs.frog.FrogFormRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.frog.FrogModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.FrogRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

public final class PrettyFrogGlowLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier MAGMA_GLOW = PrettyFrogs.id("textures/entity/frog/magma_glow.png");
    private static final Identifier RAINBOW_GLOW = PrettyFrogs.id("textures/entity/frog/rainbow_glow.png");
    private static final Identifier RAINBOW_DART_GLOW = PrettyFrogs.id("textures/entity/frog/rainbow_dart_glow.png");
    private static final Identifier RAINBOW_SOLID_GLOW = PrettyFrogs.id("textures/entity/frog/rainbow_solid_glow.png");
    private static final Identifier RAINBOW_DISCO_A = PrettyFrogs.id("textures/entity/frog/rainbow_disco_a.png");
    private static final Identifier RAINBOW_DISCO_B = PrettyFrogs.id("textures/entity/frog/rainbow_disco_b.png");
    private static final Identifier GLOW_GLOW = PrettyFrogs.id("textures/entity/frog/glow_glow.png");
    private static final Identifier SOULFIRE_GLOW = PrettyFrogs.id("textures/entity/frog/soulfire_glow.png");
    private static final Identifier STORM_GLOW = PrettyFrogs.id("textures/entity/frog/storm_glow.png");

    public PrettyFrogGlowLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        if (state.isInvisible) return;

        if (access.prettyfrogs$isForm(FrogFormRegistry.RAINBOW)) {
            submitRgb(collector, poseStack, state, RAINBOW_GLOW, state.ageInTicks * 0.0125F, 1);
            return;
        }
        if (access.prettyfrogs$isForm(FrogFormRegistry.RAINBOW_DART)) {
            submitRgb(collector, poseStack, state, RAINBOW_DART_GLOW, state.ageInTicks * 0.014F, 1);
            return;
        }
        if (access.prettyfrogs$isForm(FrogFormRegistry.RAINBOW_SOLID)) {
            submitRgb(collector, poseStack, state, RAINBOW_SOLID_GLOW, state.ageInTicks * 0.011F, 1);
            return;
        }
        if (access.prettyfrogs$isForm(FrogFormRegistry.RAINBOW_DISCO)) {
            submitRgb(collector, poseStack, state, RAINBOW_DISCO_A, state.ageInTicks * 0.019F, 1);
            submitRgb(collector, poseStack, state, RAINBOW_DISCO_B, 0.53F - state.ageInTicks * 0.013F, 2);
            return;
        }
        // RAINBOW_EYES animates only the eyes in FrogEyeLayer.

        RenderType renderType = null;
        if (access.prettyfrogs$isForm(FrogFormRegistry.MAGMA)) {
            renderType = RenderTypes.eyes(MAGMA_GLOW);
        } else if (access.prettyfrogs$isForm(FrogFormRegistry.GLOW)) {
            renderType = RenderTypes.eyes(GLOW_GLOW);
        } else if (access.prettyfrogs$isForm(FrogFormRegistry.SOULFIRE)) {
            renderType = RenderTypes.eyes(SOULFIRE_GLOW);
        } else if (access.prettyfrogs$isForm(FrogFormRegistry.STORM)) {
            renderType = RenderTypes.eyes(STORM_GLOW);
        }

        if (renderType != null) {
            collector.order(1).submitModel(
                    getParentModel(), state, poseStack, renderType,
                    0xF000F0, OverlayTexture.NO_OVERLAY, -1, null, state.outlineColor, null);
        }
    }

    private void submitRgb(SubmitNodeCollector collector, PoseStack poseStack, FrogRenderState state,
                           Identifier mask, float hueValue, int order) {
        float hue = hueValue % 1.0F;
        if (hue < 0.0F) hue += 1.0F;
        int color = ARGB.opaque(Mth.hsvToRgb(hue, 0.9F, 1.0F));
        collector.order(order).submitModel(
                getParentModel(), state, poseStack, RenderTypes.eyes(mask),
                0xF000F0, OverlayTexture.NO_OVERLAY, color, null, state.outlineColor, null);
    }
}
