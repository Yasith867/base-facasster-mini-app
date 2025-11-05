package com.xiory.nightterror.network.packet;

import com.xiory.nightterror.client.overlay.ClientEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HalvingFlickerPacket {
    private final int durationTicks;

    public HalvingFlickerPacket(int durationTicks) {
        this.durationTicks = durationTicks;
    }

    public int getDurationTicks() {
        return durationTicks;
    }

    public static void encode(HalvingFlickerPacket packet, FriendlyByteBuf buffer) {
        buffer.writeVarInt(packet.durationTicks);
    }

    public static HalvingFlickerPacket decode(FriendlyByteBuf buffer) {
        return new HalvingFlickerPacket(buffer.readVarInt());
    }

    public static void handle(HalvingFlickerPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientEffects.triggerFlicker(packet.durationTicks)));
        context.setPacketHandled(true);
    }
}
