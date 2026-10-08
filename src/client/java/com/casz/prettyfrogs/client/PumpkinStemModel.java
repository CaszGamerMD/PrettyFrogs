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

public final class PumpkinStemModel extends FrogModel {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(PrettyFrogs.id("pumpkin_stem"), "main");

    public PumpkinStemModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition modelRoot = root.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = modelRoot.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 4.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -1.0F));

        // Small blocky stem centered on top of the frog's head.
        head.addOrReplaceChild("stem",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 3.0F, 2.0F),
                PartPose.ZERO);

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, 2.0F));
        eyes.addOrReplaceChild("right_eye", CubeListBuilder.create(), PartPose.ZERO);
        eyes.addOrReplaceChild("left_eye", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("croaking_body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -5.0F));
        body.addOrReplaceChild("tongue", CubeListBuilder.create(), PartPose.offset(0.0F, -1.01F, 1.0F));
        PartDefinition leftArm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
        leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition rightArm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition leftLeg = modelRoot.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
        leftLeg.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition rightLeg = modelRoot.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);
        rightLeg.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(mesh, 16, 16);
    }
}
