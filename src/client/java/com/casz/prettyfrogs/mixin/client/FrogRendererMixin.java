package com.casz.prettyfrogs.mixin.client;

import com.casz.prettyfrogs.client.FrogTextureResolver;
import com.casz.prettyfrogs.client.PrettyFrogRenderStateAccess;
import com.casz.prettyfrogs.client.PrettyFrogGlowLayer;
import com.casz.prettyfrogs.client.WaterFrogShellLayer;
import com.casz.prettyfrogs.frog.PrettyFrogAccess;
import net.minecraft.client.renderer.entity.FrogRenderer;
import net.minecraft.client.renderer.entity.state.FrogRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.frog.Frog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.model.animal.frog.FrogModel;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@Mixin(FrogRenderer.class)
public abstract class FrogRendererMixin {
    @Shadow
    protected abstract boolean addLayer(RenderLayer<FrogRenderState, FrogModel> layer);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void prettyfrogs$addLayers(EntityRendererProvider.Context context, CallbackInfo ci) {
        FrogRenderer renderer = (FrogRenderer)(Object)this;
        this.addLayer(new PrettyFrogGlowLayer(renderer));
        this.addLayer(new WaterFrogShellLayer(renderer));
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void prettyfrogs$applyFormTexture(Frog frog, FrogRenderState state, float partialTicks, CallbackInfo ci) {
        if (!(frog instanceof PrettyFrogAccess access)) {
            return;
        }

        Identifier form = access.prettyfrogs$getForm();
        ((PrettyFrogRenderStateAccess) state).prettyfrogs$setForm(form);
        Identifier customTexture = FrogTextureResolver.customTexture(form);
        if (customTexture != null) {
            state.texture = customTexture;
        }
    }
}
