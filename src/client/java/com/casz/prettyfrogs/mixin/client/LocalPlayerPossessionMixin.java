package com.casz.prettyfrogs.mixin.client;

import com.casz.prettyfrogs.client.FrogPossessionCamera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.animal.frog.Frog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * LocalPlayer normally ignores movement input when cameraEntity != player.
 * Keep WASD/Space live while our legitimate mounted controller uses frog POV.
 * Also retains input packet/movement handling during this camera redirect.
 */
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerPossessionMixin {
    @Inject(method = "isControlledCamera", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$allowMountedFrogCamera(CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        if (FrogPossessionCamera.isPossessing(player)
                && Minecraft.getInstance().getCameraEntity() == player.getVehicle()) {
            cir.setReturnValue(true);
        }
    }
}
