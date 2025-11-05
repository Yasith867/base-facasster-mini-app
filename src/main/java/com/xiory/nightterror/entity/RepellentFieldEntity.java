package com.xiory.nightterror.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RepellentFieldEntity extends Entity {
    private double radius = 16.0D;
    private int duration = 6000;
    private boolean particlesSpawned;

    public RepellentFieldEntity(EntityType<? extends RepellentFieldEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setInvisible(true);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.radius = tag.getDouble("Radius");
        this.duration = tag.getInt("Duration");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putDouble("Radius", this.radius);
        tag.putInt("Duration", this.duration);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.particlesSpawned && this.level() instanceof ServerLevel serverLevel) {
            this.particlesSpawned = true;
            double step = Math.PI * 2 / 20;
            for (int i = 0; i < 20; i++) {
                double angle = i * step;
                double x = this.getX() + Math.cos(angle) * this.radius;
                double z = this.getZ() + Math.sin(angle) * this.radius;
                serverLevel.sendParticles(ParticleTypes.ENCHANT, x, this.getY(), z, 3, 0.2D, 0.1D, 0.2D, 0.0D);
            }
        }

        if (!this.level().isClientSide) {
            if (this.duration-- <= 0) {
                this.discard();
            }
        }
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean blocksSpawnAt(BlockPos pos) {
        Vec3 center = Vec3.atCenterOf(pos);
        return center.distanceTo(this.position()) <= this.radius;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }
}
