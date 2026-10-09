package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.control.FrogControlPayload;
import com.casz.prettyfrogs.control.FrogControlAccess;
import net.minecraft.world.entity.animal.frog.Frog;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

/**
 * Possession mouse actions, using the player's usual attack/use keybinds:
 * attack -> tongue, use -> croak, sneak + use -> return to player.
 * One action is sent per press, not every tick while right-click is held.
 */
public final class FrogControllerKeys {
    private static boolean rightClickHandled;

    private FrogControllerKeys() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && FrogPossessionCamera.isPossessing(client.player)
                    && client.gui.screen() == null && client.options.keyJump.consumeClick()
                    && ClientPlayNetworking.canSend(FrogControlPayload.TYPE)) {
                // One edge-triggered hop rather than vanilla hold-to-charge.
                // Simulate locally too, so the frog reacts immediately.
                if (client.player.getVehicle() instanceof Frog frog
                        && frog instanceof FrogControlAccess control) {
                    control.prettyfrogs$controlledHop();
                }
                ClientPlayNetworking.send(new FrogControlPayload(FrogControlPayload.HOP));
            }
            if (client.player == null || !FrogPossessionCamera.isPossessing(client.player)
                    || !client.options.keyUse.isDown()) {
                rightClickHandled = false;
            }
        });
    }

    private static boolean canAct() {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        return player != null && client.gui.screen() == null
                && FrogPossessionCamera.isPossessing(player);
    }

    public static void tongue() {
        if (canAct() && ClientPlayNetworking.canSend(FrogControlPayload.TYPE)) {
            FrogTongueHud.startAttack();
            ClientPlayNetworking.send(new FrogControlPayload(FrogControlPayload.TONGUE));
        }
    }

    public static void croakOrExit() {
        if (!canAct() || rightClickHandled) {
            return;
        }
        rightClickHandled = true;
        if (ClientPlayNetworking.canSend(FrogControlPayload.TYPE)) {
            byte action = Minecraft.getInstance().player.isShiftKeyDown()
                    ? FrogControlPayload.EXIT
                    : FrogControlPayload.CROAK;
            ClientPlayNetworking.send(new FrogControlPayload(action));
        }
    }
}
