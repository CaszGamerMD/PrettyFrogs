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

public final class SkeletonFrogModel extends FrogModel {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(PrettyFrogs.id("skeleton_frog"), "main");

    public SkeletonFrogModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition modelRoot = root.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = modelRoot.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-2.5F, -1.25F, -6.0F, 5.0F, 0.75F, 1.0F)
                        .texOffs(0, 18).addBox(-2.5F, -1.25F, -4.0F, 5.0F, 0.75F, 1.0F)
                        .texOffs(0, 20).addBox(-2.5F, -1.25F, -2.0F, 5.0F, 0.75F, 1.0F)
                        .texOffs(0, 22).addBox(-0.5F, -2.0F, -7.0F, 1.0F, 1.5F, 8.0F),
                PartPose.offset(0.0F, -2.0F, 4.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.0F, -2.0F, -6.5F, 6.0F, 2.0F, 6.0F)
                        .texOffs(24, 8).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, -2.0F, -1.0F));
        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, 2.0F));
        eyes.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F), PartPose.offset(-1.5F, -3.0F, -6.5F));
        eyes.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(26, 4).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F), PartPose.offset(2.5F, -3.0F, -6.5F));

        body.addOrReplaceChild("croaking_body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -5.0F));
        body.addOrReplaceChild("tongue", CubeListBuilder.create(), PartPose.offset(0.0F, -1.01F, 1.0F));

        PartDefinition leftArm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(20, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offset(4.0F, -1.0F, -6.5F));
        leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(20, 21).addBox(-2.5F, 0.0F, -1.5F, 5.0F, 0.5F, 3.0F), PartPose.offset(0.0F, 3.0F, -1.0F));
        PartDefinition rightArm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offset(-4.0F, -1.0F, -6.5F));
        rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(20, 26).addBox(-2.5F, 0.0F, -1.5F, 5.0F, 0.5F, 3.0F), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition leftLeg = modelRoot.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(30, 8).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 3.0F, 3.0F), PartPose.offset(3.5F, -3.0F, 4.0F));
        leftLeg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(0, 35).addBox(-2.5F, 0.0F, -1.5F, 5.0F, 0.5F, 3.0F), PartPose.offset(2.0F, 3.0F, 0.0F));
        PartDefinition rightLeg = modelRoot.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(38, 8).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 3.0F, 3.0F), PartPose.offset(-3.5F, -3.0F, 4.0F));
        rightLeg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(20, 35).addBox(-2.5F, 0.0F, -1.5F, 5.0F, 0.5F, 3.0F), PartPose.offset(-2.0F, 3.0F, 0.0F));

        return LayerDefinition.create(mesh, 48, 48);
    }
}
