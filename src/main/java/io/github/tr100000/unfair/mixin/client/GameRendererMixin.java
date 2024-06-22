package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.ScreenUtils;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final
    protected MinecraftClient client;

    @ModifyVariable(method = "renderWorld", at = @At("STORE"))
    private MatrixStack modifyMatrices(MatrixStack matrices, RenderTickCounter tickCounter) {
        if (Unfair.enabled && !client.player.isOnGround()) {
            matrices.multiply(RotationAxis.NEGATIVE_Z.rotation(ScreenUtils.getRotation(tickCounter.getTickDelta(ScreenUtils.clientPaused))));
        }
        return matrices;
    }
}
