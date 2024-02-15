package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "hasNoDrag", at = @At("HEAD"), cancellable = true)
    private void hasDrag(CallbackInfoReturnable<Boolean> info) {
        if (Unfair.enabled) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "getStepHeight", at = @At("HEAD"), cancellable = true)
    private void getStepHeight(CallbackInfoReturnable<Float> info) {
        if (Unfair.enabled) {
            info.setReturnValue(100.0F);
        }
    }
}
