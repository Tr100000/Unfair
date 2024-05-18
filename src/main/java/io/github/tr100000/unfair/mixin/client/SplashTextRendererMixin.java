package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.ScreenUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Mixin(SplashTextRenderer.class)
public abstract class SplashTextRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void unfair(DrawContext draw, int screenWidth, TextRenderer textRenderer, int alpha, CallbackInfo info) {
        if (Unfair.enabled) {
            final String splashText = "Unfair!";
            draw.getMatrices().push();
            draw.getMatrices().translate((float)screenWidth / 2.0F + 123.0F, 69.0F, 0.0F);
            draw.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotation(ScreenUtils.getRotation()));
            float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Util.getMeasuringTimeMs() % 1000L) / 100.0F) * 0.1F);
            f *= 100.0F / (float)(textRenderer.getWidth(splashText) + 32);
            draw.getMatrices().scale(f, f, f);
            draw.drawCenteredTextWithShadow(textRenderer, splashText, 0, -8, 16711680 | alpha);
            draw.getMatrices().pop();
            info.cancel();
        }
    }
}
