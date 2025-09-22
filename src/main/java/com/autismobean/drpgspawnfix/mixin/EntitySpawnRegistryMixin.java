package com.autismobean.drpgspawnfix.mixin;

import com.autismobean.drpgspawnfix.SpawnBlacklist;
import divinerpg.registry.EntitySpawnRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Stop DivineRPG from registering the 3 water mobs in biome spawn lists.
 * No runtime cancel here, so MultiMob/InControl can control them.
 */
@Mixin(value = EntitySpawnRegistry.class, remap = false)
public abstract class EntitySpawnRegistryMixin {

    @Redirect(
            method = "initSpawns",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/common/registry/EntityRegistry;addSpawn(Ljava/lang/Class;IIILnet/minecraft/entity/EnumCreatureType;[Lnet/minecraft/world/biome/Biome;)V"
            ),
            require = 0
    )
    private static void drpg$filterAddSpawn(
            Class entityClass, int weight, int min, int max,
            EnumCreatureType type, Biome[] biomes
    ) {
        // Skip DRPG's default spawn registration for blocked classes
        if (!SpawnBlacklist.isBlocked(entityClass)) {
            // re-emit the original call when allowed
            EntityRegistry.addSpawn(entityClass, weight, min, max, type, biomes);
        }
    }
}
