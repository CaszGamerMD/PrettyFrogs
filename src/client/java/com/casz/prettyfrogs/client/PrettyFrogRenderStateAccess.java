package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.frog.FrogFormRegistry;
import net.minecraft.resources.Identifier;

public interface PrettyFrogRenderStateAccess {
    Identifier prettyfrogs$getForm();
    void prettyfrogs$setForm(Identifier form);
    Identifier prettyfrogs$getCrystal();
    void prettyfrogs$setCrystal(Identifier crystal);
    int prettyfrogs$getCakeCandles();
    void prettyfrogs$setCakeCandles(int count);

    default boolean prettyfrogs$isForm(Identifier form) {
        return prettyfrogs$getForm().equals(form);
    }
}
