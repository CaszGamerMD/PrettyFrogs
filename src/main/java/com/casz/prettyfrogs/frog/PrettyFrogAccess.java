package com.casz.prettyfrogs.frog;

import net.minecraft.resources.Identifier;

/** Implemented on vanilla Frog by the server/common mixin. */
public interface PrettyFrogAccess {
    Identifier prettyfrogs$getForm();
    void prettyfrogs$setForm(Identifier form);
}
