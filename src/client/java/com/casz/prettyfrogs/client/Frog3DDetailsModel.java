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
 * Lightweight animated frog-shaped skeleton with ONLY the optional geometry.
 * Parent/body/head/leg names mirror vanilla FrogModel so model animation
 * follows hopping, walking, croaking and swimming without replacing the skin.
 * Each decoration has its own visibility group; unrelated cubes never render.
 */
public final class Frog3DDetailsModel extends FrogModel {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(PrettyFrogs.id("frog_3d_details"), "main");

    private final ModelPart crystalHead;
    private final ModelPart crystalBack;
    private final ModelPart mushroomHead;
    private final ModelPart mushroomBack;
    private final ModelPart balloonKnot;
    private final ModelPart frostingHead;
    private final ModelPart frostingBack;
    private final ModelPart snowHead;
    private final ModelPart snowBack;

    public Frog3DDetailsModel(ModelPart root) {
        super(root);
        ModelPart modelRoot = root.getChild("root");
        ModelPart body = modelRoot.getChild("body");
        ModelPart head = body.getChild("head");
        crystalHead = head.getChild("crystal_head");
        crystalBack = body.getChild("crystal_back");
        mushroomHead = head.getChild("mushroom_head");
        mushroomBack = body.getChild("mushroom_back");
        balloonKnot = body.getChild("balloon_knot");
        frostingHead = head.getChild("frosting_head");
        frostingBack = body.getChild("frosting_back");
        snowHead = head.getChild("snow_head");
        snowBack = body.getChild("snow_back");
        setDetail(Frog3DDetailKind.NONE);
    }

