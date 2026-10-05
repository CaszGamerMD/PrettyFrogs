package com.casz.prettyfrogs.frog;

import com.casz.prettyfrogs.PrettyFrogs;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class FrogFormRegistry {
    private static final Map<Identifier, FrogForm> FORMS = new LinkedHashMap<>();
    private static final Map<Item, Identifier> TRANSFORM_ITEMS = new LinkedHashMap<>();

    public static final Identifier NORMAL = PrettyFrogs.id("normal");
    public static final Identifier WATERMELON = PrettyFrogs.id("watermelon");
    public static final Identifier PUMPKIN = PrettyFrogs.id("pumpkin");
    public static final Identifier RED_EYED_TREE = PrettyFrogs.id("red_eyed_tree");
    public static final Identifier SKELETON = PrettyFrogs.id("skeleton");
    public static final Identifier MUDDY = PrettyFrogs.id("muddy");
    public static final Identifier WATER = PrettyFrogs.id("water");
    public static final Identifier MAGMA = PrettyFrogs.id("magma");

    private FrogFormRegistry() {}

    public static void bootstrap() {
        register(NORMAL, null);
        register(WATERMELON, "watermelon");
        register(PUMPKIN, "pumpkin");
        register(RED_EYED_TREE, "red_eyed_tree");
        register(SKELETON, "skeleton");
        register(MUDDY, "muddy");
        register(WATER, "water");
        register(MAGMA, "magma");

        TRANSFORM_ITEMS.put(Items.MELON_SLICE, WATERMELON);
        TRANSFORM_ITEMS.put(Items.CARVED_PUMPKIN, PUMPKIN);
        TRANSFORM_ITEMS.put(Items.ENDER_PEARL, RED_EYED_TREE);
        TRANSFORM_ITEMS.put(Items.BONE, SKELETON);
        TRANSFORM_ITEMS.put(Items.MUD, MUDDY);
        TRANSFORM_ITEMS.put(Items.POTION, WATER);
        TRANSFORM_ITEMS.put(Items.MAGMA_CREAM, MAGMA);
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

    public static Identifier fromItem(Item item) {
        return TRANSFORM_ITEMS.get(item);
    }

    public static boolean isResetItem(Item item) {
        return item == Items.MILK_BUCKET;
    }
}
