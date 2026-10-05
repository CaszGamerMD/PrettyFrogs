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

    @Override
    public Identifier prettyfrogs$getForm() {
        return prettyfrogs$form;
    }

    @Override
    public void prettyfrogs$setForm(Identifier form) {
        prettyfrogs$form = FrogFormRegistry.get(form).id();
    }
}
