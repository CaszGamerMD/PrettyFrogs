package com.casz.prettyfrogs.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.frog.Frog;

/**
 * Render the world through the controlled frog's eyes, not the rider's eyes.
 * Keeping the actual player on the frog internally preserves vanilla mounted
 * movement/input syncing and avoids teleporting a player entity each tick.
 *
 * No persistent camera or invisibility state: death, dismount, dimension
 * change, disconnect and entity removal all exit on the next client tick.
 */
public final class FrogPossessionCamera {
    private static Entity activeCamera;

    private FrogPossessionCamera() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(FrogPossessionCamera::tick);
    }

    public static boolean isPossessing(LocalPlayer player) {
        return player != null
                && player.getVehicle() instanceof Frog frog
                && frog.isAlive()
                && !frog.isRemoved()
                && frog.getControllingPassenger() == player;
    }

    private static void tick(Minecraft minecraft) {
        LocalPlayer player = minecraft.player;
        if (player != null && isPossessing(player)) {
            Frog frog = (Frog)player.getVehicle();
            if (minecraft.getCameraEntity() != frog) {
                minecraft.setCameraEntity(frog);
            }
            activeCamera = frog;
            return;
        }

        if (activeCamera != null) {
            if (player != null && minecraft.getCameraEntity() == activeCamera) {
                minecraft.setCameraEntity(player);
            }
            activeCamera = null;
        }
    }
}
