package com.xiory.nightterror.registry;

import com.xiory.nightterror.NightTerror;
import com.xiory.nightterror.entity.DrHalvingEntity;
import com.xiory.nightterror.entity.EchoerEntity;
import com.xiory.nightterror.entity.RepellentFieldEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NightTerror.MOD_ID);

    public static final RegistryObject<EntityType<EchoerEntity>> ECHOER = ENTITY_TYPES.register("echoer",
            () -> EntityType.Builder.of(EchoerEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build(location("echoer")));

    public static final RegistryObject<EntityType<DrHalvingEntity>> DR_HALVING = ENTITY_TYPES.register("dr_halving",
            () -> EntityType.Builder.of(DrHalvingEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.8F)
                    .clientTrackingRange(8)
                    .build(location("dr_halving")));

    public static final RegistryObject<EntityType<RepellentFieldEntity>> REPELLENT_FIELD = ENTITY_TYPES.register("repellent_field",
            () -> EntityType.Builder.<RepellentFieldEntity>of(RepellentFieldEntity::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(32)
                    .updateInterval(20)
                    .noSummon()
                    .build(location("repellent_field")));

    private ModEntities() {
    }

    private static String location(String path) {
        return new ResourceLocation(NightTerror.MOD_ID, path).toString();
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ECHOER.get(), EchoerEntity.createAttributes().build());
        event.put(DR_HALVING.get(), DrHalvingEntity.createAttributes().build());
    }
}
