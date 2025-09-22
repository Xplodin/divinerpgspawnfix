package com.autismobean.drpgspawnfix;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public final class DRPGSpawnFixConfig {
    public static boolean SOFT_CAPS = true;
    public static int CAP_RADIUS = 128; // blocks
    public static int CAP_WHALE = 4;
    public static int CAP_SHARK = 8;
    public static int CAP_LIOPLEURODON = 2;
    public static boolean HARD_STOP = false;     // true = absolute removal (EntityJoinWorldEvent)
    public static boolean DENY_CHECKSPAWN = false; // true = deny at LivingSpawnEvent.CheckSpawn
    public static boolean DEBUG = false;

    public static void load(File cfgDir) {
        File f = new File(cfgDir, "drpgspawnfix.cfg");
        Configuration c = new Configuration(f);
        try {
            c.load();
            HARD_STOP = c.getBoolean("hardStop", "general", false,
                    "If true, cancels EntityJoinWorldEvent for the three DRPG mobs (nothing can spawn them).");
            DENY_CHECKSPAWN = c.getBoolean("denyCheckSpawn", "general", false,
                    "If true, denies LivingSpawnEvent.CheckSpawn for the three DRPG mobs.");
            DEBUG = c.getBoolean("debugLogging", "general", false,
                    "If true, logs when DRPG tries to register those mobs.");
            SOFT_CAPS     = c.getBoolean("softCaps", "caps", true, "Enable local soft caps for DRPG water mobs.");
            CAP_RADIUS    = c.getInt("radius", "caps", 128, 32, 256, "Radius to count existing mobs.");
            CAP_WHALE     = c.getInt("whale", "caps", 4, 0, 64, "Max whales within radius.");
            CAP_SHARK     = c.getInt("shark", "caps", 8, 0, 64, "Max sharks within radius.");
            CAP_LIOPLEURODON = c.getInt("liopleurodon", "caps", 2, 0, 64, "Max liopleurodons within radius.");

        } finally {
            if (c.hasChanged()) c.save();
        }
    }

    private DRPGSpawnFixConfig() {}
}