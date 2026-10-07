package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.PrettyFrogs;
import net.minecraft.client.model.animal.frog.FrogModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class GhostFrogModel extends FrogModel {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(PrettyFrogs.id("ghost_frog"), "main");

    public GhostFrogModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition modelRoot = root.addOrReplaceChild(
                "root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = modelRoot.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(3, 1).addBox(-3.5F, -2.0F, -8.0F, 7.0F, 3.0F, 9.0F)
                        .texOffs(23, 22).addBox(-2.7F, 0.7F, -5.5F, 5.4F, 1.7F, 6.2F)
                        .texOffs(23, 22).addBox(-1.8F, 2.1F, -4.4F, 3.6F, 1.8F, 4.6F),
                PartPose.offset(0.0F, -2.0F, 4.0F));

        PartDefinition head = body.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(0, 13).addBox(-3.5F, -2.0F, -7.0F, 7.0F, 3.0F, 9.0F),
                PartPose.offset(0.0F, -2.0F, -1.0F));

        PartDefinition eyes = head.addOrReplaceChild(
                "eyes", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, 2.0F));
        eyes.addOrReplaceChild("right_eye", CubeListBuilder.create(), PartPose.ZERO);
        eyes.addOrReplaceChild("left_eye", CubeListBuilder.create(), PartPose.ZERO);

        body.addOrReplaceChild(
                "croaking_body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -5.0F));
        body.addOrReplaceChild(
                "tongue", CubeListBuilder.create(), PartPose.offset(0.0F, -1.01F, 1.0F));

        PartDefinition leftArm = body.addOrReplaceChild(
                "left_arm",
                CubeListBuilder.create().texOffs(0, 32).addBox(-0.8F, 0.0F, -0.8F, 1.6F, 2.4F, 2.2F),
                PartPose.offset(4.0F, -1.0F, -6.5F));
        leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition rightArm = body.addOrReplaceChild(
                "right_arm",
                CubeListBuilder.create().texOffs(0, 38).addBox(-0.8F, 0.0F, -0.8F, 1.6F, 2.4F, 2.2F),
                PartPose.offset(-4.0F, -1.0F, -6.5F));
        rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.ZERO);

        // Required animation nodes, intentionally without geometry: the ghost has no legs.
        PartDefinition leftLeg = modelRoot.addOrReplaceChild(
                "left_leg", CubeListBuilder.create(), PartPose.offset(3.5F, -3.0F, 4.0F));
        leftLeg.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition rightLeg = modelRoot.addOrReplaceChild(
                "right_leg", CubeListBuilder.create(), PartPose.offset(-3.5F, -3.0F, 4.0F));
        rightLeg.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(mesh, 48, 48);
    }
}
