# Appearance Settings

Open the **PrettyFrogs Field Guide** and select the **Appearance** tab. There are two independent switches, both **ON** by default.

## HD Textures

**ON:** Renders the newer HD/updated artwork for the supported frog textures.

**OFF:** Selects the pre-merge **classic** artwork preserved inside the JAR for these files:

| Classic texture | What it affects |
| --- | --- |
| `cake.png` | Cake Frog base |
| `crystal_overlay.png` | Crystal fallback overlay (when used) |
| `ice.png` | Ice Frog base |
| `mushroom.png` | Mushroom Frog base |
| `pumpkin.png` | Pumpkin Frog base |
| `pumpkin_stem.png` | Pumpkin Frog stem |
| `water.png` | Water Frog base |
| `water_shell.png` | Water Frog shell |

**This is not a universal low-resolution toggle.** Other frogs—including newer Skeleton, Ghost, Tadpole Costume, and the Watermelon variants—keep the same textures in either mode.

The classic files are bundled under `assets/prettyfrogs/textures/entity/frog/classic/`.

## Extra 3D Decorations

**ON:** Draws the shared cosmetic decoration layer for:

- **Crystal Frog:** crown and back crystals.
- **Mushroom Frog:** tiny 3D mushrooms.
- **Water Frog:** balloon neck/knot.
- **Cake Frog:** frosting cap.
- **Ice Frog:** settled snow cap.

**OFF:** Hides **only** that shared cosmetic layer. It does not remove Tadpole Costume hoods/tails, Skeleton/Ghost custom bodies, Bumble wings, Cactus spikes, Pumpkin stem, or Cake candles.

## Where settings are saved

```text
.minecraft/config/prettyfrogs-client.properties
```

The exact `config` location depends on the Minecraft instance directory. The keys are:

```properties
hdTextures=true
extra3DDecorations=true
```

- Settings save when toggled and load the next time the client starts.
- They are **client-side**; another player can use different settings on the same multiplayer server.
- No server configuration or world save edits are necessary.
- Toggle effects are intended to be immediate without relogging or reloading resources.

[[Field Guide]] · [[Home]] · [[Textures and Blockbench]]
