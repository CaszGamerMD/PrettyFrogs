package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.PrettyFrogsItems;
import com.casz.prettyfrogs.control.FrogControlPayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.animal.frog.Frog;
import org.lwjgl.glfw.GLFW;

/** Bindings work only while actively steering a frog with the controller. */
public final class FrogControllerKeys {
    private static final KeyMapping CROAK = KeyMappingHelper.registerKeyMapping(
            new KeyMapping("key.prettyfrogs.croak", GLFW.GLFW_KEY_C, KeyMapping.Category.GAMEPLAY));
    private static final KeyMapping TONGUE = KeyMappingHelper.registerKeyMapping(
            new KeyMapping("key.prettyfrogs.tongue", GLFW.GLFW_KEY_V, KeyMapping.Category.GAMEPLAY));

    private FrogControllerKeys() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean croak = CROAK.consumeClick();
            boolean tongue = TONGUE.consumeClick();
            if ((!croak && !tongue) || client.player == null || client.gui.screen() != null
                    || !(client.player.getControlledVehicle() instanceof Frog)
                    || !client.player.isHolding(PrettyFrogsItems.FROG_CONTROLLER)
                    || !ClientPlayNetworking.canSend(FrogControlPayload.TYPE)) {
                return;
            }
            if (tongue) ClientPlayNetworking.send(new FrogControlPayload(FrogControlPayload.TONGUE));
            else ClientPlayNetworking.send(new FrogControlPayload(FrogControlPayload.CROAK));
        });
    }
}
