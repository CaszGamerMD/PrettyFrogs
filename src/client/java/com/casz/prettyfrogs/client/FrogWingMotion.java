package com.casz.prettyfrogs.client;

import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.phys.Vec3;

/**
 * Lightweight, per-frog client animation envelope. A WeakHashMap avoids
 * retaining frogs once they unload. Rendering may happen more than once per
 * tick; using fractional tick time keeps opening/folding frame-rate independent.
 */
public final class FrogWingMotion {
    private static final Map<Frog, Motion> MOTIONS = new WeakHashMap<>();
    private static final double MOVEMENT_THRESHOLD_SQR = 1.0E-7D;

    private FrogWingMotion() {}

    public static float update(Frog frog, float partialTicks) {
        Vec3 velocity = frog.getDeltaMovement();
        double dx = frog.getX() - frog.xo;
        double dy = frog.getY() - frog.yo;
        double dz = frog.getZ() - frog.zo;
        boolean moving = velocity.lengthSqr() > MOVEMENT_THRESHOLD_SQR
                || dx * dx + dy * dy + dz * dz > MOVEMENT_THRESHOLD_SQR;
        float time = frog.tickCount + partialTicks;
        Motion motion = MOTIONS.get(frog);
        if (motion == null || time < motion.lastTime) {
            motion = new Motion(time, moving ? 1.0F : 0.0F);
            MOTIONS.put(frog, motion);
        } else {
            float delta = Mth.clamp(time - motion.lastTime, 0.0F, 5.0F);
            // Spread promptly when movement begins; fold more gently at rest.
            double retention = Math.pow(moving ? 0.48D : 0.68D, delta);
            motion.spread = (float)((moving ? 1.0D : 0.0D)
                    + (motion.spread - (moving ? 1.0D : 0.0D)) * retention);
            motion.lastTime = time;
        }
        return motion.spread;
    }

    private static final class Motion {
        private float lastTime;
        private float spread;

        private Motion(float lastTime, float spread) {
            this.lastTime = lastTime;
            this.spread = spread;
        }
    }
}
