package com.xiory.nightterror.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public final class FlickerOverlay {
    private static final RandomSource RANDOM = RandomSource.create();

    private FlickerOverlay() {
    }

    public static void render(GuiGraphics graphics, float partialTick) {
        if (!ClientEffects.isFlickerActive()) {
            return;
        }

        float progress = ClientEffects.getFlickerProgress(partialTick);
        float sine = (Mth.sin(progress * Mth.TWO_PI * 2.5F) + 1.0F) * 0.5F;
        float alpha = Mth.clamp(0.35F + sine * 0.35F + RANDOM.nextFloat() * 0.15F, 0.0F, 0.85F);
        int color = ((int) (alpha * 255.0F) & 0xFF) << 24 | 0x00FFFFFF;

        RenderSystem.enableBlend();
        graphics.fill(0, 0, graphics.guiWidth(), graphics.guiHeight(), color);
        RenderSystem.disableBlend();
    }
}