    public void setDetail(Frog3DDetailKind kind) {
        crystalHead.visible = kind == Frog3DDetailKind.CRYSTALS;
        crystalBack.visible = kind == Frog3DDetailKind.CRYSTALS;
        mushroomHead.visible = kind == Frog3DDetailKind.MUSHROOMS;
        mushroomBack.visible = kind == Frog3DDetailKind.MUSHROOMS;
        balloonKnot.visible = kind == Frog3DDetailKind.BALLOON_KNOT;
        frostingHead.visible = kind == Frog3DDetailKind.FROSTING_CAP;
        frostingBack.visible = kind == Frog3DDetailKind.FROSTING_CAP;
        snowHead.visible = kind == Frog3DDetailKind.SNOW_CAP;
        snowBack.visible = kind == Frog3DDetailKind.SNOW_CAP;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition modelRoot = root.addOrReplaceChild("root", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = modelRoot.addOrReplaceChild("body", CubeListBuilder.create(),
                PartPose.offset(0.0F, -2.0F, 4.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(),
                PartPose.offset(0.0F, -2.0F, -1.0F));

        // Crystal spires: large central shard plus offset small shards, anchored
        // to top of the head. Use only the grayscale atlas area, tinted at render time.
        head.addOrReplaceChild("crystal_head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1.0F, -6.0F, -3.0F, 2.0F, 4.0F, 2.0F)
                        .texOffs(10, 0).addBox(-2.7F, -4.5F, -2.7F, 1.0F, 2.0F, 1.0F)
                        .texOffs(16, 0).addBox(1.7F, -4.0F, -1.8F, 1.0F, 2.0F, 1.0F),
                PartPose.ZERO);
        // More crystals protrude from the back, leaving the frog's face clear.
        body.addOrReplaceChild("crystal_back",
                CubeListBuilder.create()
                        .texOffs(0, 11).addBox(-2.1F, -4.0F, 1.5F, 1.5F, 3.0F, 1.5F)
                        .texOffs(10, 11).addBox(0.8F, -3.5F, 2.3F, 1.5F, 2.5F, 1.5F)
                        .texOffs(20, 11).addBox(-0.7F, -3.7F, 5.0F, 1.4F, 2.7F, 1.4F),
                PartPose.ZERO);

        // Tiny mushroom on the crown: ivory stalk, spotted red cap and off-white dot.
        head.addOrReplaceChild("mushroom_head",
                CubeListBuilder.create()
                        .texOffs(32, 0).addBox(1.0F, -3.7F, -1.8F, 1.0F, 2.0F, 1.0F)
                        .texOffs(40, 0).addBox(0.2F, -4.2F, -2.5F, 2.5F, 1.0F, 2.5F)
                        .texOffs(56, 0).addBox(1.0F, -4.35F, -1.9F, 0.7F, 0.25F, 0.7F),
                PartPose.ZERO);
        // Two different-size little mushrooms on the back.
        body.addOrReplaceChild("mushroom_back",
                CubeListBuilder.create()
                        .texOffs(32, 7).addBox(-2.3F, -3.0F, 2.0F, 1.0F, 2.0F, 1.0F)
                        .texOffs(40, 7).addBox(-3.2F, -3.5F, 1.2F, 2.8F, 1.0F, 2.8F)
                        .texOffs(32, 15).addBox(1.3F, -3.8F, 4.2F, 1.0F, 2.6F, 1.0F)
                        .texOffs(40, 15).addBox(0.3F, -4.3F, 3.3F, 3.0F, 1.0F, 3.0F),
                PartPose.ZERO);

        // Water/Balloon frog: tied off at the underside of the frog, with a
        // wrapped neck and hanging tail. No balloon sphere replaces the body.
        body.addOrReplaceChild("balloon_knot",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-0.8F, 0.0F, -4.3F, 1.6F, 1.0F, 1.5F)
                        .texOffs(10, 32).addBox(-1.3F, 0.9F, -4.1F, 2.6F, 0.65F, 1.1F)
                        .texOffs(20, 32).addBox(-0.5F, 1.5F, -3.9F, 1.0F, 1.5F, 0.7F),
                PartPose.ZERO);

        // Cake Frog: frosting is a shallow cap hugging the TOP HALF of the
        // vanilla head and back. Slight overhang makes it read as icing without
        // replacing the cake-colored lower body. Candles render independently above it.
        head.addOrReplaceChild("frosting_head",
                CubeListBuilder.create()
                        .texOffs(32, 24).addBox(-3.25F, -2.55F, -6.75F, 6.5F, 0.9F, 6.5F)
                        .texOffs(32, 24).addBox(-3.18F, -1.72F, -6.68F, 0.55F, 0.75F, 6.35F)
                        .texOffs(32, 24).addBox(2.63F, -1.72F, -6.68F, 0.55F, 0.75F, 6.35F),
                PartPose.ZERO);
        body.addOrReplaceChild("frosting_back",
                CubeListBuilder.create()
                        .texOffs(32, 24).addBox(-3.45F, -1.70F, -0.65F, 6.9F, 0.9F, 7.4F)
                        .texOffs(32, 24).addBox(-3.38F, -0.90F, 0.1F, 0.55F, 0.8F, 5.9F)
                        .texOffs(32, 24).addBox(2.83F, -0.90F, 0.9F, 0.55F, 0.65F, 4.8F),
                PartPose.ZERO);

        // Ice Frog: an uneven layer of settled snow over the same top-half
        // silhouette. Low blocky mounds break up the outline so it reads as snow,
        // while the existing translucent ice shell remains visible beneath.
        head.addOrReplaceChild("snow_head",
                CubeListBuilder.create()
                        .texOffs(32, 44).addBox(-3.22F, -2.58F, -6.72F, 6.44F, 0.88F, 6.44F)
                        .texOffs(32, 44).addBox(-2.45F, -3.05F, -5.45F, 2.2F, 0.55F, 2.1F)
                        .texOffs(32, 44).addBox(0.55F, -2.95F, -3.35F, 2.0F, 0.45F, 2.0F),
                PartPose.ZERO);
        body.addOrReplaceChild("snow_back",
                CubeListBuilder.create()
                        .texOffs(32, 44).addBox(-3.40F, -1.73F, -0.62F, 6.8F, 0.88F, 7.3F)
                        .texOffs(32, 44).addBox(-2.5F, -2.20F, 1.1F, 2.3F, 0.55F, 2.0F)
                        .texOffs(32, 44).addBox(0.45F, -2.12F, 3.65F, 2.45F, 0.5F, 2.1F),
                PartPose.ZERO);

        // Required vanilla model children: zero geometry, but correct hierarchy
        // so FrogModel's inherited animation setup can still run safely.
        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create(),
                PartPose.offset(-0.5F, 0.0F, 2.0F));
        eyes.addOrReplaceChild("right_eye", CubeListBuilder.create(), PartPose.ZERO);
        eyes.addOrReplaceChild("left_eye", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("croaking_body", CubeListBuilder.create(),
                PartPose.offset(0.0F, -1.0F, -5.0F));
        body.addOrReplaceChild("tongue", CubeListBuilder.create(),
                PartPose.offset(0.0F, -1.01F, 1.0F));
        PartDefinition leftArm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
        leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition rightArm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition leftLeg = modelRoot.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
        leftLeg.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition rightLeg = modelRoot.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);
        rightLeg.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 64);
    }
}
