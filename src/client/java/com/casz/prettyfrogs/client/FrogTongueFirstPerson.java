package com.casz.prettyfrogs.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.definitions.FrogAnimation;
import net.minecraft.client.model.animal.frog.FrogModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.frog.Frog;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Matrix4fStack;

/**
 * Renders the *actual vanilla FrogModel tongue mesh* in frog-eye first person.
 *
 * Not a HUD rectangle or imitation geometry: the ModelPart comes directly
 * from FrogModel.createBodyLayer(), uses FrogAnimation.FROG_TONGUE's vanilla
 * position/scale keyframes, and is textured with the original frog atlas UVs.
 *
 * It is submitted during GameRenderer.renderItemInHand after clearing world
 * depth, so the tongue is correctly perspective-projected, without rendering
 * the player's arms, the frog's head or its body through the camera.
 */
public final class FrogTongueFirstPerson {
    private static final int ATTACK_COOLDOWN = 16;
    private static final Identifier FROG_SKIN =
            Identifier.withDefaultNamespace("textures/entity/frog/frog_temperate.png");

    private static final ModelPart BAKED_ROOT = FrogModel.createBodyLayer().bakeRoot();
    private static final FrogModel MODEL = new FrogModel(BAKED_ROOT);
    private static final ModelPart TONGUE =
            MODEL.root().getChild("body").getChild("tongue");
    private static final KeyframeAnimation TONGUE_ANIMATION =
            FrogAnimation.FROG_TONGUE.bake(BAKED_ROOT);
    private static int cooldownRemaining;

    private FrogTongueFirstPerson() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (cooldownRemaining > 0) --cooldownRemaining;
            if (!FrogPossessionCamera.isPossessing(client.player)) {
                cooldownRemaining = 0;
            }
        });
    }

    /** Only transmit an attack when the previous strike's cooldown expired. */
    public static boolean startAttack() {
        if (cooldownRemaining > 0) return false;
        cooldownRemaining = ATTACK_COOLDOWN;
        return true;
    }

    /** Called by the existing GameRenderer possession hand-pass hook. */
    public static void submit(float partialTick, Matrix4fc modelView,
                              SubmitNodeStorage collector) {
        Minecraft mc = Minecraft.getInstance();
        if (!FrogPossessionCamera.isPossessing(mc.player)
                || !mc.options.getCameraType().isFirstPerson()
                || !(mc.player.getVehicle() instanceof Frog frog)
                || frog.getPose() != Pose.USING_TONGUE
                || !frog.tongueAnimationState.isStarted()) {
            return;
        }

        // Apply precisely the same tongue keyframes vanilla uses in the
        // third-person FrogModel. Animation lives on the synced frog entity.
        MODEL.resetPose();
        TONGUE_ANIMATION.apply(frog.tongueAnimationState,
                frog.tickCount + partialTick);

        PoseStack pose = new PoseStack();
        pose.pushPose();
        pose.mulPose(modelView.invert(new Matrix4f()));
        // First-person mouth position, just below and ahead of frog eyes.
        // Vanilla tongue UV quad extends forward in -Z and is 4x7 pixels.
        pose.translate(0.0D, -0.18D, -0.32D);
        pose.scale(1.0F, -1.0F, 1.0F);

        Matrix4fStack viewStack = RenderSystem.getModelViewStack();
        viewStack.pushMatrix().mul(modelView);
        try {
            collector.submitModelPart(TONGUE, pose,
                    RenderTypes.entityCutout(FROG_SKIN),
                    0x00F000F0, OverlayTexture.NO_OVERLAY, null);
        } finally {
            viewStack.popMatrix();
            pose.popPose();
        }
    }
}
