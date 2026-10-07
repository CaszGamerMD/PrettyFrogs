package com.casz.prettyfrogs.frog;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public final class CakeCandleColors {
    public static final int DEFAULT = 0xF0D8A8;

    private CakeCandleColors() {}

    public static int colorFor(Item item) {
        String id = BuiltInRegistries.ITEM.getKey(item).toString();
        int colon = id.indexOf(':');
        String path = colon >= 0 ? id.substring(colon + 1) : id;
        return switch (path) {
            case "white_candle" -> 0xF0F0F0;
            case "light_gray_candle" -> 0xA8A8A8;
            case "gray_candle" -> 0x55555A;
            case "black_candle" -> 0x202025;
            case "brown_candle" -> 0x7A4B2A;
            case "red_candle" -> 0xC63B35;
            case "orange_candle" -> 0xF28C28;
            case "yellow_candle" -> 0xF4D348;
            case "lime_candle" -> 0x7DCE45;
            case "green_candle" -> 0x3B8D48;
            case "cyan_candle" -> 0x35A9B4;
            case "light_blue_candle" -> 0x6FB4E8;
            case "blue_candle" -> 0x4052C5;
            case "purple_candle" -> 0x8E49B5;
            case "magenta_candle" -> 0xC64EB6;
            case "pink_candle" -> 0xE889A9;
            default -> DEFAULT;
        };
    }
}
