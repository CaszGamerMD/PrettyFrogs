package com.casz.prettyfrogs.guide;

import com.casz.prettyfrogs.frog.FrogFormRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class FrogGuideEntries {
    public record Entry(Identifier form, Item trigger, String name) {}

    private FrogGuideEntries() {}

    public static List<Entry> entries() {
        List<Entry> entries = new ArrayList<>();
        add(entries, FrogFormRegistry.WATERMELON, Items.MELON_SLICE, "Watermelon Frog");
        add(entries, FrogFormRegistry.PUMPKIN, Items.CARVED_PUMPKIN, "Pumpkin Frog");
        add(entries, FrogFormRegistry.WATERMELON_RED_TOP, Items.MELON_SLICE, "Red-Top Watermelon Frog");
        add(entries, FrogFormRegistry.TADPOLE_COSTUME, Items.TADPOLE_BUCKET, "Tadpole Costume Frog");
        add(entries, FrogFormRegistry.RED_EYED_TREE, Items.ENDER_PEARL, "Red-Eyed Tree Frog");
        add(entries, FrogFormRegistry.SKELETON, Items.BONE, "Skeleton Frog");
        add(entries, FrogFormRegistry.MUDDY, Items.MUD, "Muddy Frog");
        add(entries, FrogFormRegistry.WATER, Items.POTION, "Water Frog");
        add(entries, FrogFormRegistry.MAGMA, Items.MAGMA_CREAM, "Magma Frog");
        add(entries, FrogFormRegistry.ICE, Items.ICE, "Ice Frog");
        add(entries, FrogFormRegistry.DART_RED, Items.POISONOUS_POTATO, "Red Dart Frog");
        add(entries, FrogFormRegistry.DART_BLUE, Items.POISONOUS_POTATO, "Blue Dart Frog");
        add(entries, FrogFormRegistry.DART_YELLOW, Items.POISONOUS_POTATO, "Yellow Dart Frog");
        add(entries, FrogFormRegistry.DART_GREEN, Items.POISONOUS_POTATO, "Green Dart Frog");
        add(entries, FrogFormRegistry.DART_ORANGE, Items.POISONOUS_POTATO, "Orange Dart Frog");
        add(entries, FrogFormRegistry.RAINBOW, Items.REDSTONE, "Rainbow Frog");
        add(entries, FrogFormRegistry.RAINBOW_DART, Items.REDSTONE, "RGB Dart Frog");
        add(entries, FrogFormRegistry.RAINBOW_EYES, Items.REDSTONE, "RGB Eyes Frog");
        add(entries, FrogFormRegistry.RAINBOW_SOLID, Items.REDSTONE, "Solid RGB Frog");
        add(entries, FrogFormRegistry.RAINBOW_DISCO, Items.REDSTONE, "Disco Frog");
        add(entries, FrogFormRegistry.CHERRY_BLOSSOM, Items.PINK_PETALS, "Cherry Blossom Frog");
        add(entries, FrogFormRegistry.BUMBLE, Items.HONEYCOMB, "Bumblefrog");
        add(entries, FrogFormRegistry.MUSHROOM, Items.RED_MUSHROOM, "Mushroom Frog");
        add(entries, FrogFormRegistry.MOSS, Items.MOSS_BLOCK, "Moss Frog");
        add(entries, FrogFormRegistry.ENDER, Items.CHORUS_FRUIT, "Ender Frog");
        add(entries, FrogFormRegistry.GHOST, Items.SOUL_SAND, "Ghost Frog");
        add(entries, FrogFormRegistry.GLOW, Items.GLOW_INK_SAC, "Glow Frog");
        add(entries, FrogFormRegistry.CACTUS, Items.CACTUS, "Cactus Frog");
        add(entries, FrogFormRegistry.STORM, net.minecraft.world.level.block.Blocks.LIGHTNING_ROD.asList().getFirst().asItem(), "Storm Frog");
        add(entries, FrogFormRegistry.SOULFIRE, Items.SOUL_TORCH, "Soulfire Frog");
        add(entries, FrogFormRegistry.SCULK, Items.SCULK, "Sculk Frog");
        add(entries, FrogFormRegistry.CAKE, Items.CAKE, "Cake Frog");
        add(entries, FrogFormRegistry.SLIMY, Items.SLIME_BLOCK, "Slimy Frog");
        add(entries, FrogFormRegistry.CRYSTAL, Items.AMETHYST_CLUSTER, "Crystal Frogs");
        return List.copyOf(entries);
    }

    private static void add(List<Entry> entries, Identifier form, Item trigger, String name) {
        entries.add(new Entry(form, trigger, name));
    }
}
