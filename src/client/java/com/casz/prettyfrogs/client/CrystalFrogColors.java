package com.casz.prettyfrogs.client;

import java.util.Map;
import net.minecraft.resources.Identifier;

public final class CrystalFrogColors {
    private static final Map<String, Integer> COLORS = Map.ofEntries(
            Map.entry("minecraft:amethyst_cluster", 0xB783E3),
            Map.entry("crystaldepths:coal_cluster", 0x45454D),
            Map.entry("crystaldepths:iron_cluster", 0xD8C5B4),
            Map.entry("crystaldepths:gold_cluster", 0xF6D34A),
            Map.entry("crystaldepths:lapis_cluster", 0x315CC6),
            Map.entry("crystaldepths:diamond_cluster", 0x62DDD2),
            Map.entry("crystaldepths:emerald_cluster", 0x36C96B),
            Map.entry("mythicupgrades:ametrine_crystal_cluster", 0xC98AE8),
            Map.entry("mythicupgrades:aquamarine_crystal_cluster", 0x72DDE7),
            Map.entry("mythicupgrades:citrine_crystal_cluster", 0xE7B83D),
            Map.entry("mythicupgrades:jade_crystal_cluster", 0x63B87C),
            Map.entry("mythicupgrades:peridot_crystal_cluster", 0xA5D94D),
            Map.entry("mythicupgrades:ruby_crystal_cluster", 0xD83E55),
            Map.entry("mythicupgrades:sapphire_crystal_cluster", 0x466FDC),
            Map.entry("mythicupgrades:topaz_crystal_cluster", 0xE59A47)
    );

    private CrystalFrogColors() {}

    public static int color(Identifier crystal) {
        return COLORS.getOrDefault(crystal.toString(), 0xB783E3);
    }
}
