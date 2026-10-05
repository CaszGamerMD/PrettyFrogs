package com.casz.prettyfrogs.frog;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public final class FrogInteractionHandler {
    private FrogInteractionHandler() {}

    public static void register() {
        UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (!(entity instanceof Frog frog) || !(frog instanceof PrettyFrogAccess access)) {
                return InteractionResult.PASS;
            }

            ItemStack stack = player.getItemInHand(hand);
            Identifier current = access.prettyfrogs$getForm();

            if (frog.isBaby()) {
                return InteractionResult.PASS;
            }

            if (FrogFormRegistry.isResetItem(stack.getItem())) {
                if (current.equals(FrogFormRegistry.NORMAL)) {
                    return InteractionResult.PASS;
                }

                if (!level.isClientSide()) {
                    access.prettyfrogs$setForm(FrogFormRegistry.NORMAL);
                    level.playSound(null, frog.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.NEUTRAL, 0.7F, 1.15F);
                    level.broadcastEntityEvent(frog, (byte) 20);
                    if (!player.getAbilities().instabuild) {
                        player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                    }
                }
                return InteractionResult.SUCCESS;
            }

            if (stack.is(Items.POISONOUS_POTATO) && FrogFormRegistry.isDart(current)) {
                return InteractionResult.PASS;
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
                level.playSound(null, frog.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 0.65F, 1.0F + frog.getRandom().nextFloat() * 0.2F);
                frog.getNavigation().stop();
                level.broadcastEntityEvent(frog, (byte) 20);
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
