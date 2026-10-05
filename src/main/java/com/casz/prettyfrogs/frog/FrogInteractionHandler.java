package com.casz.prettyfrogs.frog;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

public final class FrogInteractionHandler {
    private FrogInteractionHandler() {}

    public static void register() {
        UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (!(entity instanceof Frog frog) || !(frog instanceof PrettyFrogAccess access)) {
                return InteractionResult.PASS;
            }

            ItemStack stack = player.getItemInHand(hand);
            Identifier current = access.prettyfrogs$getForm();

            if (FrogFormRegistry.isResetItem(stack.getItem())) {
                if (current.equals(FrogFormRegistry.NORMAL)) {
                    return InteractionResult.PASS;
                }

                if (!level.isClientSide()) {
                    access.prettyfrogs$setForm(FrogFormRegistry.NORMAL);
                    if (!player.getAbilities().instabuild) {
                        player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                    }
                }
                return InteractionResult.SUCCESS;
            }

            Identifier requested = stack.is(Items.POISONOUS_POTATO)
                    ? FrogFormRegistry.randomDart(frog.getRandom())
                    : FrogFormRegistry.isRgbEndRod(stack.getItem())
                            ? FrogFormRegistry.RAINBOW
                            : FrogFormRegistry.fromItem(stack.getItem());
            if (requested != null && requested.equals(FrogFormRegistry.WATER)
                    && !stack.is(Items.POTION)) {
                requested = null;
            }
            if (requested != null && requested.equals(FrogFormRegistry.WATER)
                    && !stack.getOrDefault(net.minecraft.core.component.DataComponents.POTION_CONTENTS, PotionContents.EMPTY).is(Potions.WATER)) {
                requested = null;
            }
            if (requested == null || requested.equals(current)) {
                return InteractionResult.PASS;
            }

            if (!level.isClientSide()) {
                access.prettyfrogs$setForm(requested);
                if (!player.getAbilities().instabuild) {
                    if (requested.equals(FrogFormRegistry.WATER)) {
                        player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                    } else {
                        stack.shrink(1);
                    }
                }
            }

            return InteractionResult.SUCCESS;
        });
    }
}
