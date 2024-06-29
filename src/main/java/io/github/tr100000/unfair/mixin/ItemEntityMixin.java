package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.UnfairUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends EntityMixin {
    @Shadow
    abstract Entity getOwner();

    @ModifyVariable(method = "setStack", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private ItemStack oops(ItemStack stack) {
        return Unfair.enabled ? new ItemStack(UnfairUtils.getRandomItem(), stack.getCount()) : stack;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo callback) {
        Entity owner = getOwner();
        if (owner != null) {
            Vec3d ownerPos = owner.getPos();
            setPosition(ownerPos.x, ownerPos.y, ownerPos.z);
        }
    }
}
