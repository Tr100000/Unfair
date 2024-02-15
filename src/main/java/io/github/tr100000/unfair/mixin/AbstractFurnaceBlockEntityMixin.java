package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    @Inject(method = "getCookTime", at = @At("RETURN"), cancellable = true)
    private static void getLongerCookTime(CallbackInfoReturnable<Integer> info) {
        if (Unfair.enabled) {
            info.setReturnValue(info.getReturnValue() * 10);
        }
    }
}
