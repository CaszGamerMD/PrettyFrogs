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
    private boolean appearancePage;
    private Button previous;
    private Button next;
    private Button frogsTab;
    private Button appearanceTab;
    private Button hdToggle;
    private Button decorToggle;
    private Frog preview;

    public FrogGuideScreen() {
        super(Component.translatable("screen.prettyfrogs.frog_guide"));
    }

    @Override
    protected void init() {
        int center = width / 2;
        int top = height / 2 - 100;

        frogsTab = addRenderableWidget(Button.builder(
                Component.translatable("screen.prettyfrogs.tab.frogs"),
                button -> switchPage(false)).bounds(center - 95, top + 1, 90, 20).build());
        appearanceTab = addRenderableWidget(Button.builder(
                Component.translatable("screen.prettyfrogs.tab.appearance"),
                button -> switchPage(true)).bounds(center + 5, top + 1, 90, 20).build());

        previous = addRenderableWidget(Button.builder(Component.literal("<"),
                button -> changePage(-1)).bounds(center - 80, top + 175, 40, 20).build());
        next = addRenderableWidget(Button.builder(Component.literal(">"),
                button -> changePage(1)).bounds(center + 40, top + 175, 40, 20).build());
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE,
                button -> onClose()).bounds(center - 35, top + 175, 70, 20).build());

        hdToggle = addRenderableWidget(Button.builder(
                Component.empty(), button -> {
                    FrogAppearanceSettings.toggleHdTextures();
                    updateAppearanceButtons();
                }).bounds(center - 100, top + 78, 200, 20).build());
        decorToggle = addRenderableWidget(Button.builder(
                Component.empty(), button -> {
                    FrogAppearanceSettings.toggleExtra3DDecorations();
                    updateAppearanceButtons();
                }).bounds(center - 100, top + 121, 200, 20).build());

        updatePreview();
        updateButtons();
    }

    private void switchPage(boolean showAppearance) {
        appearancePage = showAppearance;
        updateButtons();
    }

    private void updateAppearanceButtons() {
        hdToggle.setMessage(Component.translatable(
                "screen.prettyfrogs.setting.hd",
                Component.translatable(FrogAppearanceSettings.hdTextures()
                        ? "screen.prettyfrogs.on" : "screen.prettyfrogs.off")));
        decorToggle.setMessage(Component.translatable(
                "screen.prettyfrogs.setting.details",
                Component.translatable(FrogAppearanceSettings.extra3DDecorations()
                        ? "screen.prettyfrogs.on" : "screen.prettyfrogs.off")));
    }

    private void changePage(int amount) {
        page = Math.max(0, Math.min(entries.size() - 1, page + amount));
        updatePreview();
        updateButtons();
    }

    private void updateButtons() {
        frogsTab.active = appearancePage;
        appearanceTab.active = !appearancePage;
        previous.visible = !appearancePage;
        next.visible = !appearancePage;
        previous.active = page > 0;
        next.active = page < entries.size() - 1;
        hdToggle.visible = appearancePage;
        decorToggle.visible = appearancePage;
        updateAppearanceButtons();
    }

    private void updatePreview() {
        if (minecraft.level == null || entries.isEmpty()) return;
        EntityType<?> frogType = BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.withDefaultNamespace("frog"));
        var created = frogType == null ? null : frogType.create(minecraft.level, EntitySpawnReason.COMMAND);
        preview = created instanceof Frog frog ? frog : null;
        // Synthetic entity requires an id for certain modded item renderers.
        if (preview != null) preview.setId(-9000 - page);
        if (preview instanceof PrettyFrogAccess access) {
            access.prettyfrogs$setForm(entries.get(page).form());
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        int center = width / 2;
        int top = height / 2 - 100;
        graphics.centeredText(font, Component.translatable("screen.prettyfrogs.frog_guide"),
                center, top - 14, -1);

        if (appearancePage) {
            graphics.centeredText(font, Component.translatable("screen.prettyfrogs.appearance.title"),
                    center, top + 43, -1);
            graphics.centeredText(font, Component.translatable("screen.prettyfrogs.appearance.hd.help"),
                    center, top + 104, -8355712);
            graphics.centeredText(font, Component.translatable("screen.prettyfrogs.appearance.details.help"),
                    center, top + 147, -8355712);
            graphics.centeredText(font, Component.translatable("screen.prettyfrogs.appearance.local"),
                    center, top + 161, -8355712);
            return;
        }
        if (entries.isEmpty()) return;
        FrogGuideEntries.Entry entry = entries.get(page);
        graphics.centeredText(font, Component.literal(entry.name()), center, top + 32, -1);
        graphics.centeredText(font, Component.literal((page + 1) + " / " + entries.size()),
                center, top + 46, -8355712);
        if (preview != null) {
            InventoryScreen.extractEntityInInventoryFollowsMouse(
                    graphics, center - 55, top + 50, center + 55, top + 120,
                    48, 0.05F, mouseX, mouseY, preview);
        }
        ItemStack trigger = new ItemStack(entry.trigger());
        graphics.item(trigger, center - 8, top + 126);
        graphics.centeredText(font, trigger.getHoverName(), center, top + 146, -1);
        graphics.centeredText(font,
                Component.translatable("screen.prettyfrogs.use_on_adult_frog"),
                center, top + 160, -8355712);
    }
}
