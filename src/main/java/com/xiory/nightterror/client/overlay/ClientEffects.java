package com.xiory.nightterror.client.overlay;

import net.minecraft.util.Mth;

import java.util.HashSet;
import java.util.Set;

public final class ClientEffects {
    private static int vignetteTicks;
    private static int vignetteDuration;
    private static int flickerTicks;
    private static int flickerDuration;

    private static final Set<Integer> LOOPING_ECHOERS = new HashSet<>();

    private ClientEffects() {
    }

    public static void triggerVignette(int duration) {
        vignetteDuration = Math.max(duration, 1);
        vignetteTicks = duration;
    }

    public static void triggerFlicker(int duration) {
        flickerDuration = Math.max(duration, 1);
        flickerTicks = duration;
    }

    public static void tick() {
        if (vignetteTicks > 0) {
            vignetteTicks--;
        }
        if (flickerTicks > 0) {
            flickerTicks--;
        }
    }

    public static boolean isVignetteActive() {
        return vignetteTicks > 0;
    }

    public static boolean isFlickerActive() {
        return flickerTicks > 0;
    }

    public static float getVignetteAlpha(float partialTick) {
        if (!isVignetteActive()) {
            return 0.0F;
        }
        float remaining = vignetteTicks - partialTick;
        float progress = remaining / (float) vignetteDuration;
        return Mth.clamp(progress, 0.0F, 1.0F);
    }

    public static float getFlickerProgress(float partialTick) {
        if (!isFlickerActive()) {
            return 0.0F;
        }
        float remaining = flickerTicks - partialTick;
        float progress = 1.0F - (remaining / (float) flickerDuration);
        return Mth.clamp(progress, 0.0F, 1.0F);
    }

    public static void trackEchoer(int entityId) {
        LOOPING_ECHOERS.add(entityId);
    }

    public static void untrackEchoer(int entityId) {
        LOOPING_ECHOERS.remove(entityId);
    }

    public static Set<Integer> trackedEchoers() {
        return Set.copyOf(LOOPING_ECHOERS);
    }
}
