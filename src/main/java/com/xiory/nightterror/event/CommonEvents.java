package com.xiory.nightterror.event;

import com.xiory.nightterror.NightTerror;
import com.xiory.nightterror.registry.ModItems;
import com.xiory.nightterror.registry.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NightTerror.MOD_ID)
public final class CommonEvents {
    private CommonEvents() {
    }

    @SubscribeEvent
    public static void onItemPickup(EntityItemPickupEvent event) {
        ItemStack stack = event.getItem().getItem();
        if (stack.is(ModItems.ECHO_FRAGMENT.get())) {
            Player player = event.getEntity();
            player.level().playSound(null, player.blockPosition(), ModSounds.ECHO_FRAGMENT_PICKUP.get(), SoundSource.PLAYERS, 0.5F, 1.1F);
        }
    }
}
