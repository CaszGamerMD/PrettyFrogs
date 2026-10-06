package com.casz.prettyfrogs.client;

import com.casz.prettyfrogs.frog.PrettyFrogAccess;
import com.casz.prettyfrogs.guide.FrogGuideEntries;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.item.ItemStack;

public final class FrogGuideScreen extends Screen {
    private final List<FrogGuideEntries.Entry> entries = FrogGuideEntries.entries();
    private int page;
    private Button previous;
    private Button next;
    private Frog preview;

    public FrogGuideScreen() {
        super(Component.translatable("screen.prettyfrogs.frog_guide"));
    }

    @Override
    protected void init() {
        int y = this.height / 2 + 75;
        this.previous = this.addRenderableWidget(Button.builder(Component.literal("<"), b -> changePage(-1)).bounds(this.width / 2 - 80, y, 40, 20).build());
        this.next = this.addRenderableWidget(Button.builder(Component.literal(">"), b -> changePage(1)).bounds(this.width / 2 + 40, y, 40, 20).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, b -> onClose()).bounds(this.width / 2 - 35, y, 70, 20).build());
        updatePreview();
        updateButtons();
    }

    private void changePage(int amount) {
        page = Math.max(0, Math.min(entries.size() - 1, page + amount));
        updatePreview();
        updateButtons();
    }

    private void updateButtons() {
        previous.active = page > 0;
        next.active = page < entries.size() - 1;
    }

    private void updatePreview() {
        if (minecraft.level == null) return;
        EntityType<?> frogType = BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.withDefaultNamespace("frog"));
        var created = frogType == null ? null : frogType.create(minecraft.level, EntitySpawnReason.COMMAND);
        preview = created instanceof Frog frog ? frog : null;
        if (preview instanceof PrettyFrogAccess access) {
            access.prettyfrogs$setForm(entries.get(page).form());
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        FrogGuideEntries.Entry entry = entries.get(page);
        int center = width / 2;
        int top = height / 2 - 100;
        graphics.centeredText(font, Component.literal("PrettyFrogs Field Guide"), center, top, -1);
        graphics.centeredText(font, Component.literal(entry.name()), center, top + 18, -1);
        graphics.centeredText(font, Component.literal((page + 1) + " / " + entries.size()), center, top + 31, -8355712);
        if (preview != null) {
            InventoryScreen.extractEntityInInventoryFollowsMouse(graphics, center - 55, top + 42, center + 55, top + 118, 48, 0.05F, mouseX, mouseY, preview);
        }
        ItemStack trigger = new ItemStack(entry.trigger());
        graphics.item(trigger, center - 8, top + 125);
        graphics.centeredText(font, trigger.getHoverName(), center, top + 145, -1);
        graphics.centeredText(font, Component.translatable("screen.prettyfrogs.use_on_adult_frog"), center, top + 158, -8355712);
    }
}
