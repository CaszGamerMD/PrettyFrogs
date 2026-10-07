package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.PrettyFrogs;
import com.casz.prettyfrogs.frog.FrogFormRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
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
    private static final Identifier FALLBACK_MASK = PrettyFrogs.id("textures/entity/frog/crystal_overlay.png");

    public CrystalFrogLayer(RenderLayerParent<FrogRenderState, FrogModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       FrogRenderState state, float yRot, float xRot) {
        PrettyFrogRenderStateAccess access = (PrettyFrogRenderStateAccess) state;
        if (!access.prettyfrogs$isForm(FrogFormRegistry.CRYSTAL) || state.isInvisible) return;

        Identifier crystal = access.prettyfrogs$getCrystal();
        Identifier source = findSourceTexture(crystal);
        if (source != null) {
            collector.order(2).submitModel(
                    getParentModel(), state, poseStack, RenderTypes.entityTranslucent(source),
                    lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                    0xF2FFFFFF, null, state.outlineColor, null);
            return;
        }

        int rgb = CrystalFrogColors.color(crystal);
        int color = ARGB.color(220, (rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255);
        collector.order(2).submitModel(
                getParentModel(), state, poseStack, RenderTypes.entityTranslucent(FALLBACK_MASK),
                lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                color, null, state.outlineColor, null);
    }

    private static Identifier findSourceTexture(Identifier crystal) {
        var resources = Minecraft.getInstance().getResourceManager();

        Identifier exact = Identifier.fromNamespaceAndPath(
                crystal.getNamespace(), "textures/block/" + crystal.getPath() + ".png");
        if (resources.getResource(exact).isPresent()) return exact;

        String path = crystal.getPath();
        if (path.endsWith("_crystal_cluster")) {
            Identifier block = Identifier.fromNamespaceAndPath(
                    crystal.getNamespace(),
                    "textures/block/" + path.substring(0, path.length() - "_crystal_cluster".length()) + "_crystal_block.png");
            if (resources.getResource(block).isPresent()) return block;
        }
        if (path.endsWith("_cluster")) {
            Identifier block = Identifier.fromNamespaceAndPath(
                    crystal.getNamespace(),
                    "textures/block/" + path.substring(0, path.length() - "_cluster".length()) + "_block.png");
            if (resources.getResource(block).isPresent()) return block;
        }
        return null;
    }
}
