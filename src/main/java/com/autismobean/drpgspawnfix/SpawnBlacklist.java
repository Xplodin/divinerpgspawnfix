package com.autismobean.drpgspawnfix;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class SpawnBlacklist {
    // FQCNs found in your DRPG 1.7.1 jar
    private static final Set<String> NAMES = new HashSet<>(Arrays.asList(
            "divinerpg.objects.entities.entity.vanilla.EntityShark",
            "divinerpg.objects.entities.entity.vanilla.EntityWhale",
            "divinerpg.objects.entities.entity.vanilla.EntityLiopleurodon"
    ));

    public static boolean isBlocked(Class<?> entityClass) {
        return NAMES.contains(entityClass.getName());
    }

    public static Set<String> names() {
        return Collections.unmodifiableSet(NAMES);
    }

    private SpawnBlacklist() {}
}