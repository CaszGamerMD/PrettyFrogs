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

public final class GhostSlimeFrogLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier GHOST_SHELL = PrettyFrogs.id("textures/entity/frog/ghost_shell.png");
    private static final Identifier SLIME_SHELL = PrettyFrogs.id("textures/entity/frog/slimy_shell.png");

    public GhostSlimeFrogLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        Identifier shell = access.prettyfrogs$isForm(FrogFormRegistry.GHOST) ? GHOST_SHELL
                : access.prettyfrogs$isForm(FrogFormRegistry.SLIMY) ? SLIME_SHELL : null;
        if (shell == null || state.isInvisible) return;

        collector.order(2).submitModel(
                getParentModel(), state, poseStack, RenderTypes.entityTranslucent(shell),
                lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                state.outlineColor, null);
    }
}
