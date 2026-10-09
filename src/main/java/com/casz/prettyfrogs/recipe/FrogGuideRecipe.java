package com.casz.prettyfrogs.recipe;

import com.casz.prettyfrogs.PrettyFrogs;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.NormalCraftingRecipe;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

/**
 * Shapeless, recipe-book-compatible guide recipe with a specific remainder.
 *
 * Vanilla tadpole buckets do NOT have a crafting remainder; don't change the
 * global item (which could affect recipes from other mods). Only THIS recipe
 * returns an empty bucket after the tadpole bucket is consumed.
 */
public final class FrogGuideRecipe extends NormalCraftingRecipe {
    public static final MapCodec<FrogGuideRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Recipe.CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
                    CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(recipe -> recipe.bookInfo),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                    Ingredient.CODEC.listOf(1, 9).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients)
            ).apply(instance, FrogGuideRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FrogGuideRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Recipe.CommonInfo.STREAM_CODEC,
                    recipe -> recipe.commonInfo,
                    CraftingRecipe.CraftingBookInfo.STREAM_CODEC,
                    recipe -> recipe.bookInfo,
                    ItemStackTemplate.STREAM_CODEC,
                    recipe -> recipe.result,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                    recipe -> recipe.ingredients,
                    FrogGuideRecipe::new
            );

    public static final RecipeSerializer<FrogGuideRecipe> SERIALIZER =
            new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final ItemStackTemplate result;
    private final List<Ingredient> ingredients;

    public FrogGuideRecipe(Recipe.CommonInfo commonInfo,
                           CraftingRecipe.CraftingBookInfo bookInfo,
                           ItemStackTemplate result,
                           List<Ingredient> ingredients) {
        super(commonInfo, bookInfo);
        this.result = result;
        this.ingredients = ingredients;
    }

    public static void register() {
        net.minecraft.core.Registry.register(
                net.minecraft.core.registries.BuiltInRegistries.RECIPE_SERIALIZER,
                PrettyFrogs.id("frog_guide"), SERIALIZER
        );
    }

    @Override
    public RecipeSerializer<FrogGuideRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    protected PlacementInfo createPlacementInfo() {
        return PlacementInfo.create(this.ingredients);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != this.ingredients.size()) {
            return false;
        }
        return input.size() == 1 && this.ingredients.size() == 1
                ? this.ingredients.getFirst().test(input.getItem(0))
                : input.stackedContents().canCraft(this, null);
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return this.result.create();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = CraftingRecipe.defaultCraftingReminder(input);
        for (int slot = 0; slot < input.size(); slot++) {
            if (input.getItem(slot).is(Items.TADPOLE_BUCKET)) {
                remaining.set(slot, new ItemStack(Items.BUCKET));
            }
        }
        return remaining;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(new ShapelessCraftingRecipeDisplay(
                ingredients.stream().map(Ingredient::display).toList(),
                new SlotDisplay.ItemStackSlotDisplay(this.result),
                new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
        ));
    }
}
