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
import net.minecraft.resources.Identifier;

public final class FrogAccessoryLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier BUMBLE_WINGS = PrettyFrogs.id("textures/entity/frog/bumble_wings.png");
    private static final Identifier CACTUS_SPIKES = PrettyFrogs.id("textures/entity/frog/cactus_spikes.png");
    private final FrogAccessoryModel model;

    public FrogAccessoryLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        model = new FrogAccessoryModel(modelSet.bakeLayer(FrogAccessoryModel.LAYER_LOCATION));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        Identifier form = access.prettyfrogs$getForm();
        if (state.isInvisible || (!form.equals(FrogFormRegistry.BUMBLE) && !form.equals(FrogFormRegistry.CACTUS))) {
            return;
        }

        model.setForm(form);
        boolean bumble = form.equals(FrogFormRegistry.BUMBLE);
        Identifier texture = bumble ? BUMBLE_WINGS : CACTUS_SPIKES;
        RenderType type = bumble ? RenderTypes.entityTranslucent(texture) : RenderTypes.entityCutout(texture);

        collector.order(3).submitModel(
                model, state, poseStack, type,
                lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                state.outlineColor, null);
    }
}
