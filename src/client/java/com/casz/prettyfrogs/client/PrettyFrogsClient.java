package com.casz.prettyfrogs.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

public final class PrettyFrogsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FrogAppearanceSettings.load();
        FrogControllerKeys.register();
        FrogPossessionCamera.register();
        ModelLayerRegistry.registerModelLayer(SkeletonFrogModel.LAYER_LOCATION, SkeletonFrogModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(GhostFrogModel.LAYER_LOCATION, GhostFrogModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(PumpkinStemModel.LAYER_LOCATION, PumpkinStemModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(BasicFrogHoodModel.LAYER_LOCATION, BasicFrogHoodModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(TadpoleCostumeModel.LAYER_LOCATION, TadpoleCostumeModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(Frog3DDetailsModel.LAYER_LOCATION, Frog3DDetailsModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(CakeCandleModel.LAYER_LOCATION, CakeCandleModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(FrogEyeModel.LAYER_LOCATION, FrogEyeModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(FrogAccessoryModel.LAYER_LOCATION, FrogAccessoryModel::createBodyLayer);
    }
}
