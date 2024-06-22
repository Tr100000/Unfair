package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.ScreenUtils;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderTickCounter;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Final
    private RenderTickCounter.Dynamic renderTickCounter;
    @Shadow
    private boolean paused;

    @Inject(method = "render", at = @At("HEAD"))
    private void render(boolean tick, CallbackInfo info) {
        if (Unfair.enabled) {
            ScreenUtils.clientTickDelta = renderTickCounter.getTickDelta(paused);
            ScreenUtils.clientPaused = paused;
            if (tick) {
                ScreenUtils.buttonTimeValue++;
                if (ScreenUtils.buttonTimeValue >= ScreenUtils.timeForEachButton) {
                    ScreenUtils.buttonTimeValue = 0;
                    ScreenUtils.shufflePositions();
                }
            }
        }
    }

    @Inject(method = "setScreen", at = @At("RETURN"))
    private void setScreen(Screen screen, CallbackInfo info) {
        ScreenUtils.positions.clear();
    }
}
