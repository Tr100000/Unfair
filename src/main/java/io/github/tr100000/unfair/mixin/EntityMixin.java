package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    abstract void setPosition(double x, double y, double z);

    @ModifyVariable(method = "dropStack", at = @At("HEAD"), ordinal = 0)
    private ItemStack replaceWithDirt(ItemStack stack) {
        return Unfair.enabled ? new ItemStack(Items.DIRT, stack.getCount()) : stack;
    }

    @Inject(method = "getMaxAir", at = @At("HEAD"), cancellable = true)
    private void noAir(CallbackInfoReturnable<Integer> info) {
        if (Unfair.enabled) {
            info.setReturnValue(10);
        }
    }

    @Inject(method = "getMinFreezeDamageTicks", at = @At("HEAD"), cancellable = true)
    private void freeze(CallbackInfoReturnable<Integer> info) {
        if (Unfair.enabled) {
            info.setReturnValue(10);
        }
    }

    @Inject(method = "doesRenderOnFire", at = @At("HEAD"), cancellable = true)
    private void fire(CallbackInfoReturnable<Boolean> info) {
        if (Unfair.enabled && !((Object)this instanceof ItemEntity)) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "getRenderDistanceMultiplier", at = @At("HEAD"), cancellable = true)
    private static void performance(CallbackInfoReturnable<Double> info) {
        if (Unfair.enabled) {
            info.setReturnValue(0.05);
        }
    }
}
