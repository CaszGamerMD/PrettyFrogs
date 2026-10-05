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

public final class PrettyFrogGlowLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier MAGMA_GLOW = PrettyFrogs.id("textures/entity/frog/magma_glow.png");
    private static final Identifier RAINBOW_GLOW = PrettyFrogs.id("textures/entity/frog/rainbow_glow.png");

    public PrettyFrogGlowLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        RenderType renderType = null;

        if (access.prettyfrogs$isForm(FrogFormRegistry.MAGMA)) {
            renderType = RenderTypes.eyes(MAGMA_GLOW);
        } else if (access.prettyfrogs$isForm(FrogFormRegistry.RAINBOW)) {
            renderType = RenderTypes.eyes(RAINBOW_GLOW);
        }

        if (renderType != null && !state.isInvisible) {
            collector.order(1).submitModel(
                    getParentModel(), state, poseStack, renderType,
                    0xF000F0, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        }
    }
}
