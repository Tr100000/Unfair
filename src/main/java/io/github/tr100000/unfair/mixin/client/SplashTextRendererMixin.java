package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.SplashTextRenderer;
import net.minecraft.util.Util;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.MathHelper;

@Mixin(SplashTextRenderer.class)
public class SplashTextRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void unfair(GuiGraphics graphics, int screenWidth, TextRenderer textRenderer, int alpha, CallbackInfo info) {
        if (Unfair.enabled) {
            final String splashText = "Unfair!";
            graphics.getMatrices().push();
            graphics.getMatrices().translate((float)screenWidth / 2.0F + 123.0F, 69.0F, 0.0F);
            graphics.getMatrices().multiply(Axis.Z_POSITIVE.rotationDegrees(-20.0F));
            float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Util.getMeasuringTimeMs() % 1000L) / 100.0F) * 0.1F);
            f *= 100.0F / (float)(textRenderer.getWidth(splashText) + 32);
            graphics.getMatrices().scale(f, f, f);
            graphics.drawCenteredShadowedText(textRenderer, splashText, 0, -8, 16711680 | alpha);
            graphics.getMatrices().pop();
            info.cancel();
        }
    }
}
