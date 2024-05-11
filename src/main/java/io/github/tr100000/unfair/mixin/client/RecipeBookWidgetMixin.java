package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.Unfair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;

@Mixin(RecipeBookWidget.class)
public class RecipeBookWidgetMixin {
    @Shadow
    private TextFieldWidget searchField;

    @Inject(method = "isOpen", at = @At("HEAD"), cancellable = true)
    private void alwaysOpen(CallbackInfoReturnable<Boolean> info) {
        if (Unfair.enabled && searchField != null) {
            info.setReturnValue(true);
        }
    }
}
