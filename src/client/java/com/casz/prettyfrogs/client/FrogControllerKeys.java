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
    private static int hopRequestDelay;

    private FrogControllerKeys() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && FrogPossessionCamera.isPossessing(client.player)
                    && client.gui.screen() == null
                    && client.player.getVehicle() instanceof Frog frog
                    && frog instanceof FrogControlAccess control) {
                // Water keeps continuous vanilla swim inputs. On land, holding
                // Space chains long jumps after landing rather than requiring
                // perfectly timed taps or relying on consumeClick(), which
                // other vanilla input handling may already have consumed.
                boolean jumpDown = client.options.keyJump.isDown();
                control.prettyfrogs$setSwimInputs(
                        jumpDown, client.options.keyShift.isDown());

                if (hopRequestDelay > 0) --hopRequestDelay;
                if (!jumpDown) {
                    hopRequestDelay = 0;
                } else if (!frog.isInWater() && frog.onGround()
                        && hopRequestDelay == 0
                        && ClientPlayNetworking.canSend(FrogControlPayload.TYPE)) {
                    // Local prediction keeps the camera and pose responsive;
                    // the server independently checks ground state/cooldown.
                    control.prettyfrogs$controlledHop();
                    ClientPlayNetworking.send(new FrogControlPayload(FrogControlPayload.HOP));
                    hopRequestDelay = 8;
                }
            } else {
                hopRequestDelay = 0;
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
