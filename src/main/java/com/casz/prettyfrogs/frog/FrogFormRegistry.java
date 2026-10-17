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
    private static final Map<Identifier, Identifier> POLISHED_BASES = new LinkedHashMap<>();

    public static final Identifier NORMAL = PrettyFrogs.id("normal");
    public static final Identifier WATERMELON = PrettyFrogs.id("watermelon");
    public static final Identifier WATERMELON_RED_TOP = PrettyFrogs.id("watermelon_red_top");
    public static final Identifier PUMPKIN = PrettyFrogs.id("pumpkin");
    public static final Identifier TADPOLE_COSTUME = PrettyFrogs.id("tadpole_costume");
    public static final Identifier RED_EYED_TREE = PrettyFrogs.id("red_eyed_tree");
    public static final Identifier SKELETON = PrettyFrogs.id("skeleton");
    public static final Identifier MUDDY = PrettyFrogs.id("muddy");
    public static final Identifier WATER = PrettyFrogs.id("water");
    public static final Identifier MAGMA = PrettyFrogs.id("magma");
    public static final Identifier ICE = PrettyFrogs.id("ice");
    public static final Identifier DARTFROG_1 = PrettyFrogs.id("dartfrog_1");
    public static final Identifier DARTFROG_2 = PrettyFrogs.id("dartfrog_2");
    public static final Identifier DARTFROG_3 = PrettyFrogs.id("dartfrog_3");
    public static final Identifier DARTFROG_4 = PrettyFrogs.id("dartfrog_4");
    public static final Identifier DARTFROG_5 = PrettyFrogs.id("dartfrog_5");
    public static final Identifier DARTFROG_6 = PrettyFrogs.id("dartfrog_6");
    public static final Identifier DARTFROG_7 = PrettyFrogs.id("dartfrog_7");
    public static final Identifier DARTFROG_8 = PrettyFrogs.id("dartfrog_8");
    private static final Identifier[] DART_FORMS = {
            DARTFROG_1, DARTFROG_2, DARTFROG_3, DARTFROG_4,
            DARTFROG_5, DARTFROG_6, DARTFROG_7, DARTFROG_8
    };
    // Older worlds/buckets keep their custom frog instead of resetting to normal.
    private static final Map<Identifier, Identifier> LEGACY_DART_FORMS = Map.of(
            PrettyFrogs.id("dart_red"), DARTFROG_6,
            PrettyFrogs.id("dart_blue"), DARTFROG_5,
            PrettyFrogs.id("dart_yellow"), DARTFROG_2,
            PrettyFrogs.id("dart_green"), DARTFROG_3,
            PrettyFrogs.id("dart_orange"), DARTFROG_6
    );
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
        POLISHED_BASES.clear();

        register(NORMAL, null);
        register(WATERMELON, "watermelon");
        register(WATERMELON_RED_TOP, "watermelon_red_top");
        register(PUMPKIN, "pumpkin");
        // Costume overlays the frog, preserving the underlying vanilla texture.
        register(TADPOLE_COSTUME, null);
        register(RED_EYED_TREE, "red_eyed_tree");
        register(SKELETON, "skeleton");
        register(MUDDY, "muddy");
        register(WATER, "water");
        register(MAGMA, "magma");
        register(ICE, "ice");
        for (int i = 0; i < DART_FORMS.length; i++) {
            register(DART_FORMS[i], "dartfrog_" + (i + 1));
        }
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

        // Add identities rather than replacing assets or migrating existing frogs.
        // Snapshot the originals before adding their counterparts.
        for (FrogForm original : FORMS.values().toArray(FrogForm[]::new)) {
            if (original.id().equals(NORMAL)) continue;
            Identifier variant = polished(original.id());
            Identifier texture = original.texture() == null ? null
                    : PrettyFrogs.id("textures/entity/frog/polished/" + original.id().getPath() + ".png");
            FORMS.put(variant, new FrogForm(variant, texture));
            POLISHED_BASES.put(variant, original.id());
        }

        TRANSFORM_ITEMS.put(Items.MELON_SLICE, WATERMELON);
        VARIANT_ITEMS.put(Items.MELON_SLICE, new Identifier[]{WATERMELON, WATERMELON_RED_TOP});
        VARIANT_ITEMS.put(Items.POISONOUS_POTATO, DART_FORMS.clone());
        TRANSFORM_ITEMS.put(Items.CARVED_PUMPKIN, PUMPKIN);
        TRANSFORM_ITEMS.put(Items.TADPOLE_BUCKET, TADPOLE_COSTUME);
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
        TRANSFORM_ITEMS.forEach((item, form) -> VARIANT_ITEMS.putIfAbsent(item, new Identifier[]{form}));
        VARIANT_ITEMS.replaceAll((item, originals) -> {
            Identifier[] pool = java.util.Arrays.copyOf(originals, originals.length * 2);
            for (int i = 0; i < originals.length; i++) pool[originals.length + i] = polished(originals[i]);
            return pool;
        });
    }

    public static Identifier polished(Identifier original) {
        return PrettyFrogs.id(original.getPath() + "_polished");
    }

    public static boolean isPolished(Identifier form) {
        return POLISHED_BASES.containsKey(form);
    }

    /** Resolve behavior only. Never use this value when saving a frog's identity. */
    public static Identifier familyOf(Identifier form) {
        Identifier canonical = LEGACY_DART_FORMS.getOrDefault(form, form);
        return POLISHED_BASES.getOrDefault(canonical, canonical);
    }

    public static boolean isFamily(Identifier form, Identifier family) {
        return family.equals(familyOf(form));
    }

    public static Identifier crystalVariantForUse(Identifier current, Identifier oldCrystal, Identifier newCrystal) {
        if (isFamily(current, CRYSTAL) && oldCrystal.equals(newCrystal)) {
            return isPolished(current) ? CRYSTAL : polished(CRYSTAL);
        }
        return isPolished(current) && isFamily(current, CRYSTAL) ? polished(CRYSTAL) : CRYSTAL;
    }

    private static void register(Identifier id, String textureName) {
        Identifier texture = textureName == null
                ? null
                : PrettyFrogs.id("textures/entity/frog/" + textureName + ".png");
        FORMS.put(id, new FrogForm(id, texture));
    }

    public static FrogForm get(Identifier id) {
        return FORMS.getOrDefault(LEGACY_DART_FORMS.getOrDefault(id, id), FORMS.get(NORMAL));
    }

    public static boolean isKnown(Identifier id) {
        return FORMS.containsKey(id) || LEGACY_DART_FORMS.containsKey(id);
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
        current = LEGACY_DART_FORMS.getOrDefault(current, current);
        Identifier[] variants = VARIANT_ITEMS.get(item);
        if (variants == null || variants.length == 0) return fromItem(item);
        for (int i = 0; i < variants.length; i++) {
            if (variants[i].equals(current)) return variants[(i + 1) % variants.length];
        }
        return variants[random.nextInt(variants.length)];
    }

    public static boolean isDart(Identifier form) {
        form = familyOf(form);
        for (Identifier dart : DART_FORMS) if (dart.equals(form)) return true;
        return LEGACY_DART_FORMS.containsKey(form);
    }

    public static boolean isWatermelon(Identifier form) {
        form = familyOf(form);
        return form.equals(WATERMELON) || form.equals(WATERMELON_RED_TOP);
    }

    public static Identifier randomWatermelon(net.minecraft.util.RandomSource random) {
        return variantForUse(Items.MELON_SLICE, NORMAL, random);
    }

    public static Identifier nextWatermelon(Identifier current) {
        return nextVariant(Items.MELON_SLICE, current);
    }

    public static Identifier nextDart(Identifier current) {
        return nextVariant(Items.POISONOUS_POTATO, current);
    }

    public static Identifier randomDart(net.minecraft.util.RandomSource random) {
        return variantForUse(Items.POISONOUS_POTATO, NORMAL, random);
    }

    private static Identifier nextVariant(Item item, Identifier current) {
        Identifier canonical = LEGACY_DART_FORMS.getOrDefault(current, current);
        Identifier[] pool = VARIANT_ITEMS.get(item);
        for (int i = 0; i < pool.length; i++) {
            if (pool[i].equals(canonical)) return pool[(i + 1) % pool.length];
        }
        return pool[0];
    }

    public static boolean isResetItem(Item item) {
        return item == Items.MILK_BUCKET;
    }
}
