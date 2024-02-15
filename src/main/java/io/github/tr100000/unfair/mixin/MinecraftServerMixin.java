package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @ModifyVariable(method = "setDifficulty", at = @At("HEAD"), ordinal = 0)
    private Difficulty alwaysHard(Difficulty difficulty) {
        return Unfair.enabled ? Difficulty.HARD : difficulty;
    }
}
