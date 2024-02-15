package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.UnfairDamageTypes;
import io.github.tr100000.unfair.things.UnfairUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BedBlock;
import net.minecraft.entity.Entity;

@Mixin(BedBlock.class)
public class BedBlockMixin {
    @Inject(method = "isBedWorking", at = @At("HEAD"), cancellable = true)
    private static void broken(CallbackInfoReturnable<Boolean> info) {
        if (Unfair.enabled) {
            info.setReturnValue(false);
        }
    }

    @Inject(method = "bounceEntity", at = @At("HEAD"), cancellable = true)
    private void killPlayer(Entity entity, CallbackInfo info) {
        if (Unfair.enabled) {
            UnfairUtils.killPlayer(entity, UnfairUtils.fromDamageType(entity.getWorld(), UnfairDamageTypes.BED));
            info.cancel();
        }
    }
}
