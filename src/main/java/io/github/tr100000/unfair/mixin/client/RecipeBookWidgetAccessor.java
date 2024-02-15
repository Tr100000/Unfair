package io.github.tr100000.unfair.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.recipe.book.RecipeBookWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;

@Mixin(RecipeBookWidget.class)
public interface RecipeBookWidgetAccessor {
    @Accessor
    TextFieldWidget getSearchField();
}
