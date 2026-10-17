package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.frog.FrogFormRegistry;
import net.minecraft.resources.Identifier;

public interface PrettyFrogRenderStateAccess {
    Identifier prettyfrogs$getForm();
    void prettyfrogs$setForm(Identifier form);
    Identifier prettyfrogs$getCrystal();
    void prettyfrogs$setCrystal(Identifier crystal);
    float prettyfrogs$getWingSpread();
    void prettyfrogs$setWingSpread(float spread);
    int prettyfrogs$getCakeCandles();
    void prettyfrogs$setCakeCandles(int count);
    int prettyfrogs$getCakeCandleColor(int index);
    void prettyfrogs$setCakeCandleColor(int index, int color);

    default boolean prettyfrogs$isForm(Identifier form) {
        return FrogFormRegistry.isFamily(prettyfrogs$getForm(), form);
    }
}
