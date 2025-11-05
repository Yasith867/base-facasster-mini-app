package com.xiory.nightterror.item;

import com.xiory.nightterror.config.ModConfigs;
import com.xiory.nightterror.entity.RepellentFieldEntity;
import com.xiory.nightterror.registry.ModEntities;
import com.xiory.nightterror.registry.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EchoRepellentItem extends Item {
    public EchoRepellentItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!ModConfigs.ENABLE_REPELLENT.get()) {
            return InteractionResultHolder.pass(stack);
        }

        player.getCooldowns().addCooldown(this, 20);

        boolean consumed = false;
        if (!level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) level;
            RepellentFieldEntity field = ModEntities.REPELLENT_FIELD.get().create(serverLevel);
            if (field != null) {
                field.setRadius(ModConfigs.REPELLENT_RADIUS.get());
                field.setDuration(ModConfigs.REPELLENT_DURATION_TICKS.get());
                field.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
                if (serverLevel.addFreshEntity(field)) {
                    serverLevel.playSound(null, player.blockPosition(), ModSounds.ECHOER_WORLD_PULSE.get(), SoundSource.PLAYERS, 0.7F, 1.0F);
                    consumed = true;
                }
            }
        }

        if (consumed && !player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
