package com.casz.prettyfrogs.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

public final class PrettyFrogsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLayerRegistry.registerModelLayer(SkeletonFrogModel.LAYER_LOCATION, SkeletonFrogModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(PumpkinStemModel.LAYER_LOCATION, PumpkinStemModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(CakeCandleModel.LAYER_LOCATION, CakeCandleModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(Frog3DDetailsModel.LAYER_LOCATION, Frog3DDetailsModel::createBodyLayer);
    }
}
