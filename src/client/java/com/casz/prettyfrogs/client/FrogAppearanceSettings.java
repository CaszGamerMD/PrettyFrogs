package com.casz.prettyfrogs.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Visual-only, per-player options. Never synced with the world or server.
 * Defaults preserve the appearance that was in main before these toggles.
 */
public final class FrogAppearanceSettings {
    private static final Logger LOGGER = LoggerFactory.getLogger("prettyfrogs");
    private static final String HD_KEY = "hdTextures";
    private static final String DETAILS_KEY = "extra3DDecorations";
    private static volatile boolean hdTextures = true;
    private static volatile boolean extra3DDecorations = true;

    private FrogAppearanceSettings() {}

    private static Path configPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("prettyfrogs-client.properties");
    }

    public static void load() {
        Properties props = new Properties();
        Path path = configPath();
        if (Files.exists(path)) {
            try (InputStream input = Files.newInputStream(path)) {
                props.load(input);
            } catch (IOException e) {
                LOGGER.warn("Could not read PrettyFrogs appearance settings: {}", path, e);
            }
        }
        hdTextures = Boolean.parseBoolean(props.getProperty(HD_KEY, "true"));
        extra3DDecorations = Boolean.parseBoolean(props.getProperty(DETAILS_KEY, "true"));
    }

    public static boolean hdTextures() {
        return hdTextures;
    }

    public static boolean extra3DDecorations() {
        return extra3DDecorations;
    }

    public static void toggleHdTextures() {
        hdTextures = !hdTextures;
        save();
    }

    public static void toggleExtra3DDecorations() {
        extra3DDecorations = !extra3DDecorations;
        save();
    }

    private static void save() {
        Properties props = new Properties();
        props.setProperty(HD_KEY, Boolean.toString(hdTextures));
        props.setProperty(DETAILS_KEY, Boolean.toString(extra3DDecorations));
        Path path = configPath();
        try {
            Files.createDirectories(path.getParent());
            try (OutputStream output = Files.newOutputStream(path)) {
                props.store(output, "PrettyFrogs client-only appearance options");
            }
        } catch (IOException e) {
            LOGGER.warn("Could not save PrettyFrogs appearance settings: {}", path, e);
        }
    }
}
