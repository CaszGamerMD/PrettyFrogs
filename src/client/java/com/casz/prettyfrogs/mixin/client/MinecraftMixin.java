package com.casz.prettyfrogs.mixin.client;

import com.casz.prettyfrogs.PrettyFrogsItems;
import com.casz.prettyfrogs.client.FrogGuideScreen;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "startUseItem", at = @At("HEAD"), cancellable = true)
    private void prettyfrogs$openFieldGuide(CallbackInfo ci) {
        Minecraft minecraft = (Minecraft)(Object)this;
        if (minecraft.player != null && minecraft.player.getMainHandItem().is(PrettyFrogsItems.FROG_GUIDE)) {
            minecraft.setScreen(new FrogGuideScreen());
            ci.cancel();
        }
    }
}
