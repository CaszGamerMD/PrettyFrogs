package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.PrettyFrogs;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

/**
 * First-person frog tongue. Minecraft normally hides the camera entity's
 * model, which hides the real frog's tongue as well. Draw a short, animated,
 * tapered pink tongue from the bottom-center (frog's mouth) toward the reticle.
 * Only appears for an actual frog possession in first-person perspective.
 */
public final class FrogTongueHud {
    private static int animationTicks;

    private FrogTongueHud() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (animationTicks > 0) --animationTicks;
            if (!FrogPossessionCamera.isPossessing(client.player)) animationTicks = 0;
        });
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CROSSHAIR, PrettyFrogs.id("frog_tongue_pov"),
                (graphics, tracker) -> draw(graphics));
    }

    public static void startAttack() {
        animationTicks = 11;
    }

    private static void draw(GuiGraphicsExtractor graphics) {
        Minecraft client = Minecraft.getInstance();
        if (animationTicks <= 0 || client.player == null || client.gui.screen() != null
                || !client.options.getCameraType().isFirstPerson()
                || !FrogPossessionCamera.isPossessing(client.player)) return;

        // Fast extension, brief hold, then retraction. Size is independent
        // of the window resolution because GuiGraphicsExtractor is GUI-scaled.
        float progress = switch (animationTicks) {
            case 11 -> 0.17F;
            case 10 -> 0.47F;
            case 9 -> 0.78F;
            case 8, 7, 6 -> 1.0F;
            case 5 -> 0.80F;
            case 4 -> 0.58F;
            case 3 -> 0.36F;
            case 2 -> 0.17F;
            default -> 0.07F;
        };
        int center = graphics.guiWidth() / 2;
        int mouthY = graphics.guiHeight() - 27;
        int fullReach = Math.max(30, Math.min(136, graphics.guiHeight() / 2 - 34));
        int reach = Math.round(fullReach * progress);
        int steps = Math.max(1, reach / 3);

        // Trapezoid in perspective: broad at the mouth and thin at the tip.
        for (int i = 0; i < steps; ++i) {
            float t = (float)i / steps;
            int y = mouthY - i * 3;
            int radius = Math.max(2, Math.round(9.0F - 7.0F * t));
            graphics.fill(center - radius - 1, y - 3, center + radius + 1, y,
                    0xFF8B315A);
            graphics.fill(center - radius, y - 3, center + radius, y,
                    t > 0.70F ? 0xFFFF9CC4 : 0xFFFF709F);
            graphics.fill(center - Math.max(1, radius / 3), y - 3,
                    center + Math.max(1, radius / 3), y,
                    0xFFFFB3CD);
        }
        int tipY = mouthY - steps * 3;
        graphics.fill(center - 3, tipY - 4, center + 3, tipY + 1, 0xFFFFA7C8);
    }
}
