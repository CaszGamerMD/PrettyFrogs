package com.casz.prettyfrogs;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public final class PrettyFrogsItems {
    public static final ResourceKey<Item> FROG_CONTROLLER_KEY = ResourceKey.create(Registries.ITEM, PrettyFrogs.id("frog_controller"));
    public static final Item FROG_CONTROLLER = new Item(new Item.Properties().setId(FROG_CONTROLLER_KEY).stacksTo(1));

    public static final ResourceKey<Item> FROG_GUIDE_KEY = ResourceKey.create(Registries.ITEM, PrettyFrogs.id("frog_guide"));
    public static final Item FROG_GUIDE = new Item(new Item.Properties().setId(FROG_GUIDE_KEY).stacksTo(1));

    // Vanilla tab keys are private in Minecraft 26.2, so construct their
    // public resource key by the registry identifier rather than referencing
    // CreativeModeTabs.TOOLS_AND_UTILITIES directly.
    private static final ResourceKey<CreativeModeTab> TOOLS_TAB = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace("tools_and_utilities"));

    private PrettyFrogsItems() {}

    public static void register() {
        Registry.register(BuiltInRegistries.ITEM, FROG_GUIDE_KEY, FROG_GUIDE);
        Registry.register(BuiltInRegistries.ITEM, FROG_CONTROLLER_KEY, FROG_CONTROLLER);

        // Merely registering an item does not make it show in the creative
        // inventory or creative search. Add both mod items to a real tab.
        CreativeModeTabEvents.modifyOutputEvent(TOOLS_TAB).register(output -> {
            output.accept(FROG_CONTROLLER);
            output.accept(FROG_GUIDE);
        });
    }
}
