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
 * An actual hollow-looking frog skeleton instead of a reskinned solid frog.
 * The vanilla frog part names and animation pivots are deliberately retained.
 *
 * The UVs use a purpose-made 128x128, 16-panel bone atlas.  Each panel is
 * 32x32, leaving enough room for all six faces of every box in its group.
 */
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
        PartDefinition body = modelRoot.addOrReplaceChild(
                "body", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 4.0F));
        // The skull follows the vanilla frog silhouette: a very broad, low
        // face, a blunt full-width muzzle and two separate raised eye bumps.
        // Keep it skeletal with an open U-shaped lower jaw, not a solid chin.
        PartDefinition head = body.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        // Broad and shallow cranium behind the muzzle.
                        .texOffs(0, 0).addBox(-4.5F, -2.0F, -5.35F, 9.0F, 1.35F, 4.75F)
                        // Wide, flat front snout characteristic of vanilla frogs.
                        .texOffs(32, 0).addBox(-4.35F, -1.52F, -6.15F, 8.7F, 0.95F, 1.2F)
                        // Cheekbones broaden the profile under the eye bumps.
                        .texOffs(0, 32).addBox(-4.55F, -1.25F, -5.15F, 1.2F, 0.95F, 3.2F)
                        .texOffs(0, 32).addBox(3.35F, -1.25F, -5.15F, 1.2F, 0.95F, 3.2F)
                        // Open lower jaw: front bone and the two side branches.
                        .texOffs(64, 0).addBox(-4.0F, -0.20F, -6.15F, 8.0F, 0.38F, 0.65F)
                        .texOffs(64, 0).addBox(-4.0F, -0.20F, -5.65F, 0.60F, 0.38F, 4.25F)
                        .texOffs(64, 0).addBox(3.40F, -0.20F, -5.65F, 0.60F, 0.38F, 4.25F)
                        // Two tiny nostril pits in the flat top of the snout.
                        .texOffs(96, 0).addBox(-2.0F, -1.56F, -5.85F, 0.5F, 0.08F, 0.5F)
                        .texOffs(96, 0).addBox(1.5F, -1.56F, -5.85F, 0.5F, 0.08F, 0.5F)
                        // Short teeth remain visible without a hanging "mask".
                        .texOffs(96, 96).addBox(-1.9F, -0.70F, -6.18F, 0.4F, 0.52F, 0.30F)
                        .texOffs(96, 96).addBox(1.5F, -0.70F, -6.18F, 0.4F, 0.52F, 0.30F),
                PartPose.offset(0.0F, -2.0F, -1.0F));

        // Vanilla-style upper-corner eye mounds, with dark sockets set into
        // their FRONT rather than floating as two black tiles on the roof.
        PartDefinition eyes = head.addOrReplaceChild(
                "eyes", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, 2.0F));
        addEyeSocket(eyes, "right_eye", -2.70F);
        addEyeSocket(eyes, "left_eye", 3.70F);

        // Thin backbone and hip bar; the body is open between its ribs.
        body.addOrReplaceChild(
                "spine",
                CubeListBuilder.create()
                        .texOffs(32, 32).addBox(-0.45F, -1.95F, -7.0F, 0.9F, 0.85F, 8.0F)
                        .texOffs(96, 32).addBox(-0.7F, -2.2F, -5.65F, 1.4F, 0.6F, 0.7F)
                        .texOffs(96, 32).addBox(-0.7F, -2.2F, -3.85F, 1.4F, 0.6F, 0.7F)
                        .texOffs(96, 32).addBox(-0.7F, -2.2F, -2.05F, 1.4F, 0.6F, 0.7F),
                PartPose.ZERO);
        addRib(body, "front_ribs", -5.7F);
        addRib(body, "middle_ribs", -3.9F);
        addRib(body, "rear_ribs", -2.1F);
        body.addOrReplaceChild(
                "pelvis",
                CubeListBuilder.create()
                        .texOffs(0, 64).addBox(-3.5F, -1.3F, -0.4F, 7.0F, 0.65F, 1.1F)
                        .texOffs(0, 64).addBox(-3.5F, -1.1F, -0.6F, 0.85F, 1.1F, 1.25F)
                        .texOffs(0, 64).addBox(2.65F, -1.1F, -0.6F, 0.85F, 1.1F, 1.25F),
                PartPose.ZERO);

        // No flesh: keeping empty versions preserves vanilla croak/tongue
        // animation lookups while leaving the ribs and jaw visible.
        body.addOrReplaceChild("croaking_body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -5.0F));
        body.addOrReplaceChild("tongue", CubeListBuilder.create(), PartPose.offset(0.0F, -1.01F, 1.0F));

        addArm(body, "left_arm", 3.45F);
        addArm(body, "right_arm", -3.45F);
        addLeg(modelRoot, "left_leg", 3.45F);
        addLeg(modelRoot, "right_leg", -3.45F);
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static void addEyeSocket(PartDefinition eyes, String name, float x) {
        // Raised, bone-framed eye mound with a recessed black front-facing
        // socket. The mound overlaps the skull to avoid floating geometry.
        eyes.addOrReplaceChild(
                name,
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-1.28F, -1.25F, -1.15F, 2.56F, 1.75F, 2.40F)
                        .texOffs(96, 0).addBox(-0.82F, -0.87F, -1.19F, 1.64F, 0.78F, 0.12F)
                        .texOffs(32, 0).addBox(-1.12F, -1.27F, -1.19F, 2.24F, 0.26F, 0.30F),
                PartPose.offset(x, -2.35F, -5.0F));
    }

    private static void addRib(PartDefinition body, String name, float z) {
        body.addOrReplaceChild(
                name,
                CubeListBuilder.create()
                        // Paired ribs and short downward-curving tips.
                        .texOffs(64, 32).addBox(-2.85F, -1.6F, 0.0F, 2.55F, 0.5F, 0.6F)
                        .texOffs(64, 32).addBox(0.3F, -1.6F, 0.0F, 2.55F, 0.5F, 0.6F)
                        .texOffs(64, 32).addBox(-2.85F, -1.25F, 0.0F, 0.5F, 0.8F, 0.6F)
                        .texOffs(64, 32).addBox(2.35F, -1.25F, 0.0F, 0.5F, 0.8F, 0.6F),
                PartPose.offset(0.0F, 0.0F, z));
    }

    private static void addArm(PartDefinition body, String name, float x) {
        PartDefinition arm = body.addOrReplaceChild(
                name,
                CubeListBuilder.create()
                        .texOffs(32, 64).addBox(-0.46F, 0.0F, -0.45F, 0.92F, 1.5F, 0.9F)
                        .texOffs(64, 64).addBox(-0.35F, 1.3F, -0.6F, 0.7F, 1.5F, 0.7F)
                        .texOffs(32, 96).addBox(-0.52F, 1.25F, -0.6F, 1.04F, 0.65F, 0.85F),
                PartPose.offset(x, -1.0F, -5.55F));
        PartDefinition hand = arm.addOrReplaceChild(
                name.equals("left_arm") ? "left_hand" : "right_hand",
                CubeListBuilder.create()
                        .texOffs(96, 64).addBox(-0.85F, -0.15F, -0.7F, 1.7F, 0.38F, 1.3F),
                PartPose.offset(0.0F, 2.83F, -0.55F));
        addDigits(hand, false);
    }

    private static void addLeg(PartDefinition root, String name, float x) {
        PartDefinition leg = root.addOrReplaceChild(
                name,
                CubeListBuilder.create()
                        .texOffs(0, 96).addBox(-0.55F, 0.0F, -0.6F, 1.1F, 1.95F, 1.2F)
                        .texOffs(32, 96).addBox(-0.62F, 1.75F, -0.7F, 1.24F, 0.72F, 1.3F)
                        .texOffs(0, 96).addBox(-0.42F, 2.2F, -0.55F, 0.84F, 0.75F, 1.0F),
                PartPose.offset(x, -3.0F, 4.0F));
        PartDefinition foot = leg.addOrReplaceChild(
                name.equals("left_leg") ? "left_foot" : "right_foot",
                CubeListBuilder.create()
                        .texOffs(64, 96).addBox(-1.15F, -0.25F, -1.1F, 2.3F, 0.42F, 1.65F),
                PartPose.offset(0.0F, 3.0F, -0.35F));
        addDigits(foot, true);
    }

    private static void addDigits(PartDefinition handOrFoot, boolean hindFoot) {
        // A four-toed web-free bone fan, visible even from above. Each digit
        // gets its own part so no cubes become detached when walking/hopping.
        float extension = hindFoot ? 2.0F : 1.4F;
        for (int i = 0; i < 4; i++) {
            float x = (i - 1.5F) * (hindFoot ? 0.67F : 0.5F);
            handOrFoot.addOrReplaceChild(
                    "digit_" + i,
                    CubeListBuilder.create()
                            .texOffs(96, 96).addBox(-0.16F, -0.12F, -extension, 0.32F, 0.26F, extension)
                            .texOffs(32, 96).addBox(-0.23F, -0.18F, -extension, 0.46F, 0.34F, 0.45F),
                    PartPose.offset(x, 0.0F, -0.35F));
        }
    }
}
