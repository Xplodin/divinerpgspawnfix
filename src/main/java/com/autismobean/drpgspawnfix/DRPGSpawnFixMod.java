package com.autismobean.drpgspawnfix;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

@Mod(modid = "drpgspawnfix", name = "DivineRPG Spawn Fix", version = "1.3.0", acceptableRemoteVersions="*")
public class DRPGSpawnFixMod {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        DRPGSpawnFixConfig.load(e.getModConfigurationDirectory());
        // Register optional runtime guards only if enabled
        if (DRPGSpawnFixConfig.HARD_STOP || DRPGSpawnFixConfig.DENY_CHECKSPAWN) {
            MinecraftForge.EVENT_BUS.register(new RuntimeGuards());
        }
    }

    /** Optional runtime guards, controlled by config */
    public static class RuntimeGuards {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public void onCheckSpawn(LivingSpawnEvent.CheckSpawn e) {
            if (!DRPGSpawnFixConfig.DENY_CHECKSPAWN) return;
            if (SpawnBlacklist.isBlocked(e.getEntityLiving().getClass())) {
                e.setResult(net.minecraftforge.fml.common.eventhandler.Event.Result.DENY);
            }
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public void onJoin(EntityJoinWorldEvent e) {
            if (!DRPGSpawnFixConfig.HARD_STOP) return;
            Entity ent = e.getEntity();
            if (SpawnBlacklist.isBlocked(ent.getClass())) {
                e.setCanceled(true);
            }
        }
    }
}
