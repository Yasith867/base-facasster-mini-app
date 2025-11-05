package com.xiory.nightterror.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;

public final class VignetteOverlay {
    private VignetteOverlay() {
    }

    public static void render(GuiGraphics graphics, float partialTick) {
        float alpha = ClientEffects.getVignetteAlpha(partialTick);
        if (alpha <= 0.0F) {
            return;
        }

        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        int border = Math.round(Math.min(width, height) * 0.22F);
        int opaque = ((int) (alpha * 200.0F) & 0xFF) << 24;
        int transparent = 0x00000000;

        RenderSystem.enableBlend();
        graphics.fillGradient(0, 0, width, border, opaque, transparent);
        graphics.fillGradient(0, height - border, width, height, transparent, opaque);
        graphics.fillGradient(0, 0, border, height, opaque, transparent);
        graphics.fillGradient(width - border, 0, width, height, transparent, opaque);
        RenderSystem.disableBlend();
    }
}
