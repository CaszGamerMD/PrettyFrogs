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

/** Draws only hood and tail over the original living frog. */
public final class TadpoleCostumeLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier TEXTURE =
            PrettyFrogs.id("textures/entity/frog/tadpole_costume.png");
    private final TadpoleCostumeModel model;

    public TadpoleCostumeLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer,
                              EntityModelSet modelSet) {
        super(renderer);
        model = new TadpoleCostumeModel(modelSet.bakeLayer(TadpoleCostumeModel.LAYER_LOCATION));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        if (state.isInvisible || !access.prettyfrogs$isForm(FrogFormRegistry.TADPOLE_COSTUME)) {
            return;
        }
        collector.order(3).submitModel(
                model, state, poseStack, RenderTypes.entityCutout(TEXTURE),
                lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                state.outlineColor, null);
    }
}
