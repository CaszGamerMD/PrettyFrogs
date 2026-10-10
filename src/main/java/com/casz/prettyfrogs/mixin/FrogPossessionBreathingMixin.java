package com.casz.prettyfrogs.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Frogs can remain underwater; the possessing player must share that
 * ability or their invisible passenger body still drowns while swimming.
 *
 * Apply only while actively controlling a frog. Returning to human form
 * restores ordinary vanilla respiration without any potion/status effects.
 */
@Mixin(LivingEntity.class)
public abstract class FrogPossessionBreathingMixin {
    @Inject(method = "canBreatheUnderwater", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$breatheWhilePossessing(CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof Player player
                && player.getVehicle() instanceof Frog frog
                && frog.isAlive()
                && frog.getControllingPassenger() == player) {
            cir.setReturnValue(true);
        }
    }
}
