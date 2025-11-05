package com.xiory.nightterror.registry;

import com.xiory.nightterror.NightTerror;
import com.xiory.nightterror.item.EchoRepellentItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NightTerror.MOD_ID);

    public static final RegistryObject<Item> ECHO_FRAGMENT = ITEMS.register("echo_fragment", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ECHO_REPELLENT = ITEMS.register("echo_repellent", () -> new EchoRepellentItem(new Item.Properties().stacksTo(16)));

    private ModItems() {
    }

    public static void buildCreativeTabContents(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ECHO_FRAGMENT);
            event.accept(ECHO_REPELLENT);
        }
    }
}
