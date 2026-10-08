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

/**
 * Reusable frog-sized costume hood. No tadpole-specific geometry is required.
 * Future costume models can call createLayer(...) and attach extras under
 * the "head" or "body" model parts. Uses the vanilla frog part hierarchy.
 */
public class BasicFrogHoodModel extends FrogModel {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(PrettyFrogs.id("basic_frog_hood"), "main");

    public BasicFrogHoodModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return createLayer(false);
    }

    protected static LayerDefinition createLayer(boolean tadpoleCostume) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition modelRoot = root.addOrReplaceChild(
                "root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = modelRoot.addOrReplaceChild(
                "body", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 4.0F));
        PartDefinition head = body.addOrReplaceChild(
                "head", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -1.0F));

        addHood(head);

        // Keep vanilla frog animation nodes, even though this model only
        // draws clothing, and leaves the frog's own body fully visible.
        PartDefinition eyes = head.addOrReplaceChild(
                "eyes", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, 2.0F));
        eyes.addOrReplaceChild("right_eye", CubeListBuilder.create(), PartPose.ZERO);
        eyes.addOrReplaceChild("left_eye", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild(
                "croaking_body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -5.0F));
        body.addOrReplaceChild(
                "tongue", CubeListBuilder.create(), PartPose.offset(0.0F, -1.01F, 1.0F));
        PartDefinition leftArm = body.addOrReplaceChild(
                "left_arm", CubeListBuilder.create(), PartPose.ZERO);
        leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition rightArm = body.addOrReplaceChild(
                "right_arm", CubeListBuilder.create(), PartPose.ZERO);
        rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition leftLeg = modelRoot.addOrReplaceChild(
                "left_leg", CubeListBuilder.create(), PartPose.ZERO);
        leftLeg.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition rightLeg = modelRoot.addOrReplaceChild(
                "right_leg", CubeListBuilder.create(), PartPose.ZERO);
        rightLeg.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.ZERO);

        if (tadpoleCostume) {
            TadpoleCostumeModel.addTadpoleDetails(head, body);
        }
        return LayerDefinition.create(mesh, 128, 128);
    }

    /**
     * Open-front, frog-shaped shell. The original frog eyes and face remain
     * visible beneath the rim. Hood dimensions are shared by every costume.
     */
    protected static void addHood(PartDefinition head) {
        head.addOrReplaceChild(
                "hood_cap",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.55F, -4.82F, -6.60F, 9.1F, 0.95F, 5.55F)
                        .texOffs(32, 0).addBox(-4.10F, -4.65F, -1.30F, 8.2F, 1.30F, 2.40F),
                PartPose.ZERO);
        head.addOrReplaceChild(
                "hood_front_rim",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-4.65F, -3.90F, -6.93F, 9.3F, 0.60F, 0.60F),
                PartPose.ZERO);
        head.addOrReplaceChild(
                "hood_left_side",
                CubeListBuilder.create()
                        .texOffs(32, 32).addBox(3.98F, -3.95F, -6.35F, 0.76F, 3.4F, 7.10F),
                PartPose.ZERO);
        head.addOrReplaceChild(
                "hood_right_side",
                CubeListBuilder.create()
                        .texOffs(32, 32).addBox(-4.74F, -3.95F, -6.35F, 0.76F, 3.4F, 7.10F),
                PartPose.ZERO);
        head.addOrReplaceChild(
                "hood_back",
                CubeListBuilder.create()
                        .texOffs(64, 32).addBox(-4.12F, -3.92F, 0.70F, 8.24F, 3.1F, 0.65F),
                PartPose.ZERO);
        head.addOrReplaceChild(
                "hood_inner_lip",
                CubeListBuilder.create()
                        .texOffs(0, 64).addBox(-3.65F, -3.28F, -6.96F, 7.30F, 0.28F, 0.35F),
                PartPose.ZERO);
    }
}
