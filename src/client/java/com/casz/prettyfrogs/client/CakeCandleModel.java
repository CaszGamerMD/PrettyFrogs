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

public final class CakeCandleModel extends FrogModel {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(PrettyFrogs.id("cake_candles"), "main");

    private final ModelPart[] candles;

    public CakeCandleModel(ModelPart root) {
        super(root);
        ModelPart head = root.getChild("root").getChild("body").getChild("head");
        this.candles = new ModelPart[] {
                head.getChild("candle_1"),
                head.getChild("candle_2"),
                head.getChild("candle_3"),
                head.getChild("candle_4")
        };
    }

    public void setCount(int count) {
        int visible = Math.max(0, Math.min(4, count));
        for (int i = 0; i < candles.length; i++) {
            candles[i].visible = i < visible;
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition modelRoot = root.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = modelRoot.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 4.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -1.0F));

        addCandle(head, "candle_1", -1.5F, -3.8F);
        addCandle(head, "candle_2", 1.5F, -3.8F);
        addCandle(head, "candle_3", -1.5F, -1.6F);
        addCandle(head, "candle_4", 1.5F, -1.6F);

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

    private static void addCandle(PartDefinition head, String name, float x, float z) {
        head.addOrReplaceChild(name,
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 3.0F, 1.0F)
                        .texOffs(8, 0).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(x, 0.0F, z));
    }
}
