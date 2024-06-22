package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void flipRender(DrawContext draw, RenderTickCounter tickCounter, CallbackInfo callback) {
        if (Unfair.enabled) {
            final int width = draw.getScaledWindowWidth();
            final int height = draw.getScaledWindowHeight();
            MatrixStack matrices = draw.getMatrices();
            matrices.translate(width / 2f, height / 2f, 0);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
            matrices.translate(-width / 2f, -height / 2f, 0);
        }
    }
}
