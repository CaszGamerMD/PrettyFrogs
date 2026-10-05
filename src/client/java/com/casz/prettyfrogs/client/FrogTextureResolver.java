package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.frog.FrogForm;
import com.casz.prettyfrogs.frog.FrogFormRegistry;
import net.minecraft.resources.Identifier;

public final class FrogTextureResolver {
    private FrogTextureResolver() {}

    /** null means use vanilla's normal frog texture selection. */
    public static Identifier customTexture(Identifier formId) {
        FrogForm form = FrogFormRegistry.get(formId);
        return form.texture();
    }
}
