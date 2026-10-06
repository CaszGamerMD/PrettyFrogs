package com.casz.prettyfrogs.mixin.client;

import com.casz.prettyfrogs.client.FrogTextureResolver;
import com.casz.prettyfrogs.client.PrettyFrogRenderStateAccess;
import com.casz.prettyfrogs.client.PrettyFrogGlowLayer;
import com.casz.prettyfrogs.client.WaterFrogShellLayer;
import com.casz.prettyfrogs.client.IceFrogShellLayer;
import com.casz.prettyfrogs.client.SkeletonFrogLayer;
import com.casz.prettyfrogs.client.GhostSlimeFrogLayer;
import com.casz.prettyfrogs.client.CrystalFrogLayer;
import com.casz.prettyfrogs.frog.PrettyFrogAccess;
import net.minecraft.client.renderer.entity.FrogRenderer;
import net.minecraft.client.renderer.entity.state.FrogRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.frog.Frog;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.model.animal.frog.FrogModel;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@Mixin(FrogRenderer.class)
public abstract class FrogRendererMixin extends net.minecraft.client.renderer.entity.MobRenderer<Frog, FrogRenderState, FrogModel> {
    protected FrogRendererMixin(EntityRendererProvider.Context context, FrogModel model, float shadow) {
        super(context, model, shadow);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void prettyfrogs$addLayers(EntityRendererProvider.Context context, CallbackInfo ci) {
        FrogRenderer renderer = (FrogRenderer)(Object)this;
        this.addLayer(new PrettyFrogGlowLayer(renderer));
        this.addLayer(new WaterFrogShellLayer(renderer));
        this.addLayer(new IceFrogShellLayer(renderer));
        this.addLayer(new GhostSlimeFrogLayer(renderer));
        this.addLayer(new CrystalFrogLayer(renderer));
        this.addLayer(new SkeletonFrogLayer(renderer, context.getModelSet()));
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void prettyfrogs$applyFormTexture(Frog frog, FrogRenderState state, float partialTicks, CallbackInfo ci) {
        if (!(frog instanceof PrettyFrogAccess access)) {
            return;
        }

        Identifier form = access.prettyfrogs$getForm();
        PrettyFrogRenderStateAccess renderAccess = (PrettyFrogRenderStateAccess) state;
        renderAccess.prettyfrogs$setForm(form);
        renderAccess.prettyfrogs$setCrystal(access.prettyfrogs$getCrystal());
        Identifier customTexture = form.equals(com.casz.prettyfrogs.frog.FrogFormRegistry.SKELETON)
                ? com.casz.prettyfrogs.PrettyFrogs.id("textures/entity/frog/skeleton_base.png")
                : FrogTextureResolver.customTexture(form);
        if (customTexture != null) {
            state.texture = customTexture;
        }
    }
}
