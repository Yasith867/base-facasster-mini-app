package com.xiory.nightterror.client.event;

import com.xiory.nightterror.NightTerror;
import com.xiory.nightterror.entity.client.DrHalvingRenderer;
import com.xiory.nightterror.entity.client.EchoerRenderer;
import com.xiory.nightterror.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NightTerror.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {
    private ClientModEvents() {
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.ECHOER.get(), EchoerRenderer::new);
        event.registerEntityRenderer(ModEntities.DR_HALVING.get(), DrHalvingRenderer::new);
    }
}
