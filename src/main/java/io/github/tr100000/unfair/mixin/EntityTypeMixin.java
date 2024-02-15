package io.github.tr100000.unfair.mixin;

import java.util.function.Consumer;

import io.github.tr100000.unfair.Unfair;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.TntEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

@Mixin(EntityType.class)
public class EntityTypeMixin {
    @Inject(method = "Lnet/minecraft/entity/EntityType;spawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/nbt/NbtCompound;Ljava/util/function/Consumer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/SpawnReason;ZZ)Lnet/minecraft/entity/Entity;", at = @At("HEAD"), cancellable = true)
    private void tnt(ServerWorld world, @Nullable NbtCompound nbt, @Nullable Consumer<Entity> spawnConfig, BlockPos pos, SpawnReason reason, boolean alignPosition, boolean invertY, CallbackInfoReturnable<Entity> info) {
        if (Unfair.enabled && reason == SpawnReason.SPAWN_EGG) {
            if (nbt == null) {
                nbt = new NbtCompound();
            }
            TntEntity tnt = EntityType.TNT.create(world, nbt, null, pos, reason, alignPosition, invertY);
            if (tnt != null) {
                world.spawnEntityAndPassengers(tnt);
                tnt.setFuse(20);
                double d = world.random.nextDouble() * Math.PI * 2;
                tnt.setVelocity(-Math.sin(d) * 0.02, 0.2F, -Math.cos(d) * 0.02);
            }
            info.setReturnValue(tnt);
        }
    }
}
