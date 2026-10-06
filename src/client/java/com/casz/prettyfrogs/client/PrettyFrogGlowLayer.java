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
    private static final Identifier GLOW_GLOW = PrettyFrogs.id("textures/entity/frog/glow_glow.png");
    private static final Identifier SOULFIRE_GLOW = PrettyFrogs.id("textures/entity/frog/soulfire_glow.png");
    private static final Identifier STORM_GLOW = PrettyFrogs.id("textures/entity/frog/storm_glow.png");
    private static final Identifier SCULK_GLOW = PrettyFrogs.id("textures/entity/frog/sculk_glow.png");

    public PrettyFrogGlowLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        RenderType renderType = null;
        int color = -1;

        if (access.prettyfrogs$isForm(FrogFormRegistry.MAGMA)) {
            renderType = RenderTypes.eyes(MAGMA_GLOW);
        } else if (access.prettyfrogs$isForm(FrogFormRegistry.GLOW)) {
            renderType = RenderTypes.eyes(GLOW_GLOW);
        } else if (access.prettyfrogs$isForm(FrogFormRegistry.SOULFIRE)) {
            renderType = RenderTypes.eyes(SOULFIRE_GLOW);
        } else if (access.prettyfrogs$isForm(FrogFormRegistry.STORM)) {
            renderType = RenderTypes.eyes(STORM_GLOW);
        } else if (access.prettyfrogs$isForm(FrogFormRegistry.SCULK)) {
            renderType = RenderTypes.eyes(SCULK_GLOW);
        } else if (access.prettyfrogs$isForm(FrogFormRegistry.RAINBOW)) {
            renderType = RenderTypes.eyes(RAINBOW_GLOW);
            float hue = (state.ageInTicks * 0.0125F) % 1.0F;
            color = ARGB.opaque(Mth.hsvToRgb(hue, 0.85F, 1.0F));
        } else if (access.prettyfrogs$isForm(FrogFormRegistry.RAINBOW_DART)
                || access.prettyfrogs$isForm(FrogFormRegistry.RAINBOW_EYES)
                || access.prettyfrogs$isForm(FrogFormRegistry.RAINBOW_SOLID)
                || access.prettyfrogs$isForm(FrogFormRegistry.RAINBOW_DISCO)) {
            // These variants carry their RGB pattern directly in their base texture.
            // Keeping them out of the spotted overlay prevents patterns from stacking.
            renderType = null;
        }

        if (renderType != null && !state.isInvisible) {
            collector.order(1).submitModel(
                    getParentModel(), state, poseStack, renderType,
                    0xF000F0, OverlayTexture.NO_OVERLAY, color, null, state.outlineColor, null);
        }
    }
}
