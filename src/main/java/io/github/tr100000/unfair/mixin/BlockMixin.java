package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.world.explosion.Explosion;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "shouldDropItemsOnExplosion", at = @At("HEAD"), cancellable = true)
    private void shouldDropItemsOnExplosion(Explosion explosion, CallbackInfoReturnable<Boolean> info) {
        if (Unfair.enabled) {
            info.setReturnValue(false);
        }
    }

    @Inject(method = "getSlipperiness", at = @At("HEAD"), cancellable = true)
    private void slippery(CallbackInfoReturnable<Float> info) {
        if (Unfair.enabled) {
            info.setReturnValue(1.0F);
        }
    }
}
