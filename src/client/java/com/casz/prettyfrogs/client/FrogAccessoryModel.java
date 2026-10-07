package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.PrettyFrogs;
import com.casz.prettyfrogs.frog.FrogFormRegistry;
import net.minecraft.client.model.animal.frog.FrogModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;

public final class FrogAccessoryModel extends FrogModel {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(PrettyFrogs.id("frog_accessories"), "main");

    private final ModelPart wings;
    private final ModelPart cactusBodySpikes;
    private final ModelPart cactusHeadSpikes;

    public FrogAccessoryModel(ModelPart root) {
        super(root);
        ModelPart body = root.getChild("root").getChild("body");
        ModelPart head = body.getChild("head");
        wings = body.getChild("bumble_wings");
        cactusBodySpikes = body.getChild("cactus_body_spikes");
        cactusHeadSpikes = head.getChild("cactus_head_spikes");
        setForm(FrogFormRegistry.NORMAL);
    }

    public void setForm(Identifier form) {
        wings.visible = form.equals(FrogFormRegistry.BUMBLE);
        boolean cactus = form.equals(FrogFormRegistry.CACTUS);
        cactusBodySpikes.visible = cactus;
        cactusHeadSpikes.visible = cactus;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition modelRoot = root.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = modelRoot.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 4.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -1.0F));

        PartDefinition wings = body.addOrReplaceChild("bumble_wings", CubeListBuilder.create(), PartPose.ZERO);
        wings.addOrReplaceChild("left_wing",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.4F, -2.5F, 5.0F, 0.8F, 5.0F),
                PartPose.offset(2.8F, -2.2F, -2.0F));
        wings.addOrReplaceChild("right_wing",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -0.4F, -2.5F, 5.0F, 0.8F, 5.0F),
                PartPose.offset(-2.8F, -2.2F, -2.0F));

        // Anchor each spike at the actual top surface (local Y=-2) and extend
        // upward from that contact point. Keeping every Z inside the body/head
        // bounds prevents the old floating spikes behind the frog.
        PartDefinition bodySpikes = body.addOrReplaceChild("cactus_body_spikes", CubeListBuilder.create(), PartPose.ZERO);
        addSpike(bodySpikes, "back_1", -1.8F, -5.8F);
        addSpike(bodySpikes, "back_2", 1.8F, -4.0F);
        addSpike(bodySpikes, "back_3", -1.2F, -1.8F);
        addSpike(bodySpikes, "back_4", 1.5F, 0.0F);

        PartDefinition headSpikes = head.addOrReplaceChild("cactus_head_spikes", CubeListBuilder.create(), PartPose.ZERO);
        addSpike(headSpikes, "head_1", -2.1F, -4.5F);
        addSpike(headSpikes, "head_2", 2.0F, -2.0F);

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

        return LayerDefinition.create(mesh, 32, 32);
    }

    private static void addSpike(PartDefinition parent, String name, float x, float z) {
        parent.addOrReplaceChild(
                name,
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(x, -2.0F, z));
    }
}
