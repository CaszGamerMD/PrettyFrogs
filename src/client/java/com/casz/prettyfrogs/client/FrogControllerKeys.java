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
    private static int croakRepeatDelay;

    private FrogControllerKeys() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && FrogPossessionCamera.isPossessing(client.player)
                    && client.gui.screen() == null
                    && client.player.getVehicle() instanceof Frog frog
                    && frog instanceof FrogControlAccess control) {
                // Space is a single frog leap on land. While swimming, hold
                // Space to ascend; hold Sneak to descend. The server reads
                // the same values from vanilla ServerboundPlayerInputPacket.
                control.prettyfrogs$setSwimInputs(
                        client.options.keyJump.isDown(),
                        client.options.keyShift.isDown());
                if (client.options.keyJump.consumeClick() && !frog.isInWater()
                        && ClientPlayNetworking.canSend(FrogControlPayload.TYPE)) {
                    control.prettyfrogs$controlledHop();
                    ClientPlayNetworking.send(new FrogControlPayload(FrogControlPayload.HOP));
                }
            }
            if (client.player == null || !FrogPossessionCamera.isPossessing(client.player)
                    || !client.options.keyUse.isDown() || client.gui.screen() != null) {
                rightClickHandled = false;
                croakRepeatDelay = 0;
            } else if (rightClickHandled && !client.player.isShiftKeyDown()) {
                // Holding right-click keeps croaking; rapid distinct clicks
                // also work because the server no longer gates croak on the
                // tongue attack timer.
                if (croakRepeatDelay > 0) --croakRepeatDelay;
                if (croakRepeatDelay == 0 && ClientPlayNetworking.canSend(FrogControlPayload.TYPE)) {
                    ClientPlayNetworking.send(new FrogControlPayload(FrogControlPayload.CROAK));
                    croakRepeatDelay = 5;
                }
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
            if (FrogTongueFirstPerson.startAttack()) {
                ClientPlayNetworking.send(new FrogControlPayload(FrogControlPayload.TONGUE));
            }
        }
    }

    public static void croakOrExit() {
        if (!canAct() || rightClickHandled) {
            return;
        }
        rightClickHandled = true;
        croakRepeatDelay = 5;
        if (ClientPlayNetworking.canSend(FrogControlPayload.TYPE)) {
            byte action = Minecraft.getInstance().player.isShiftKeyDown()
                    ? FrogControlPayload.EXIT
                    : FrogControlPayload.CROAK;
            ClientPlayNetworking.send(new FrogControlPayload(action));
        }
    }
}
