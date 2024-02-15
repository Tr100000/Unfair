package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.network.packet.c2s.play.DifficultyUpdateC2SPacket;
import net.minecraft.world.Difficulty;

@Mixin(DifficultyUpdateC2SPacket.class)
public class DifficultyUpdateC2SPacketMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0)
    private static Difficulty alwaysHard(Difficulty difficulty) {
        return Unfair.enabled ? Difficulty.HARD : difficulty;
    }
}
