package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.component.type.FoodComponent;

@Mixin(FoodComponent.class)
public abstract class FoodComponentMixin {
    @Inject(method = "nutrition", at = @At("RETURN"), cancellable = true)
    private void modifyNutrition(CallbackInfoReturnable<Integer> info) {
        if (Unfair.enabled) {
            info.setReturnValue(0);
        }
    }

    @Inject(method = "saturation", at = @At("RETURN"), cancellable = true)
    private void modifySaturation(CallbackInfoReturnable<Float> info) {
        if (Unfair.enabled) {
            info.setReturnValue(0F);
        }
    }

    @Inject(method = "canAlwaysEat", at = @At("HEAD"), cancellable = true)
    private void setAlwaysEdible(CallbackInfoReturnable<Boolean> info) {
        if (Unfair.enabled) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "eatSeconds", at = @At("HEAD"), cancellable = true)
    private void modifyEatTime(CallbackInfoReturnable<Float> info) {
        if (Unfair.enabled) {
            info.setReturnValue(20F);
        }
    }
}
