package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.things.UnfairUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

@Mixin(TextureManager.class)
public class TextureManagerMixin {
    @Inject(method = "loadTexture", at = @At("HEAD"), cancellable = true)
    private void loadTexture(Identifier id, AbstractTexture texture, CallbackInfoReturnable<AbstractTexture> callback) {
        if (UnfairUtils.dontLoadTexture(id)) {
            callback.setReturnValue(MissingSprite.getMissingSpriteTexture());
        }
    }
}
