package io.github.tr100000.unfair.mixin.client;

import io.github.tr100000.unfair.things.UnfairUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.texture.MissingSprite;
import net.minecraft.util.Identifier;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {
    @ModifyVariable(method = "setShaderTexture", at = @At("HEAD"), ordinal = 0)
    private static Identifier oops(Identifier identifier) {
        return UnfairUtils.dontLoadTexture(identifier) ? MissingSprite.getMissingSpriteId() : identifier;
    }
}
