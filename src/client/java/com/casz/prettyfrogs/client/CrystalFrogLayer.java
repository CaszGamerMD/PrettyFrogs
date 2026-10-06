package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.PrettyFrogs;
import com.casz.prettyfrogs.frog.FrogFormRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.frog.FrogModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.FrogRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public final class CrystalFrogLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier MASK = PrettyFrogs.id("textures/entity/frog/crystal_overlay.png");

    public CrystalFrogLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        if (!access.prettyfrogs$isForm(FrogFormRegistry.CRYSTAL) || state.isInvisible) return;

        int rgb = CrystalFrogColors.color(access.prettyfrogs$getCrystal());
        int color = ARGB.color(220, (rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255);
        collector.order(2).submitModel(
                getParentModel(), state, poseStack, RenderTypes.entityTranslucent(MASK),
                lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                color, null, state.outlineColor, null);
    }
}
