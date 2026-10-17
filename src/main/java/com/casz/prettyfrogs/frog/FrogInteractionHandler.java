package com.casz.prettyfrogs.frog;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import com.casz.prettyfrogs.PrettyFrogsItems;
import com.casz.prettyfrogs.frog.PrettyFrogBucketItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;

public final class FrogInteractionHandler {
    private FrogInteractionHandler() {}

    public static void register() {
        UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (!(entity instanceof Frog frog) || !(frog instanceof PrettyFrogAccess access)) {
                return InteractionResult.PASS;
            }

            ItemStack stack = player.getItemInHand(hand);
            // Our dedicated bucket serializes the entire frog instead of
            // recreating a vanilla-only variant like third-party bucket mods.
            if (stack.is(PrettyFrogsItems.FROG_KEEPER_BUCKET)) {
                return PrettyFrogBucketItem.capture(player, stack, frog);
            }
            Identifier current = access.prettyfrogs$getForm();

            if (frog.isBaby()) {
                return InteractionResult.PASS;
            }

            // Controller is not consumed and never changes the cosmetic form.
            // Player dismounts using their normal Sneak key.
            if (stack.is(PrettyFrogsItems.FROG_CONTROLLER)) {
                if (frog.isVehicle() || player.isPassenger()) return InteractionResult.PASS;
                if (!level.isClientSide()) {
                    frog.getNavigation().stop();
                    player.startRiding(frog);
                }
                return InteractionResult.SUCCESS;
            }

            if (FrogFormRegistry.isFamily(current, FrogFormRegistry.CAKE) && isCandle(stack)) {
                int candles = access.prettyfrogs$getCakeCandles();
                if (candles >= 4) {
                    return InteractionResult.PASS;
                }
                if (!level.isClientSide()) {
                    access.prettyfrogs$setCakeCandleColor(candles, CakeCandleColors.colorFor(stack.getItem()));
                    access.prettyfrogs$setCakeCandles(candles + 1);
                    level.playSound(null, frog.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 0.45F, 1.35F);
                    level.broadcastEntityEvent(frog, (byte) 20);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }

            if (FrogFormRegistry.isResetItem(stack.getItem())) {
                if (current.equals(FrogFormRegistry.NORMAL)) {
                    return InteractionResult.PASS;
                }

                if (!level.isClientSide()) {
                    access.prettyfrogs$setForm(FrogFormRegistry.NORMAL);
                    level.playSound(null, frog.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.NEUTRAL, 0.7F, 1.15F);
                    level.broadcastEntityEvent(frog, (byte) 60);
                    if (!player.getAbilities().instabuild) {
                        player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                    }
                }
                return InteractionResult.SUCCESS;
            }

            boolean crystalCluster = FrogFormRegistry.isCrystalCluster(stack);
            Identifier requested;
            if (crystalCluster) {
                requested = FrogFormRegistry.crystalVariantForUse(current, access.prettyfrogs$getCrystal(),
                        FrogFormRegistry.crystalId(stack.getItem()));
            } else {
                requested = FrogFormRegistry.variantForUse(stack.getItem(), current, frog.getRandom());
            }
            if (requested != null && FrogFormRegistry.isFamily(requested, FrogFormRegistry.WATER)
                    && !stack.is(Items.POTION)) {
                requested = null;
            }
            if (requested != null && FrogFormRegistry.isFamily(requested, FrogFormRegistry.WATER)
                    && !stack.getOrDefault(net.minecraft.core.component.DataComponents.POTION_CONTENTS, PotionContents.EMPTY).is(Potions.WATER)) {
                requested = null;
            }
            if (requested == null) {
                return InteractionResult.PASS;
            }
            if (!crystalCluster && FrogTransformationLogic.isAlreadyTransformed(current, requested)) {
                return InteractionResult.PASS;
            }

            if (!level.isClientSide()) {
                access.prettyfrogs$setForm(requested);
                if (crystalCluster) {
                    access.prettyfrogs$setCrystal(FrogFormRegistry.crystalId(stack.getItem()));
                }
                level.playSound(null, frog.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 0.65F, 1.0F + frog.getRandom().nextFloat() * 0.2F);
                frog.getNavigation().stop();
                level.broadcastEntityEvent(frog, (byte) 20);
                if (!player.getAbilities().instabuild) {
                    if (FrogFormRegistry.isFamily(requested, FrogFormRegistry.WATER)) {
                        player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                    } else if (FrogFormRegistry.isFamily(requested, FrogFormRegistry.TADPOLE_COSTUME)) {
                        // Filled bucket is consumed, not its reusable container.
                        player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                    } else {
                        stack.shrink(1);
                    }
                }
            }

            return InteractionResult.SUCCESS;
        });
    }

    private static boolean isCandle(ItemStack stack) {
        return stack.is(ItemTags.CANDLES);

    }
}
