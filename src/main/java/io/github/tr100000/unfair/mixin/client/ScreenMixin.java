package io.github.tr100000.unfair.mixin.client;

import java.util.List;
import java.util.stream.Collectors;

import io.github.tr100000.unfair.Unfair;
import io.github.tr100000.unfair.things.ScreenUtils;

import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow
    protected MinecraftClient client;
    @Shadow @Final
    protected List<Drawable> drawables;
    @Shadow
    protected int width;
    @Shadow
    protected int height;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void uhoh(DrawContext draw, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (Unfair.enabled) {
            MatrixStack matrices = draw.getMatrices();
            matrices.translate(width / 2f, height / 2f, 0);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(ScreenUtils.getRotation(delta)));
            matrices.translate(-width / 2f, -height / 2f, 0);

            List<Widget> widgets = getWidgets();

            if (ScreenUtils.positions.isEmpty()) {
                updateStoredPositions(widgets);
            }

            for (int i = 0; i < widgets.size(); i++) {
                if (i >= ScreenUtils.positions.size()) {
                    updateStoredPositions(widgets);
                    break;
                }
                Widget widget = widgets.get(i);
                Vector2i pos = ScreenUtils.positions.get(i);
                widget.setPosition(pos.x() - widget.getWidth() / 2, pos.y() - widget.getHeight() / 2);
            }
        }
    }

    @Inject(method = "resize", at = @At("TAIL"))
    private void onResize(MinecraftClient client, int width, int height, CallbackInfo callback) {
        if (this.width != width || this.height != height) {
            updateStoredPositions(getWidgets());
        }
    }

    @Unique
    private List<Widget> getWidgets() {
        return drawables.stream().filter(drawable -> drawable instanceof Widget).map(widget -> (Widget)widget).collect(Collectors.toUnmodifiableList());
    }

    @Unique
    private void updateStoredPositions(List<Widget> widgets) {
        ScreenUtils.positions.clear();
        widgets.forEach(widget -> {
            ScreenUtils.positions.add(new Vector2i(widget.getX() + widget.getWidth() / 2, widget.getY() + widget.getHeight() / 2));
        });
        ScreenUtils.shufflePositions();
    }
}
