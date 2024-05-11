package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.ScreenShuffle;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final
    protected MinecraftClient client;

    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;", ordinal = 2))
    private void renderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo callback) {
        if (Unfair.enabled && !client.player.isOnGround()) {
            matrices.multiply(RotationAxis.NEGATIVE_Z.rotation(ScreenShuffle.getRotation(tickDelta)));
        }
    }
}
