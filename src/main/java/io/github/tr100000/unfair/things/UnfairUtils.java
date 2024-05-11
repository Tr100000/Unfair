package io.github.tr100000.unfair.things;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public final class UnfairUtils {
    private UnfairUtils() {}

    public static void killPlayer(Entity player, DamageSource source) {
        player.damage(source, Float.MAX_VALUE);
    }

    public static DamageSource fromDamageType(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getEntry(key).orElseThrow());
    }
}
