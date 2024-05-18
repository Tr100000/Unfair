package io.github.tr100000.unfair.mixin;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.item.Item;

@Mixin(Item.Settings.class)
public abstract class ItemSettingsMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        if (Unfair.enabled) {
            Item.Settings settings = (Item.Settings)(Object)this;
            settings.maxCount(1);
            settings.maxDamage(1);
        }
    }

    @ModifyVariable(method = "maxCount", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int realism(int count) {
        return Unfair.enabled ? 1 : count;
    }

    @ModifyVariable(method = "maxDamage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int realism2(int max) {
        return Unfair.enabled ? 1 : max;
    }
}
