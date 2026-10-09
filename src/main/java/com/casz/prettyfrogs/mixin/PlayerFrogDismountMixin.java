package com.casz.prettyfrogs.mixin;

import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Vanilla dismounts a player as soon as Sneak is pressed. During frog
 * possession, Sneak is a modifier for right-click instead; the server
 * ends the ride only when it receives the explicit EXIT controller packet.
 */
@Mixin(Player.class)
public abstract class PlayerFrogDismountMixin {
    @Inject(method = "wantsToStopRiding", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$requireSneakAndUseToExit(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player)(Object)this;
        if (player.getVehicle() instanceof Frog frog
                && frog.getControllingPassenger() == player) {
            cir.setReturnValue(false);
        }
    }
}
