package com.casz.prettyfrogs.control;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;

/** Validates all controller actions on the server, never trusts a target id. */
public final class FrogControlNetworking {
    private FrogControlNetworking() {}

    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(FrogControlPayload.TYPE, FrogControlPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(FrogControlPayload.TYPE, (payload, context) -> {
            Player player = context.player();
            // No remote attacks: you must be the controlling passenger.
            if (!(player.getVehicle() instanceof Frog frog)
                    || frog.getControllingPassenger() != player
                    || !(frog instanceof FrogControlAccess control)
                    || !frog.isAlive()) {
                return;
            }
            if (payload.action() == FrogControlPayload.CROAK) {
                control.prettyfrogs$controlledCroak();
            } else if (payload.action() == FrogControlPayload.TONGUE) {
                control.prettyfrogs$controlledTongue();
            } else if (payload.action() == FrogControlPayload.EXIT) {
                // The server owns the actual dismount. A client cannot
                // remotely detach another player, since frog ownership was
                // validated above against this packet's sender.
                player.stopRiding();
            }
        });
    }
}
