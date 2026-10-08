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
 * A hovering, legless ghost with a recognizably vanilla-frog face.
 *
 * The animated vanilla frog hierarchy is retained, including its empty hind
 * legs, to avoid breaking vanilla look/hop/croak animations. UV islands are
 * grouped into 32x32 tiles of a bespoke translucent 128x128 spectral atlas.
 */
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
                        // Translucent, rounded-off mantle and nested chest.
                        .texOffs(32, 32).addBox(-3.6F, -2.1F, -6.8F, 7.2F, 2.8F, 7.5F)
                        .texOffs(64, 32).addBox(-3.0F, -0.45F, -6.2F, 6.0F, 1.6F, 6.5F)
                        .texOffs(96, 32).addBox(-4.0F, -1.6F, -5.7F, 8.0F, 1.25F, 2.1F),
                PartPose.offset(0.0F, -2.0F, 4.0F));

        // Wide, shallow vanilla-style frog skull, softened into a ghostly
        // mask rather than a giant rectangular block.
        PartDefinition head = body.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.4F, -1.8F, -5.4F, 8.8F, 2.0F, 4.6F)
                        .texOffs(32, 0).addBox(-4.25F, -1.18F, -6.20F, 8.5F, 1.25F, 1.25F)
                        .texOffs(64, 0).addBox(-3.8F, 0.32F, -6.0F, 7.6F, 0.40F, 1.3F)
                        // Round out the silhouette where the eyes meet the jaw.
                        .texOffs(64, 0).addBox(-4.48F, -0.9F, -4.9F, 0.9F, 1.5F, 2.7F)
                        .texOffs(64, 0).addBox(3.58F, -0.9F, -4.9F, 0.9F, 1.5F, 2.7F)
                        // A dark curved grin across the snout, distinct from
                        // the lower lip; these are real thin cubes, not an
                        // oversized generic eye or mouth overlay.
                        .texOffs(64, 96).addBox(-2.9F, 0.04F, -6.25F, 5.8F, 0.19F, 0.17F)
                        .texOffs(64, 96).addBox(-3.4F, -0.15F, -6.2F, 0.55F, 0.20F, 0.18F)
                        .texOffs(64, 96).addBox(2.85F, -0.15F, -6.2F, 0.55F, 0.20F, 0.18F),
                PartPose.offset(0.0F, -2.0F, -1.0F));

        // Vanilla eye-group pivots, but bespoke raised mounds with dark
        // sockets and small glowing spectral pupils.
        PartDefinition eyes = head.addOrReplaceChild(
                "eyes", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, 2.0F));
        addEye(eyes, "right_eye", -2.68F);
        addEye(eyes, "left_eye", 3.68F);

        // The lower half tapers into layered, irregular wisps rather than
        // legs or a flat rectangular bottom.
        addWisp(body, "front_left_wisp", -2.75F, -4.9F, 1.65F, 2.15F, 0);
        addWisp(body, "front_right_wisp", 2.75F, -4.9F, 1.85F, 1.9F, 1);
        addWisp(body, "rear_left_wisp", -2.25F, -1.0F, 1.55F, 2.15F, 2);
        addWisp(body, "rear_middle_wisp", 0.0F, -0.15F, 1.7F, 2.0F, 3);
        addWisp(body, "rear_right_wisp", 2.25F, -1.1F, 1.3F, 2.1F, 4);

        body.addOrReplaceChild(
                "spectral_tail",
                CubeListBuilder.create()
                        .texOffs(32, 96).addBox(-1.35F, 0.15F, 0.4F, 2.7F, 0.75F, 2.6F)
                        .texOffs(0, 96).addBox(-0.8F, 0.55F, 2.6F, 1.6F, 0.6F, 1.7F)
                        .texOffs(32, 96).addBox(-0.35F, 0.9F, 3.9F, 0.7F, 0.35F, 1.3F),
                PartPose.ZERO);

        // Animation compatibility: visible anatomy is spectral, not flesh.
        body.addOrReplaceChild(
                "croaking_body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -5.0F));
        body.addOrReplaceChild(
                "tongue", CubeListBuilder.create(), PartPose.offset(0.0F, -1.01F, 1.0F));

        addArm(body, "left_arm", 3.9F);
        addArm(body, "right_arm", -3.9F);

        // Ghost intentionally has no hind legs or feet, but the animation
        // hierarchy requires their names to be present.
        PartDefinition leftLeg = modelRoot.addOrReplaceChild(
                "left_leg", CubeListBuilder.create(), PartPose.offset(3.5F, -3.0F, 4.0F));
        leftLeg.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition rightLeg = modelRoot.addOrReplaceChild(
                "right_leg", CubeListBuilder.create(), PartPose.offset(-3.5F, -3.0F, 4.0F));
        rightLeg.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(mesh, 128, 128);
    }

    private static void addEye(PartDefinition eyes, String name, float x) {
        eyes.addOrReplaceChild(
                name,
                CubeListBuilder.create()
                        // Broad raised eye bump overlapping the cranium.
                        .texOffs(0, 32).addBox(-1.28F, -1.0F, -1.10F, 2.56F, 1.7F, 2.35F)
                        // Dark, recessed-looking front socket.
                        .texOffs(96, 0).addBox(-0.84F, -0.53F, -1.17F, 1.68F, 0.72F, 0.13F)
                        // Only this UV tile is full-bright in the glow pass.
                        .texOffs(96, 96).addBox(-0.28F, -0.36F, -1.23F, 0.56F, 0.44F, 0.11F)
                        // Thin brow catches a brighter translucent highlight.
                        .texOffs(32, 0).addBox(-1.06F, -1.05F, -1.22F, 2.12F, 0.25F, 0.22F),
                PartPose.offset(x, -2.0F, -4.85F));
    }

    private static void addWisp(PartDefinition body, String name, float x, float z,
                                float length, float width, int style) {
        float centerX = x;
        float trail = style % 2 == 0 ? -0.26F : 0.26F;
        float secondaryWidth = width * 0.62F;
        body.addOrReplaceChild(
                name,
                CubeListBuilder.create()
                        // Broad attachment, then two offset thinning pieces.
                        .texOffs(0, 64).addBox(-width / 2.0F, 0.60F, -0.55F,
                                width, 0.65F, 1.1F)
                        .texOffs(32, 64).addBox(-secondaryWidth / 2.0F + trail, 1.05F, -0.38F,
                                secondaryWidth, length * 0.46F, 0.76F)
                        .texOffs(0, 96).addBox(-0.25F + trail, 1.27F + length * 0.36F, -0.23F,
                                0.5F, length * 0.33F, 0.46F),
                PartPose.offset(centerX, 0.0F, z));
    }

    private static void addArm(PartDefinition body, String name, float x) {
        PartDefinition arm = body.addOrReplaceChild(
                name,
                CubeListBuilder.create()
                        .texOffs(64, 64).addBox(-0.92F, 0.0F, -0.78F, 1.84F, 1.8F, 1.75F)
                        .texOffs(64, 64).addBox(-0.63F, 1.25F, -0.58F, 1.26F, 1.0F, 1.1F),
                PartPose.offset(x, -1.0F, -5.7F));
        PartDefinition hand = arm.addOrReplaceChild(
                name.equals("left_arm") ? "left_hand" : "right_hand",
                CubeListBuilder.create()
                        .texOffs(96, 64).addBox(-0.9F, -0.12F, -0.65F, 1.8F, 0.38F, 1.25F),
                PartPose.offset(0.0F, 2.2F, -0.28F));
        for (int i = 0; i < 3; i++) {
            hand.addOrReplaceChild(
                    "mist_finger_" + i,
                    CubeListBuilder.create().texOffs(96, 64).addBox(-0.18F, 0.0F, -1.25F,
                            0.36F, 0.26F, 1.25F),
                    PartPose.offset((i - 1) * 0.67F, 0.0F, -0.58F));
        }
    }
}
