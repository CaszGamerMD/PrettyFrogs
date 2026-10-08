package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.PrettyFrogs;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;

/** Tadpole costume = reusable hood + pair of eyes + a tapered swimming tail. */
public final class TadpoleCostumeModel extends BasicFrogHoodModel {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(PrettyFrogs.id("tadpole_costume"), "main");

    public TadpoleCostumeModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return createLayer(true);
    }

    static void addTadpoleDetails(PartDefinition head, PartDefinition body) {
        // Tadpole eyes belong to the hood and move with frog head animation.
        addHoodEye(head, "tadpole_left_eye", -2.75F);
        addHoodEye(head, "tadpole_right_eye", 2.75F);

        // Tail starts inside the back of the costume and gets narrower in
        // overlapping segments, avoiding disjointed pieces while hopping.
        body.addOrReplaceChild(
                "tadpole_tail",
                CubeListBuilder.create()
                        .texOffs(0, 96).addBox(-1.55F, -0.70F, 0.20F, 3.1F, 1.45F, 2.85F)
                        .texOffs(32, 96).addBox(-1.08F, -0.48F, 2.72F, 2.16F, 1.05F, 2.65F)
                        .texOffs(64, 96).addBox(-0.62F, -0.32F, 5.10F, 1.24F, 0.75F, 2.25F)
                        .texOffs(96, 96).addBox(-0.27F, -0.15F, 7.00F, 0.54F, 0.42F, 1.65F)
                        // Flat fin running along the back portion of the tail.
                        .texOffs(96, 64).addBox(-0.19F, -1.05F, 3.55F, 0.38F, 2.05F, 3.30F),
                PartPose.ZERO);
    }

    private static void addHoodEye(PartDefinition head, String name, float x) {
        head.addOrReplaceChild(
                name,
                CubeListBuilder.create()
                        // Tadpole eye bump, black front and tiny pale glint.
                        .texOffs(0, 96).addBox(-1.25F, -1.00F, -1.05F, 2.50F, 1.5F, 2.05F)
                        .texOffs(64, 0).addBox(-0.80F, -0.53F, -1.11F, 1.60F, 0.79F, 0.18F)
                        .texOffs(96, 0).addBox(-0.51F, -0.38F, -1.15F, 0.34F, 0.26F, 0.10F),
                PartPose.offset(x, -4.70F, -4.90F));
    }
}
