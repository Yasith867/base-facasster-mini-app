package com.xiory.nightterror.entity.client;

import com.xiory.nightterror.NightTerror;
import com.xiory.nightterror.entity.DrHalvingEntity;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DrHalvingRenderer extends HumanoidMobRenderer<DrHalvingEntity, ZombieModel<DrHalvingEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(NightTerror.MOD_ID, "textures/entity/dr_halving.png");

    public DrHalvingRenderer(EntityRendererProvider.Context context) {
        super(context, new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.45F);
    }

    @Override
    public ResourceLocation getTextureLocation(DrHalvingEntity entity) {
        return TEXTURE;
    }

}
