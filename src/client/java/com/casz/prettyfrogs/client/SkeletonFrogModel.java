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
        PartDefinition modelRoot = root.addOrReplaceChild(
                "root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        // Keep the exact 26.2 frog animation hierarchy/pivots, but replace the
        // normal flesh geometry with a real skeletal silhouette.
        PartDefinition body = modelRoot.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-2.75F, -1.45F, -7.2F, 5.5F, 0.55F, 0.75F)
                        .texOffs(0, 18).addBox(-2.75F, -1.45F, -5.2F, 5.5F, 0.55F, 0.75F)
                        .texOffs(0, 20).addBox(-2.75F, -1.45F, -3.2F, 5.5F, 0.55F, 0.75F)
                        .texOffs(0, 22).addBox(-0.45F, -1.8F, -7.5F, 0.9F, 1.2F, 8.3F)
                        .texOffs(18, 16).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 0.65F, 1.5F),
                PartPose.offset(0.0F, -2.0F, 4.0F));

        PartDefinition head = body.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.0F, -2.0F, -6.4F, 6.0F, 2.2F, 5.8F)
                        .texOffs(24, 8).addBox(-2.5F, 0.15F, -6.0F, 5.0F, 0.7F, 1.5F),
                PartPose.offset(0.0F, -2.0F, -1.0F));

        PartDefinition eyes = head.addOrReplaceChild(
                "eyes", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, 2.0F));
        eyes.addOrReplaceChild(
                "right_eye",
                CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -0.45F, -0.85F, 2.0F, 0.9F, 1.7F),
                PartPose.offset(-1.5F, -2.9F, -6.5F));
        eyes.addOrReplaceChild(
                "left_eye",
                CubeListBuilder.create().texOffs(32, 4).addBox(-1.0F, -0.45F, -0.85F, 2.0F, 0.9F, 1.7F),
                PartPose.offset(2.5F, -2.9F, -6.5F));

        body.addOrReplaceChild(
                "croaking_body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -5.0F));
        body.addOrReplaceChild(
                "tongue", CubeListBuilder.create(), PartPose.offset(0.0F, -1.01F, 1.0F));

        PartDefinition leftArm = body.addOrReplaceChild(
                "left_arm",
                CubeListBuilder.create().texOffs(20, 16).addBox(-0.4F, 0.0F, -0.4F, 0.8F, 3.0F, 0.8F),
                PartPose.offset(4.0F, -1.0F, -6.5F));
        leftArm.addOrReplaceChild(
                "left_hand",
                CubeListBuilder.create()
                        .texOffs(20, 21).addBox(-2.5F, 0.0F, -0.35F, 5.0F, 0.45F, 0.7F)
                        .texOffs(20, 23).addBox(-2.2F, 0.0F, -1.2F, 0.45F, 0.4F, 2.4F)
                        .texOffs(22, 23).addBox(1.75F, 0.0F, -1.2F, 0.45F, 0.4F, 2.4F),
                PartPose.offset(0.0F, 3.0F, -1.0F));

        PartDefinition rightArm = body.addOrReplaceChild(
                "right_arm",
                CubeListBuilder.create().texOffs(24, 16).addBox(-0.4F, 0.0F, -0.4F, 0.8F, 3.0F, 0.8F),
                PartPose.offset(-4.0F, -1.0F, -6.5F));
        rightArm.addOrReplaceChild(
                "right_hand",
                CubeListBuilder.create()
                        .texOffs(20, 26).addBox(-2.5F, 0.0F, -0.35F, 5.0F, 0.45F, 0.7F)
                        .texOffs(20, 28).addBox(-2.2F, 0.0F, -1.2F, 0.45F, 0.4F, 2.4F)
                        .texOffs(22, 28).addBox(1.75F, 0.0F, -1.2F, 0.45F, 0.4F, 2.4F),
                PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition leftLeg = modelRoot.addOrReplaceChild(
                "left_leg",
                CubeListBuilder.create().texOffs(30, 8).addBox(-0.45F, 0.0F, -0.7F, 0.9F, 3.0F, 1.4F),
                PartPose.offset(3.5F, -3.0F, 4.0F));
        leftLeg.addOrReplaceChild(
                "left_foot",
                CubeListBuilder.create()
                        .texOffs(0, 35).addBox(-2.6F, 0.0F, -0.35F, 5.2F, 0.45F, 0.7F)
                        .texOffs(0, 37).addBox(1.8F, 0.0F, -1.3F, 0.45F, 0.4F, 2.6F),
                PartPose.offset(2.0F, 3.0F, 0.0F));

        PartDefinition rightLeg = modelRoot.addOrReplaceChild(
                "right_leg",
                CubeListBuilder.create().texOffs(38, 8).addBox(-0.45F, 0.0F, -0.7F, 0.9F, 3.0F, 1.4F),
                PartPose.offset(-3.5F, -3.0F, 4.0F));
        rightLeg.addOrReplaceChild(
                "right_foot",
                CubeListBuilder.create()
                        .texOffs(20, 35).addBox(-2.6F, 0.0F, -0.35F, 5.2F, 0.45F, 0.7F)
                        .texOffs(20, 37).addBox(-2.25F, 0.0F, -1.3F, 0.45F, 0.4F, 2.6F),
                PartPose.offset(-2.0F, 3.0F, 0.0F));

        return LayerDefinition.create(mesh, 48, 48);
    }
}
