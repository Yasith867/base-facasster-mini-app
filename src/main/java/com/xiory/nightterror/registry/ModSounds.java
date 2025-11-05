package com.xiory.nightterror.registry;

import com.xiory.nightterror.NightTerror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, NightTerror.MOD_ID);

    public static final RegistryObject<SoundEvent> ECHOER_AMBIENT_LOOP = register("echoer_ambient_loop");
    public static final RegistryObject<SoundEvent> ECHOER_SPAWN_SWIRL = register("echoer_spawn_swirl");
    public static final RegistryObject<SoundEvent> ECHOER_SPAWN_SCREAM = register("echoer_spawn_scream");
    public static final RegistryObject<SoundEvent> ECHOER_IDLE_WHISPER = register("echoer_idle_whisper");
    public static final RegistryObject<SoundEvent> ECHOER_ATTACK_SCREECH = register("echoer_attack_screech");
    public static final RegistryObject<SoundEvent> ECHOER_DISAPPEAR_POP = register("echoer_disappear_pop");
    public static final RegistryObject<SoundEvent> ECHO_FRAGMENT_PICKUP = register("echo_fragment_pickup");
    public static final RegistryObject<SoundEvent> HALVING_VOICE_LINE = register("halving_voice_line");
    public static final RegistryObject<SoundEvent> ECHOER_WORLD_PULSE = register("echoer_world_pulse");

    private ModSounds() {
    }

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(NightTerror.MOD_ID, name)));
    }
}
