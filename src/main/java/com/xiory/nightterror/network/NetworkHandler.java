package com.xiory.nightterror.network;

import com.xiory.nightterror.NightTerror;
import com.xiory.nightterror.network.packet.EchoerSpawnFXPacket;
import com.xiory.nightterror.network.packet.HalvingFlickerPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NightTerror.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;
    private static boolean registered;

    private NetworkHandler() {
    }

    public static void register() {
        if (registered) {
            return;
        }
        registered = true;

        CHANNEL.registerMessage(nextId(), EchoerSpawnFXPacket.class, EchoerSpawnFXPacket::encode, EchoerSpawnFXPacket::decode, EchoerSpawnFXPacket::handle);
        CHANNEL.registerMessage(nextId(), HalvingFlickerPacket.class, HalvingFlickerPacket::encode, HalvingFlickerPacket::decode, HalvingFlickerPacket::handle);
    }

    private static int nextId() {
        return packetId++;
    }

    public static <MSG> void sendToTracking(Entity entity, MSG message) {
        if (entity.level().isClientSide()) {
            return;
        }
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), message);
    }

    public static <MSG> void sendToAllAround(ServerLevel level, Vec3 pos, double range, MSG message) {
        CHANNEL.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(pos.x, pos.y, pos.z, range, level.dimension())), message);
    }
}
