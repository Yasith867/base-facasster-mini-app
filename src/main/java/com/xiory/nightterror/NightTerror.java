package com.xiory.nightterror;

import com.xiory.nightterror.config.ModConfigs;
import com.xiory.nightterror.entity.EchoerEntity;
import com.xiory.nightterror.network.NetworkHandler;
import com.xiory.nightterror.network.packet.EchoerSpawnFXPacket;
import com.xiory.nightterror.registry.ModEntities;
import com.xiory.nightterror.registry.ModItems;
import com.xiory.nightterror.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Mod(NightTerror.MOD_ID)
public class NightTerror {
    public static final String MOD_ID = "nightterror";
    private static final Logger LOGGER = LoggerFactory.getLogger(NightTerror.class);

    public NightTerror() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ModEntities::registerAttributes);
        modEventBus.addListener(ModItems::buildCreativeTabContents);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigs.COMMON_SPEC);

        MinecraftForge.EVENT_BUS.addListener(this::onLevelTick);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }

    private void onLevelTick(final TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (!(event.level instanceof ServerLevel serverLevel) || serverLevel.isClientSide()) {
            return;
        }

        if (!ModConfigs.ENABLE_ECHOER.get()) {
            return;
        }

        if (serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
            return;
        }

        long dayTime = serverLevel.getDayTime() % 24000L;
        if (dayTime < 13000L) {
            return;
        }

        double spawnChance = ModConfigs.SPAWN_CHANCE.get();
        for (ServerPlayer player : serverLevel.players()) {
            if (player.isCreative() || player.isSpectator()) {
                continue;
            }

            if (serverLevel.random.nextDouble() >= spawnChance) {
                continue;
            }

            if (!serverLevel.getEntities(ModEntities.ECHOER.get(), player.getBoundingBox().inflate(24.0D), EntitySelector.NO_SPECTATORS).isEmpty()) {
                continue;
            }

            BlockPos spawnPos = findSpawnPosition(serverLevel, player);
            if (spawnPos == null) {
                continue;
            }

            if (isRepellentNearby(serverLevel, spawnPos)) {
                continue;
            }

            EchoerEntity echoer = ModEntities.ECHOER.get().create(serverLevel);
            if (echoer == null) {
                continue;
            }

            echoer.moveTo(spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D, serverLevel.random.nextFloat() * 360.0F, 0.0F);
            echoer.setTarget(player);

            if (serverLevel.addFreshEntity(echoer)) {
                LOGGER.debug("Spawned Echoer at {} for {}", spawnPos, player.getGameProfile().getName());
                int darknessDuration = ModConfigs.DARKNESS_DURATION_TICKS.get();
                applyDarkness(serverLevel, echoer.position(), darknessDuration);
                serverLevel.playSound(null, spawnPos, ModSounds.ECHOER_SPAWN_SWIRL.get(), SoundSource.HOSTILE, 1.2F, 0.9F + serverLevel.random.nextFloat() * 0.2F);
                serverLevel.playSound(null, spawnPos, ModSounds.ECHOER_SPAWN_SCREAM.get(), SoundSource.HOSTILE, 1.2F, 0.9F + serverLevel.random.nextFloat() * 0.2F);
                NetworkHandler.sendToTracking(echoer, new EchoerSpawnFXPacket(Math.min(60, Math.max(40, darknessDuration / 2))));
            }
        }
    }

    private void applyDarkness(ServerLevel level, Vec3 pos, int duration) {
        double radius = 24.0D;
        double radiusSqr = radius * radius;
        for (ServerPlayer player : level.players()) {
            if (player.distanceToSqr(pos) <= radiusSqr) {
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, duration, 0, false, true));
            }
        }
    }

    private boolean isRepellentNearby(ServerLevel level, BlockPos pos) {
        if (!ModConfigs.ENABLE_REPELLENT.get()) {
            return false;
        }

        double radius = ModConfigs.REPELLENT_RADIUS.get();
        AABB box = new AABB(pos).inflate(radius);
        return !level.getEntities(ModEntities.REPELLENT_FIELD.get(), box, field -> field.blocksSpawnAt(pos)).isEmpty();
    }

    private BlockPos findSpawnPosition(ServerLevel level, ServerPlayer player) {
        BlockPos base = player.blockPosition();
        RandomSource random = level.getRandom();
        for (int attempt = 0; attempt < 12; attempt++) {
            double angle = random.nextDouble() * Mth.TWO_PI;
            double distance = 8.0D + random.nextDouble() * 8.0D;
            int x = base.getX() + Mth.floor(Math.cos(angle) * distance);
            int z = base.getZ() + Mth.floor(Math.sin(angle) * distance);

            int topY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            BlockPos topPos = new BlockPos(x, topY, z);
            if (!level.getWorldBorder().isWithinBounds(topPos)) {
                continue;
            }

            if (!level.isEmptyBlock(topPos)) {
                topPos = topPos.above();
            }

            if (!level.isEmptyBlock(topPos) || !level.isEmptyBlock(topPos.above())) {
                continue;
            }

            if (level.getMaxLocalRawBrightness(topPos) > 7) {
                continue;
            }

            BlockPos below = topPos.below();
            if (!level.getBlockState(below).isFaceSturdy(level, below, Direction.UP)) {
                continue;
            }

            return topPos;
        }
        return null;
    }
}
