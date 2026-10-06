package com.casz.prettyfrogs;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public final class PrettyFrogsItems {
    public static final ResourceKey<Item> FROG_GUIDE_KEY = ResourceKey.create(Registries.ITEM, PrettyFrogs.id("frog_guide"));
    public static final Item FROG_GUIDE = new Item(new Item.Properties().setId(FROG_GUIDE_KEY).stacksTo(1));

    private PrettyFrogsItems() {}

    public static void register() {
        Registry.register(BuiltInRegistries.ITEM, FROG_GUIDE_KEY, FROG_GUIDE);
    }
}
