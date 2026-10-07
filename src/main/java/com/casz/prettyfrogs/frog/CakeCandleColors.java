package com.casz.prettyfrogs.frog;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class CakeCandleColors {
    public static final int DEFAULT = 0xF0D8A8;

    private CakeCandleColors() {}

    public static int colorFor(Item item) {
        if (item == Items.WHITE_CANDLE) return 0xF0F0F0;
        if (item == Items.LIGHT_GRAY_CANDLE) return 0xA8A8A8;
        if (item == Items.GRAY_CANDLE) return 0x55555A;
        if (item == Items.BLACK_CANDLE) return 0x202025;
        if (item == Items.BROWN_CANDLE) return 0x7A4B2A;
        if (item == Items.RED_CANDLE) return 0xC63B35;
        if (item == Items.ORANGE_CANDLE) return 0xF28C28;
        if (item == Items.YELLOW_CANDLE) return 0xF4D348;
        if (item == Items.LIME_CANDLE) return 0x7DCE45;
        if (item == Items.GREEN_CANDLE) return 0x3B8D48;
        if (item == Items.CYAN_CANDLE) return 0x35A9B4;
        if (item == Items.LIGHT_BLUE_CANDLE) return 0x6FB4E8;
        if (item == Items.BLUE_CANDLE) return 0x4052C5;
        if (item == Items.PURPLE_CANDLE) return 0x8E49B5;
        if (item == Items.MAGENTA_CANDLE) return 0xC64EB6;
        if (item == Items.PINK_CANDLE) return 0xE889A9;
        return DEFAULT;
    }
}
