package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.screen.recipe.book.RecipeBookWidget;

@Mixin(RecipeBookWidget.class)
public class RecipeBookWidgetMixin {
    @Inject(method = "isOpen", at = @At("HEAD"), cancellable = true)
    private void alwaysOpen(CallbackInfoReturnable<Boolean> info) {
        if (Unfair.enabled && ((RecipeBookWidgetAccessor)(Object)this).getSearchField() != null) {
            info.setReturnValue(true);
        }
    }
}
