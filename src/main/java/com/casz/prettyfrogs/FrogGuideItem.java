package com.casz.prettyfrogs;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public final class FrogGuideItem extends Item {
    public FrogGuideItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            com.casz.prettyfrogs.client.FrogGuideOpener.open();
        }
        return InteractionResult.SUCCESS;
    }
}
