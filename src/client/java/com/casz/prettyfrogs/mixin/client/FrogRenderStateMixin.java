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
    @Unique
    private int prettyfrogs$cakeCandles;
    @Unique
    private final int[] prettyfrogs$cakeCandleColors = new int[] {
            0xF0D8A8, 0xF0D8A8, 0xF0D8A8, 0xF0D8A8
    };

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
    public int prettyfrogs$getCakeCandles() {
        return prettyfrogs$cakeCandles;
    }

    @Override
    public void prettyfrogs$setCakeCandles(int count) {
        prettyfrogs$cakeCandles = Math.max(0, Math.min(4, count));
    }

    @Override
    public int prettyfrogs$getCakeCandleColor(int index) {
        return index >= 0 && index < prettyfrogs$cakeCandleColors.length
                ? prettyfrogs$cakeCandleColors[index] : 0xF0D8A8;
    }

    @Override
    public void prettyfrogs$setCakeCandleColor(int index, int color) {
        if (index >= 0 && index < prettyfrogs$cakeCandleColors.length) {
            prettyfrogs$cakeCandleColors[index] = color & 0xFFFFFF;
        }
    }

    @Override
    public void prettyfrogs$setForm(Identifier form) {
        prettyfrogs$form = FrogFormRegistry.get(form).id();
    }
}
