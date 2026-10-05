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

public final class WaterFrogShellLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier WATER_SHELL = PrettyFrogs.id("textures/entity/frog/water_shell.png");

    public WaterFrogShellLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        if (!access.prettyfrogs$isForm(FrogFormRegistry.WATER) || state.isInvisible) {
            return;
        }

        collector.order(2).submitModel(
                getParentModel(), state, poseStack,
                RenderTypes.entityTranslucent(WATER_SHELL),
                lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                state.outlineColor, null);
    }
}
