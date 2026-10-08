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
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public final class GhostFrogLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier TEXTURE = PrettyFrogs.id("textures/entity/frog/ghost.png");
    private static final Identifier GLOW_MASK = PrettyFrogs.id("textures/entity/frog/ghost_eyes_glow.png");
    private final GhostFrogModel model;

    public GhostFrogLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        model = new GhostFrogModel(modelSet.bakeLayer(GhostFrogModel.LAYER_LOCATION));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        if (!access.prettyfrogs$isForm(FrogFormRegistry.GHOST) || state.isInvisible) return;

        poseStack.pushPose();
        poseStack.translate(0.0F, -0.12F + Mth.sin(state.ageInTicks * 0.12F) * 0.018F, 0.0F);
        collector.order(2).submitModel(
                model, state, poseStack, RenderTypes.entityTranslucent(TEXTURE),
                lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                state.outlineColor, null);
        // The glow mask uses the pupils' dedicated UV tile, so the rest of
        // the translucent frog stays naturally shaded.
        collector.order(4).submitModel(
                model, state, poseStack, RenderTypes.eyes(GLOW_MASK),
                0xF000F0, OverlayTexture.NO_OVERLAY,
                0xFFB4FFF3, null, state.outlineColor, null);
        poseStack.popPose();
    }
}
