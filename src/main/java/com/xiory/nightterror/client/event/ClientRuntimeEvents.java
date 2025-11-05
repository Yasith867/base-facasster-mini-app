package com.xiory.nightterror.client.event;

import com.xiory.nightterror.NightTerror;
import com.xiory.nightterror.client.overlay.ClientEffects;
import com.xiory.nightterror.client.overlay.FlickerOverlay;
import com.xiory.nightterror.client.overlay.VignetteOverlay;
import com.xiory.nightterror.client.sound.EchoerAmbientLoopSound;
import com.xiory.nightterror.entity.EchoerEntity;
import com.xiory.nightterror.registry.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = NightTerror.MOD_ID, value = Dist.CLIENT)
public final class ClientRuntimeEvents {
    private ClientRuntimeEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }

        ClientEffects.tick();

        Set<Integer> tracked = new HashSet<>(ClientEffects.trackedEchoers());
        for (int id : tracked) {
            Entity entity = minecraft.level.getEntity(id);
            if (!(entity instanceof EchoerEntity echoer) || !echoer.isAlive()) {
                ClientEffects.untrackEchoer(id);
                continue;
            }

            if (minecraft.level.random.nextFloat() < 0.003F) {
                minecraft.level.playLocalSound(echoer.getX(), echoer.getY(), echoer.getZ(), ModSounds.ECHOER_IDLE_WHISPER.get(), SoundSource.HOSTILE, 0.5F, 0.9F + minecraft.level.random.nextFloat() * 0.2F, false);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        VignetteOverlay.render(event.getGuiGraphics(), event.getPartialTick());
        FlickerOverlay.render(event.getGuiGraphics(), event.getPartialTick());
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            return;
        }

        if (event.getEntity() instanceof EchoerEntity echoer) {
            if (!ClientEffects.trackedEchoers().contains(echoer.getId())) {
                ClientEffects.trackEchoer(echoer.getId());
                Minecraft.getInstance().getSoundManager().play(new EchoerAmbientLoopSound(echoer));
            }
        }
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            return;
        }

        ClientEffects.untrackEchoer(event.getEntity().getId());
    }
}
