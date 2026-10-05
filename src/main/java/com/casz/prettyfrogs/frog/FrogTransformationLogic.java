package com.casz.prettyfrogs.frog;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public final class FrogTransformationLogic {
    private FrogTransformationLogic() {}

    public static Identifier formFor(Item item) {
        return FrogFormRegistry.fromItem(item);
    }

    public static boolean resetsForm(Item item) {
        return FrogFormRegistry.isResetItem(item);
    }

    public static Identifier resetForm() {
        return FrogFormRegistry.NORMAL;
    }

    public static boolean isAlreadyTransformed(Identifier current, Identifier requested) {
        if (current.equals(requested)) {
            return true;
        }
        return FrogFormRegistry.isDart(current) && FrogFormRegistry.isDart(requested);
    }
}
