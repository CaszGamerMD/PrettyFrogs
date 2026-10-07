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
import net.minecraft.resources.Identifier;

public final class CakeCandleLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier TEXTURE = PrettyFrogs.id("textures/entity/frog/cake_candles.png");
    private final CakeCandleModel model;

    public CakeCandleLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new CakeCandleModel(modelSet.bakeLayer(CakeCandleModel.LAYER_LOCATION));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        int count = access.prettyfrogs$getCakeCandles();
        if (!access.prettyfrogs$isForm(FrogFormRegistry.CAKE) || count <= 0 || state.isInvisible) {
            return;
        }

        for (int i = 0; i < Math.min(4, count); i++) {
            model.showSingleBody(i);
            int color = 0xFF000000 | (access.prettyfrogs$getCakeCandleColor(i) & 0xFFFFFF);
            collector.order(3).submitModel(
                    model, state, poseStack, RenderTypes.entityCutout(TEXTURE),
                    lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                    color, null, state.outlineColor, null);
        }

        model.showFlames(count);
        collector.order(4).submitModel(
                model, state, poseStack, RenderTypes.entityCutout(TEXTURE),
                0xF000F0, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                state.outlineColor, null);
    }
}
