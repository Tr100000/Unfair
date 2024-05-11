package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.ScreenShuffle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

@Mixin(CubeMapRenderer.class)
public abstract class CubeMapRendererMixin {
    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;"))
    private void draw(MinecraftClient client, float x, float y, float alpha, CallbackInfo callback, @Local MatrixStack matrices) {
        if (Unfair.enabled) {
            matrices.multiply(RotationAxis.NEGATIVE_Z.rotation(ScreenShuffle.getRotation()));
        }
    }
}
