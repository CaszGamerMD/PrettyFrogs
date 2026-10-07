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
import net.minecraft.util.ARGB;

/**
 * One shared, animation-compatible 3D-detail layer for optional frog decor.
 * Geometry selection is local to this render layer; it never changes the base
 * frog texture, collision, AI, drops or NBT.
 */
public final class Frog3DDetailsLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier ATLAS = PrettyFrogs.id("textures/entity/frog/frog_3d_details.png");
    private final Frog3DDetailsModel model;

    public Frog3DDetailsLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new Frog3DDetailsModel(modelSet.bakeLayer(Frog3DDetailsModel.LAYER_LOCATION));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        if (state.isInvisible) return;

        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        Frog3DDetailKind kind = Frog3DDetailKind.forForm(access.prettyfrogs$getForm());
        if (kind == Frog3DDetailKind.NONE) return;
        model.setDetail(kind);

        if (kind == Frog3DDetailKind.CRYSTALS) {
            // Match the same source cluster color as the existing crystal overlay.
            int rgb = CrystalFrogColors.color(access.prettyfrogs$getCrystal());
            int tint = ARGB.color(228, (rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255);
            collector.order(3).submitModel(
                    model, state, poseStack, RenderTypes.entityTranslucent(ATLAS),
                    lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                    tint, null, state.outlineColor, null);
        } else {
            collector.order(3).submitModel(
                    model, state, poseStack, RenderTypes.entityCutout(ATLAS),
                    lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                    state.outlineColor, null);
        }
    }
}
