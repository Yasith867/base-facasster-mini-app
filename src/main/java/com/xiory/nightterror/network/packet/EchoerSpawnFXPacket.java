package com.xiory.nightterror.network.packet;

import com.xiory.nightterror.client.overlay.ClientEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EchoerSpawnFXPacket {
    private final int durationTicks;

    public EchoerSpawnFXPacket(int durationTicks) {
        this.durationTicks = durationTicks;
    }

    public int getDurationTicks() {
        return durationTicks;
    }

    public static void encode(EchoerSpawnFXPacket packet, FriendlyByteBuf buffer) {
        buffer.writeVarInt(packet.durationTicks);
    }

    public static EchoerSpawnFXPacket decode(FriendlyByteBuf buffer) {
        return new EchoerSpawnFXPacket(buffer.readVarInt());
    }

    public static void handle(EchoerSpawnFXPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientEffects.triggerVignette(packet.durationTicks)));
        context.setPacketHandled(true);
    }
}
