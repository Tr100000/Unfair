package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.ScreenShuffle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void pleaseDont(CallbackInfo info) {
        if (Unfair.enabled) {
            ScreenShuffle.otherTimeThing++;
            if (ScreenShuffle.otherTimeThing >= ScreenShuffle.timeForEachButton) {
                ScreenShuffle.otherTimeThing = 0;
                ScreenShuffle.timeValue++;
                ScreenShuffle.shufflePositions();
            }
        }
    }

    @Inject(method = "setScreen", at = @At("RETURN"))
    private void setScreen(Screen screen, CallbackInfo info) {
        ScreenShuffle.positions.clear();
    }
}
