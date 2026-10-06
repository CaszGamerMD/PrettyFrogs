package com.casz.prettyfrogs.mixin.client;

import com.casz.prettyfrogs.client.PrettyFrogRenderStateAccess;
import com.casz.prettyfrogs.frog.FrogFormRegistry;
import net.minecraft.client.renderer.entity.state.FrogRenderState;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FrogRenderState.class)
public abstract class FrogRenderStateMixin implements PrettyFrogRenderStateAccess {
    @Unique
    private Identifier prettyfrogs$form = FrogFormRegistry.NORMAL;
    @Unique
    private Identifier prettyfrogs$crystal = Identifier.withDefaultNamespace("amethyst_cluster");

    @Override
    public Identifier prettyfrogs$getForm() {
        return prettyfrogs$form;
    }

    @Override
    public Identifier prettyfrogs$getCrystal() {
        return prettyfrogs$crystal;
    }

    @Override
    public void prettyfrogs$setCrystal(Identifier crystal) {
        prettyfrogs$crystal = crystal;
    }

    @Override
    public void prettyfrogs$setForm(Identifier form) {
        prettyfrogs$form = FrogFormRegistry.get(form).id();
    }
}
