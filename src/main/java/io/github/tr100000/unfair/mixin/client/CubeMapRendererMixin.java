package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.ScreenUtils;

import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;

@Mixin(CubeMapRenderer.class)
public abstract class CubeMapRendererMixin {
    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;applyModelViewMatrix()V", shift = At.Shift.BEFORE))
    private void draw(MinecraftClient client, float x, float y, float alpha, CallbackInfo callback, @Local Matrix4fStack matrices) {
        if (Unfair.enabled) {
            matrices.rotateZ(ScreenUtils.getRotation());
        }
    }
}
