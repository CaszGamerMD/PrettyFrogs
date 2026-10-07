package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.frog.FrogFormRegistry;
import net.minecraft.resources.Identifier;

/**
 * Central form-to-mesh dispatch for optional 3D details.
 * A new decorative frog needs one enum entry, one mesh group, and one mapping.
 * The ordinary frog texture and all gameplay data are deliberately unchanged.
 */
public enum Frog3DDetailKind {
    NONE,
    CRYSTALS,
    MUSHROOMS,
    BALLOON_KNOT;

    public static Frog3DDetailKind forForm(Identifier form) {
        if (FrogFormRegistry.CRYSTAL.equals(form)) return CRYSTALS;
        if (FrogFormRegistry.MUSHROOM.equals(form)) return MUSHROOMS;
        if (FrogFormRegistry.WATER.equals(form)) return BALLOON_KNOT;
        return NONE;
    }
}
