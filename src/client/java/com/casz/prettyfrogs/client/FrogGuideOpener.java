package com.casz.prettyfrogs.client;

import net.minecraft.client.Minecraft;

public final class FrogGuideOpener {
    private FrogGuideOpener() {}

    public static void open() {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.setScreen(new FrogGuideScreen());
    }
}
