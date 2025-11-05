package com.xiory.nightterror.client.sound;

import com.xiory.nightterror.entity.EchoerEntity;
import com.xiory.nightterror.registry.ModSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;

public class EchoerAmbientLoopSound extends AbstractTickableSoundInstance {
    private final EchoerEntity echoer;

    public EchoerAmbientLoopSound(EchoerEntity echoer) {
        super(ModSounds.ECHOER_AMBIENT_LOOP.get(), SoundSource.HOSTILE, echoer.level().getRandom());
        this.echoer = echoer;
        this.looping = true;
        this.volume = 0.6F;
        this.pitch = 0.9F;
    }

    @Override
    public void tick() {
        if (this.echoer.isRemoved() || !this.echoer.isAlive()) {
            this.stop();
            return;
        }

        this.x = (float) this.echoer.getX();
        this.y = (float) this.echoer.getY();
        this.z = (float) this.echoer.getZ();
    }
}
