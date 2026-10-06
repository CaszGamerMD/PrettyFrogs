package com.casz.prettyfrogs.frog;

import com.casz.prettyfrogs.PrettyFrogs;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public final class FrogFormRegistry {
    private static final Map<Identifier, FrogForm> FORMS = new LinkedHashMap<>();
    private static final Map<Item, Identifier> TRANSFORM_ITEMS = new LinkedHashMap<>();
    private static final Map<Item, Identifier[]> VARIANT_ITEMS = new LinkedHashMap<>();

    public static final Identifier NORMAL = PrettyFrogs.id("normal");
    public static final Identifier WATERMELON = PrettyFrogs.id("watermelon");
    public static final Identifier WATERMELON_RED_TOP = PrettyFrogs.id("watermelon_red_top");
    public static final Identifier PUMPKIN = PrettyFrogs.id("pumpkin");
    public static final Identifier RED_EYED_TREE = PrettyFrogs.id("red_eyed_tree");
    public static final Identifier SKELETON = PrettyFrogs.id("skeleton");
    public static final Identifier MUDDY = PrettyFrogs.id("muddy");
    public static final Identifier WATER = PrettyFrogs.id("water");
    public static final Identifier MAGMA = PrettyFrogs.id("magma");
    public static final Identifier ICE = PrettyFrogs.id("ice");
    public static final Identifier DART_RED = PrettyFrogs.id("dart_red");
    public static final Identifier DART_BLUE = PrettyFrogs.id("dart_blue");
    public static final Identifier DART_YELLOW = PrettyFrogs.id("dart_yellow");
    public static final Identifier DART_GREEN = PrettyFrogs.id("dart_green");
    public static final Identifier DART_ORANGE = PrettyFrogs.id("dart_orange");
    public static final Identifier RAINBOW = PrettyFrogs.id("rainbow");
    public static final Identifier RAINBOW_DART = PrettyFrogs.id("rainbow_dart");
    public static final Identifier RAINBOW_EYES = PrettyFrogs.id("rainbow_eyes");
    public static final Identifier RAINBOW_SOLID = PrettyFrogs.id("rainbow_solid");
    public static final Identifier RAINBOW_DISCO = PrettyFrogs.id("rainbow_disco");
    public static final Identifier CHERRY_BLOSSOM = PrettyFrogs.id("cherry_blossom");
    public static final Identifier BUMBLE = PrettyFrogs.id("bumble");
    public static final Identifier MUSHROOM = PrettyFrogs.id("mushroom");
    public static final Identifier MOSS = PrettyFrogs.id("moss");
    public static final Identifier ENDER = PrettyFrogs.id("ender");
    public static final Identifier GHOST = PrettyFrogs.id("ghost");
    public static final Identifier GLOW = PrettyFrogs.id("glow");
    public static final Identifier CACTUS = PrettyFrogs.id("cactus");
    public static final Identifier STORM = PrettyFrogs.id("storm");
    public static final Identifier SOULFIRE = PrettyFrogs.id("soulfire");
    public static final Identifier SCULK = PrettyFrogs.id("sculk");
    public static final Identifier CAKE = PrettyFrogs.id("cake");
    public static final Identifier SLIMY = PrettyFrogs.id("slimy");
    public static final Identifier CRYSTAL = PrettyFrogs.id("crystal");
    public static final Identifier RGB_END_ROD = Identifier.fromNamespaceAndPath("colorful_rods", "rgb_end_rod");
    public static final TagKey<Item> CRYSTAL_CLUSTERS = TagKey.create(Registries.ITEM, PrettyFrogs.id("crystal_clusters"));

    private FrogFormRegistry() {}

    public static void bootstrap() {
        FORMS.clear();
        TRANSFORM_ITEMS.clear();
        VARIANT_ITEMS.clear();

        register(NORMAL, null);
        register(WATERMELON, "watermelon");
        register(WATERMELON_RED_TOP, "watermelon_red_top");
        register(PUMPKIN, "pumpkin");
        register(RED_EYED_TREE, "red_eyed_tree");
        register(SKELETON, "skeleton");
        register(MUDDY, "muddy");
        register(WATER, "water");
        register(MAGMA, "magma");
        register(ICE, "ice");
        register(DART_RED, "dart_red");
        register(DART_BLUE, "dart_blue");
        register(DART_YELLOW, "dart_yellow");
        register(DART_GREEN, "dart_green");
        register(DART_ORANGE, "dart_orange");
        register(RAINBOW, "rainbow");
        register(RAINBOW_DART, "rainbow_dart");
        register(RAINBOW_EYES, "rainbow_eyes");
        register(RAINBOW_SOLID, "rainbow_solid");
        register(RAINBOW_DISCO, "rainbow_disco");
        register(CHERRY_BLOSSOM, "cherry_blossom");
        register(BUMBLE, "bumble");
        register(MUSHROOM, "mushroom");
        register(MOSS, "moss");
        register(ENDER, "ender");
        register(GHOST, "ghost");
        register(GLOW, "glow");
        register(CACTUS, "cactus");
        register(STORM, "storm");
        register(SOULFIRE, "soulfire");
        register(SCULK, "sculk");
        register(CAKE, "cake");
        register(SLIMY, "slimy");
        // Crystal keeps the vanilla frog base; the crystal appearance is a translucent tinted overlay.
        register(CRYSTAL, null);

        TRANSFORM_ITEMS.put(Items.MELON_SLICE, WATERMELON);
        VARIANT_ITEMS.put(Items.MELON_SLICE, new Identifier[]{WATERMELON, WATERMELON_RED_TOP});
        VARIANT_ITEMS.put(Items.POISONOUS_POTATO, new Identifier[]{DART_RED, DART_BLUE, DART_YELLOW, DART_GREEN, DART_ORANGE});
        TRANSFORM_ITEMS.put(Items.CARVED_PUMPKIN, PUMPKIN);
        TRANSFORM_ITEMS.put(Items.ENDER_PEARL, RED_EYED_TREE);
        TRANSFORM_ITEMS.put(Items.BONE, SKELETON);
        TRANSFORM_ITEMS.put(Items.MUD, MUDDY);
        TRANSFORM_ITEMS.put(Items.POTION, WATER);
        TRANSFORM_ITEMS.put(Items.MAGMA_CREAM, MAGMA);
        TRANSFORM_ITEMS.put(Items.ICE, ICE);
        TRANSFORM_ITEMS.put(Items.PINK_PETALS, CHERRY_BLOSSOM);
        TRANSFORM_ITEMS.put(Items.HONEYCOMB, BUMBLE);
        TRANSFORM_ITEMS.put(Items.RED_MUSHROOM, MUSHROOM);
        TRANSFORM_ITEMS.put(Items.MOSS_BLOCK, MOSS);
        TRANSFORM_ITEMS.put(Items.CHORUS_FRUIT, ENDER);
        TRANSFORM_ITEMS.put(Items.SOUL_SAND, GHOST);
        TRANSFORM_ITEMS.put(Items.GLOW_INK_SAC, GLOW);
        TRANSFORM_ITEMS.put(Items.CACTUS, CACTUS);
        TRANSFORM_ITEMS.put(Blocks.LIGHTNING_ROD.asList().getFirst().asItem(), STORM);
        TRANSFORM_ITEMS.put(Items.SOUL_TORCH, SOULFIRE);
        TRANSFORM_ITEMS.put(Items.SCULK, SCULK);
        TRANSFORM_ITEMS.put(Items.CAKE, CAKE);
        TRANSFORM_ITEMS.put(Items.SLIME_BLOCK, SLIMY);

        Item rgbRod = BuiltInRegistries.ITEM.getValue(RGB_END_ROD);
        if (rgbRod != null && rgbRod != Items.AIR) {
            VARIANT_ITEMS.put(rgbRod, new Identifier[]{RAINBOW, RAINBOW_DART, RAINBOW_EYES, RAINBOW_SOLID, RAINBOW_DISCO});
        }
    }

    private static void register(Identifier id, String textureName) {
        Identifier texture = textureName == null
                ? null
                : PrettyFrogs.id("textures/entity/frog/" + textureName + ".png");
        FORMS.put(id, new FrogForm(id, texture));
    }

    public static FrogForm get(Identifier id) {
        return FORMS.getOrDefault(id, FORMS.get(NORMAL));
    }

    public static boolean isKnown(Identifier id) {
        return FORMS.containsKey(id);
    }

    public static boolean isRgbEndRod(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).equals(RGB_END_ROD);
    }

    public static boolean isCrystalCluster(net.minecraft.world.item.ItemStack stack) {
        return stack.is(CRYSTAL_CLUSTERS);
    }

    public static Identifier crystalId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static Identifier fromItem(Item item) {
        return TRANSFORM_ITEMS.get(item);
    }


    public static boolean isVariant(Item item, Identifier form) {
        Identifier[] variants = VARIANT_ITEMS.get(item);
        if (variants == null) return false;
        for (Identifier variant : variants) if (variant.equals(form)) return true;
        return false;
    }

    public static Identifier variantForUse(Item item, Identifier current, net.minecraft.util.RandomSource random) {
        Identifier[] variants = VARIANT_ITEMS.get(item);
        if (variants == null || variants.length == 0) return fromItem(item);
        for (int i = 0; i < variants.length; i++) {
            if (variants[i].equals(current)) return variants[(i + 1) % variants.length];
        }
        return variants[random.nextInt(variants.length)];
    }

    public static boolean isDart(Identifier form) {
        return form.equals(DART_RED) || form.equals(DART_BLUE) || form.equals(DART_YELLOW)
                || form.equals(DART_GREEN) || form.equals(DART_ORANGE);
    }

    public static boolean isWatermelon(Identifier form) {
        return form.equals(WATERMELON) || form.equals(WATERMELON_RED_TOP);
    }

    public static Identifier randomWatermelon(net.minecraft.util.RandomSource random) {
        return random.nextBoolean() ? WATERMELON : WATERMELON_RED_TOP;
    }

    public static Identifier nextWatermelon(Identifier current) {
        return current.equals(WATERMELON) ? WATERMELON_RED_TOP : WATERMELON;
    }

    public static Identifier nextDart(Identifier current) {
        Identifier[] colors = {DART_RED, DART_BLUE, DART_YELLOW, DART_GREEN, DART_ORANGE};
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals(current)) return colors[(i + 1) % colors.length];
        }
        return DART_RED;
    }

    public static Identifier randomDart(net.minecraft.util.RandomSource random) {
        Identifier[] colors = {DART_RED, DART_BLUE, DART_YELLOW, DART_GREEN, DART_ORANGE};
        return colors[random.nextInt(colors.length)];
    }

    public static boolean isResetItem(Item item) {
        return item == Items.MILK_BUCKET;
    }
}
