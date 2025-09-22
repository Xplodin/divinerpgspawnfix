// RuntimeGuards.java
package com.autismobean.drpgspawnfix;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "drpgspawnfix")
public class RuntimeGuards {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn e) {
        if (!DRPGSpawnFixConfig.DENY_CHECKSPAWN) return;
        if (SpawnBlacklist.isBlocked(e.getEntityLiving().getClass())) {
            e.setResult(net.minecraftforge.fml.common.eventhandler.Event.Result.DENY);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onJoinWorld(EntityJoinWorldEvent e) {
        if (!DRPGSpawnFixConfig.HARD_STOP && !DRPGSpawnFixConfig.SOFT_CAPS) return;

        final Entity ent = e.getEntity();
        final String n = ent.getClass().getName();

        if (DRPGSpawnFixConfig.HARD_STOP && SpawnBlacklist.isBlocked(ent.getClass())) {
            e.setCanceled(true);
            return;
        }

        if (DRPGSpawnFixConfig.SOFT_CAPS) {
            int cap = -1;
            if (n.equals("divinerpg.objects.entities.entity.vanilla.EntityWhale")) cap = DRPGSpawnFixConfig.CAP_WHALE;
            else if (n.equals("divinerpg.objects.entities.entity.vanilla.EntityShark")) cap = DRPGSpawnFixConfig.CAP_SHARK;
            else if (n.equals("divinerpg.objects.entities.entity.vanilla.EntityLiopleurodon")) cap = DRPGSpawnFixConfig.CAP_LIOPLEURODON;

            if (cap >= 0) {
                int r = DRPGSpawnFixConfig.CAP_RADIUS;
                net.minecraft.util.math.BlockPos p = ent.getPosition();
                net.minecraft.util.math.AxisAlignedBB box = new net.minecraft.util.math.AxisAlignedBB(
                        p.getX() - r, 0, p.getZ() - r, p.getX() + r, 255, p.getZ() + r
                );
                int count = e.getWorld().getEntities(ent.getClass(), x -> true).stream()
                        .filter(x -> x.getEntityBoundingBox().intersects(box))
                        .mapToInt(x -> 1).sum();
                if (count >= cap) e.setCanceled(true);
            }
        }
    }
}

