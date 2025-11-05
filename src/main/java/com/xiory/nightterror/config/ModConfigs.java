package com.xiory.nightterror.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ModConfigs {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final ForgeConfigSpec.BooleanValue ENABLE_ECHOER;
    public static final ForgeConfigSpec.DoubleValue SPAWN_CHANCE;
    public static final ForgeConfigSpec.IntValue DARKNESS_DURATION_TICKS;
    public static final ForgeConfigSpec.BooleanValue ENABLE_REPELLENT;
    public static final ForgeConfigSpec.IntValue REPELLENT_DURATION_TICKS;
    public static final ForgeConfigSpec.DoubleValue REPELLENT_RADIUS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("echoer");
        ENABLE_ECHOER = builder
                .comment("Enable the Echoer from spawning naturally.")
                .define("enableEchoer", true);
        SPAWN_CHANCE = builder
                .comment("Chance per eligible player tick for an Echoer to spawn.")
                .defineInRange("spawnChance", 0.0004D, 0.0D, 1.0D);
        DARKNESS_DURATION_TICKS = builder
                .comment("Duration of darkness applied to players when the Echoer spawns.")
                .defineInRange("darknessDurationTicks", 200, 20, 2400);
        builder.pop();

        builder.push("repellent");
        ENABLE_REPELLENT = builder
                .comment("Enable the Echo Repellent item and repellent fields.")
                .define("enableRepellent", true);
        REPELLENT_DURATION_TICKS = builder
                .comment("Duration a repellent field remains active in ticks.")
                .defineInRange("repellentDurationTicks", 6000, 20, 72000);
        REPELLENT_RADIUS = builder
                .comment("Radius that a repellent field will block Echoer spawns.")
                .defineInRange("repellentRadius", 16.0D, 1.0D, 64.0D);
        builder.pop();

        COMMON_SPEC = builder.build();
    }

    private ModConfigs() {
    }
}
