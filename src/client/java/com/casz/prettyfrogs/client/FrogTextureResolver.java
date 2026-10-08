package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.PrettyFrogs;
import com.casz.prettyfrogs.frog.FrogForm;
import com.casz.prettyfrogs.frog.FrogFormRegistry;
import java.util.Set;
import net.minecraft.resources.Identifier;

public final class FrogTextureResolver {
    // Only these eight assets changed in the HD merge. Every classic texture
    // is copied unchanged from main immediately before that merge.
    private static final Set<String> HD_SKINS = Set.of(
            "cake.png", "crystal_overlay.png", "ice.png", "mushroom.png",
            "pumpkin.png", "pumpkin_stem.png", "water.png", "water_shell.png");
    private static final String FROG_FOLDER = "textures/entity/frog/";

    private FrogTextureResolver() {}

    /** null means use vanilla's normal frog texture selection. */
    public static Identifier customTexture(Identifier formId) {
        FrogForm form = FrogFormRegistry.get(formId);
        return selectTexture(form.texture());
    }

    /**
     * Select HD or classic assets for the few textures changed by the merge.
     * The rest of the frogs, including the custom skeleton/ghost/costume, are
     * unaffected. This is safe for render layers and live GUI previews.
     */
    public static Identifier selectTexture(Identifier texture) {
        if (texture == null || FrogAppearanceSettings.hdTextures()
                || !PrettyFrogs.MOD_ID.equals(texture.getNamespace())) {
            return texture;
        }
        String path = texture.getPath();
        if (!path.startsWith(FROG_FOLDER)) {
            return texture;
        }
        String name = path.substring(FROG_FOLDER.length());
        if (!HD_SKINS.contains(name)) {
            return texture;
        }
        return PrettyFrogs.id(FROG_FOLDER + "classic/" + name);
    }
}
