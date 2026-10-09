package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.PrettyFrogs;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

/**
 * Perspective tongue from the frog's mouth toward the center of the screen.
 *
 * The former overlay was a huge vertical rectangle drawn almost the whole
 * height of the window. This animation is a SHORT tapering tongue with shaded
 * edges, a curved centerline, a rounded tip and eased extension/retraction.
 * It is only drawn in first-person possession and respects attack cooldown.
 */
public final class FrogTongueHud {
    private static final int ANIMATION_TICKS = 10;
    private static final int ATTACK_COOLDOWN = 16;
    private static int ticksRemaining;
    private static int cooldownRemaining;

    private FrogTongueHud() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (ticksRemaining > 0) --ticksRemaining;
            if (cooldownRemaining > 0) --cooldownRemaining;
            if (!FrogPossessionCamera.isPossessing(client.player)) {
                ticksRemaining = 0;
                cooldownRemaining = 0;
            }
        });
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CROSSHAIR, PrettyFrogs.id("frog_tongue_pov"),
                (graphics, delta) -> draw(graphics, delta.getGameTimeDeltaTicks()));
    }

    /** Do not animate a tongue strike that the server is cooling down. */
    public static boolean startAttack() {
        if (cooldownRemaining > 0) return false;
        ticksRemaining = ANIMATION_TICKS;
        cooldownRemaining = ATTACK_COOLDOWN;
        return true;
    }

    private static float smooth(float x) {
        x = Math.max(0.0F, Math.min(1.0F, x));
        return x * x * (3.0F - 2.0F * x);
    }

    private static void draw(GuiGraphicsExtractor g, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        if (ticksRemaining <= 0 || mc.player == null || mc.gui.screen() != null
                || !mc.options.getCameraType().isFirstPerson()
                || !FrogPossessionCamera.isPossessing(mc.player)) return;

        float age = ANIMATION_TICKS - ticksRemaining + Math.max(0, Math.min(1, partialTick));
        // Fire out rapidly; linger only very briefly before snapping back.
        float reach = age < 3.3F
                ? smooth(age / 3.3F)
                : age < 4.8F ? 1.0F : 1.0F - smooth((age - 4.8F) / 5.2F);
        if (reach <= 0.01F) return;

        int mx = g.guiWidth() / 2;
        int centerY = g.guiHeight() / 2;
        // Frog mouth is only just below the camera, rather than at the very
        // bottom of the UI. Scale gracefully across GUI resolutions.
        int mouthY = Math.min(g.guiHeight() - 20, centerY + Math.min(73, g.guiHeight() / 3));
        int maxReach = Math.max(12, mouthY - centerY - 5);
        int length = Math.max(2, Math.round(maxReach * reach));

        // A slightly curving tapered ribbon, thick at the mouth, rounded at
        // the tip. Narrowing conveys foreshortening in frog-eye perspective.
        // Each slice uses a dark outline, pink underside, and light center.
        for (int i = 0; i <= length; i += 2) {
            float u = (float)i / Math.max(1, length);
            int y = mouthY - i;
            int curve = Math.round(3.0F * (float)Math.sin(u * Math.PI) * (1.0F - reach * 0.3F));
            int x = mx + curve;
            int width = Math.max(2, Math.round(8.0F * (1.0F - u) + 1.8F * u));
            g.fill(x - width - 1, y - 2, x + width + 1, y + 1, 0xFF662A47);
            g.fill(x - width, y - 2, x + width, y + 1, 0xFFDB5A80);
            int highlight = Math.max(1, width / 3);
            g.fill(x - highlight, y - 2, x + highlight, y, 0xFFFF9AB4);
        }
        int tipY = mouthY - length;
        int tipX = mx;
        g.fill(tipX - 3, tipY - 2, tipX + 3, tipY + 2, 0xFF9F3D66);
        g.fill(tipX - 2, tipY - 3, tipX + 2, tipY + 1, 0xFFFF8FB1);
        // Pink lower lip at the base gives the tongue a clear mouth anchor.
        g.fill(mx - 9, mouthY, mx + 9, mouthY + 2, 0xFF7D385B);
        g.fill(mx - 6, mouthY, mx + 6, mouthY + 1, 0xFFE67A9E);
    }
}
