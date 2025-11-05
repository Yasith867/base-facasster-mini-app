package com.xiory.nightterror.entity.client;

import com.xiory.nightterror.NightTerror;
import com.xiory.nightterror.entity.EchoerEntity;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;

public class EchoerRenderer extends HumanoidMobRenderer<EchoerEntity, ZombieModel<EchoerEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(NightTerror.MOD_ID, "textures/entity/echoer.png");

    public EchoerRenderer(EntityRendererProvider.Context context) {
        super(context, new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.6F);
        this.addLayer(new HumanoidArmorLayer<>(this, new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)), new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR))));
    }

    @Override
    public ResourceLocation getTextureLocation(EchoerEntity entity) {
        return TEXTURE;
    }
}
