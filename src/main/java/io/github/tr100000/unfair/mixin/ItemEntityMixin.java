package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @ModifyVariable(method = "setStack", at = @At("HEAD"), ordinal = 0)
    public ItemStack oops(ItemStack stack) {
        return Unfair.enabled ? new ItemStack(Items.POISONOUS_POTATO, stack.getCount()) : stack;
    }
}
