package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.ScreenShuffle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    abstract float getTickDelta();

    @Inject(method = "tick", at = @At("HEAD"))
    private void pleaseDont(CallbackInfo info) {
        if (Unfair.enabled) {
            ScreenShuffle.clientTickDelta = getTickDelta();
            ScreenShuffle.buttonTimeValue++;
            if (ScreenShuffle.buttonTimeValue >= ScreenShuffle.timeForEachButton) {
                ScreenShuffle.buttonTimeValue = 0;
                ScreenShuffle.shufflePositions();
            }
        }
    }

    @Inject(method = "setScreen", at = @At("RETURN"))
    private void setScreen(Screen screen, CallbackInfo info) {
        ScreenShuffle.positions.clear();
    }
}
