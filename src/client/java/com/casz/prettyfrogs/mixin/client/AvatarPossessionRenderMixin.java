package com.casz.prettyfrogs.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hide the rider avatar, held items, armor, cape and name tag only when
 * actually possessing a controlled frog. Does not change Entity.isInvisible,
 * potion status, mob targeting, collision, or another player's game logic.
 *
 * LivingEntityRenderer.submit is the shared rendering entry point for player
 * avatars. Skip the entire avatar submission rather than merely hiding skin,
 * since armor and hand render layers would otherwise remain on the frog.
 */
@Mixin(LivingEntityRenderer.class)
public abstract class AvatarPossessionRenderMixin {
    @Inject(method = "submit", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$hidePossessingPlayer(
            LivingEntityRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            CameraRenderState camera,
            CallbackInfo ci) {
        if (!(state instanceof AvatarRenderState avatarState)) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        if (mc.level.getEntity(avatarState.id) instanceof Player player
                && player.getVehicle() instanceof Frog frog
                && frog.isAlive()
                && frog.getControllingPassenger() == player) {
            ci.cancel();
        }
    }
}
