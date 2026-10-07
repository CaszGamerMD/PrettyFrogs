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
import net.minecraft.util.Mth;

public final class FrogEyeLayer extends RenderLayer<FrogRenderState, FrogModel> {
    private static final Identifier EYES = PrettyFrogs.id("textures/entity/frog/frog_eyes.png");
    private static final Identifier RGB_EYES = PrettyFrogs.id("textures/entity/frog/frog_eyes_rgb.png");
    private final FrogEyeModel model;

    public FrogEyeLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        model = new FrogEyeModel(modelSet.bakeLayer(FrogEyeModel.LAYER_LOCATION));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        Identifier form = access.prettyfrogs$getForm();
        if (state.isInvisible
                || form.equals(FrogFormRegistry.NORMAL)
                || form.equals(FrogFormRegistry.CRYSTAL)
                || form.equals(FrogFormRegistry.SKELETON)) {
            return;
        }

        Identifier texture = form.equals(FrogFormRegistry.RAINBOW_EYES) ? RGB_EYES : EYES;
        int color = -1;
        if (form.equals(FrogFormRegistry.RAINBOW_EYES)) {
            float hue = (state.ageInTicks * 0.018F) % 1.0F;
            color = ARGB.opaque(Mth.hsvToRgb(hue, 0.9F, 1.0F));
        } else if (form.equals(FrogFormRegistry.RED_EYED_TREE)) {
            color = 0xFFFF4A4A;
        }

        collector.order(5).submitModel(
                model, state, poseStack, RenderTypes.entityCutout(texture),
                lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                color, null, state.outlineColor, null);
    }
}
