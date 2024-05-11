package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.UnfairDamageTypes;
import io.github.tr100000.unfair.things.UnfairUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World.ExplosionSourceType;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {
    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    private void isInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
        if (Unfair.enabled) {
            info.setReturnValue(false);
        }
    }

    @Inject(method = "canTakeDamage", at = @At("HEAD"), cancellable = true)
    private void canTakeDamage(CallbackInfoReturnable<Boolean> info) {
        if (Unfair.enabled) {
            info.setReturnValue(true);
        }
    }
    
    @Inject(method = "shouldDamagePlayer", at = @At("HEAD"), cancellable = true)
    private void shouldDamagePlayer(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        if (Unfair.enabled) {
            info.setReturnValue(true);
        }
    }
    
    @Inject(method = "addExperience", at = @At("HEAD"))
    private void addExperience(int experience, CallbackInfo info) {
        if (Unfair.enabled && experience > 0) {
            PlayerEntity player = (PlayerEntity)(Object)this;
            UnfairUtils.killPlayer(player, UnfairUtils.fromDamageType(player.getWorld(), UnfairDamageTypes.EXPERIENCE_COLLECT));
            player.getWorld().createExplosion(player, player.getX(), player.getY(), player.getZ(), 5, true, ExplosionSourceType.MOB);
        }
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), ordinal = 0)
    private float oops(float damage) {
        return 9999;
    }
}
