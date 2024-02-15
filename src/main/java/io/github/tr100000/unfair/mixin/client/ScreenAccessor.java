package io.github.tr100000.unfair.mixin.client;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;

@Mixin(Screen.class)
public interface ScreenAccessor {
    @Accessor
    List<Drawable> getDrawables();
}
