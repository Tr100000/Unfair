package io.github.tr100000.unfair.mixin.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.ScreenShuffle;

import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void uhoh(GuiGraphics graphics, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (Unfair.enabled) {
            ScreenAccessor accessor = (ScreenAccessor)this;
            List<Drawable> drawables = new ArrayList<>(accessor.getDrawables());
            List<Widget> widgets = drawables.stream().filter(drawable -> drawable instanceof Widget).map(widget -> (Widget)widget).collect(Collectors.toUnmodifiableList());

            if (ScreenShuffle.positions.isEmpty()) {
                ScreenShuffle.positions.clear();
                widgets.forEach(widget -> {
                    ScreenShuffle.positions.add(new Vector2i(widget.getX() + widget.getWidth() / 2, widget.getY() + widget.getHeight() / 2));
                });
                ScreenShuffle.shufflePositions();
            }

            for (int i = 0; i < widgets.size(); i++) {
                if (i >= ScreenShuffle.positions.size()) {
                    ScreenShuffle.positions.clear();
                    break;
                }
                Widget widget = widgets.get(i);
                Vector2i pos = ScreenShuffle.positions.get(i);
                widget.setPosition(pos.x() - widget.getWidth() / 2, pos.y() - widget.getHeight() / 2);
            }
        }
    }
}
