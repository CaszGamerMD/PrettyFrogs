package com.casz.prettyfrogs.frog;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueOutput;

/**
 * A self-contained reusable frog bucket. Captures the FULL Frog entity data
 * (including PrettyFrogs skin, exact crystal, candles/colors, vanilla type,
 * health, age, name, etc.) rather than regenerating a vanilla frog on release.
 *
 * Uses vanilla DataComponents.ENTITY_DATA for persistent stack/network storage.
 * The capture path is reached via FrogInteractionHandler's UseEntityCallback,
 * so it does not require or mix into third-party bucket mods.
 */
public final class PrettyFrogBucketItem extends Item {
    public PrettyFrogBucketItem(Properties properties) {
        super(properties);
    }

    public static InteractionResult capture(Player player, ItemStack stack, Frog frog) {
        if (!(stack.getItem() instanceof PrettyFrogBucketItem)
                || stack.has(DataComponents.ENTITY_DATA) || frog.isPassenger()
                || frog.isVehicle() || !frog.isAlive()) {
            return InteractionResult.PASS;
        }

        Level level = player.level();
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        // Do not remove the frog unless complete serialization succeeded.
        ProblemReporter.Collector problems = new ProblemReporter.Collector();
        TagValueOutput output = TagValueOutput.createWithContext(problems, level.registryAccess());
        frog.saveWithoutId(output);
        if (!problems.isEmpty()) {
            return InteractionResult.FAIL;
        }
        CompoundTag entityData = output.buildResult();
        stack.set(DataComponents.ENTITY_DATA, TypedEntityData.of(EntityType.FROG, entityData));
        level.playSound(null, frog.blockPosition(), SoundEvents.BUCKET_FILL_FISH,
                SoundSource.NEUTRAL, 1.0F, 1.0F);
        frog.discard();
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        TypedEntityData<EntityType<?>> stored = stack.get(DataComponents.ENTITY_DATA);
        if (stored == null) return InteractionResult.PASS;
        if (stored.type() != EntityType.FROG) return InteractionResult.FAIL;

        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        if (!(level instanceof ServerLevel server)) return InteractionResult.FAIL;

        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        if (!level.isInWorldBounds(pos) || (player != null && !server.mayInteract(player, pos))) {
            return InteractionResult.FAIL;
        }
        Frog frog = EntityType.FROG.create(server, EntitySpawnReason.BUCKET);
        if (frog == null) return InteractionResult.FAIL;

        // TypedEntityData.loadInto() preserves a fresh UUID while restoring
        // persistent vanilla frog and PrettyFrogs NBT.
        stored.loadInto(frog);
        frog.setPos(pos.getX() + 0.5D, pos.getY() + 0.05D, pos.getZ() + 0.5D);
        frog.setYRot(context.getRotation());
        frog.setXRot(0);
        frog.setDeltaMovement(0, 0, 0);

        // Never consume the bucket if there is no clear place for the frog.
        if (!server.noCollision(frog) || !server.addFreshEntity(frog)) {
            return InteractionResult.FAIL;
        }

        stack.remove(DataComponents.ENTITY_DATA);
        server.playSound(null, pos, SoundEvents.BUCKET_EMPTY_FISH,
                SoundSource.NEUTRAL, 1.0F, 1.0F);
        return InteractionResult.SUCCESS;
    }

    @Override
    public Component getName(ItemStack stack) {
        TypedEntityData<EntityType<?>> data = stack.get(DataComponents.ENTITY_DATA);
        if (data != null && data.type() == EntityType.FROG) {
            String form = data.copyTagWithoutId().getString("PrettyFrogsForm").orElse("prettyfrogs:normal");
            return Component.translatable("item.prettyfrogs.frog_keeper_bucket.filled", form);
        }
        return super.getName(stack);
    }
}
