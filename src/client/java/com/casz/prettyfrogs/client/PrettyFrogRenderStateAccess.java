package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.frog.FrogFormRegistry;
import net.minecraft.resources.Identifier;

public interface PrettyFrogRenderStateAccess {
    Identifier prettyfrogs$getForm();
    void prettyfrogs$setForm(Identifier form);

    default boolean prettyfrogs$isForm(Identifier form) {
        return prettyfrogs$getForm().equals(form);
    }
}
