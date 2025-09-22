package com.autismobean.drpgspawnfix;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DRPGSpawnFixHardStop {
    // In your event subscriber:
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onJoin(net.minecraftforge.event.entity.EntityJoinWorldEvent e) {
        if (!DRPGSpawnFixConfig.HARD_STOP) return;
        String n = e.getEntity().getClass().getName();
        if (SpawnBlacklist.names().contains(n)) e.setCanceled(true);
    }
}
