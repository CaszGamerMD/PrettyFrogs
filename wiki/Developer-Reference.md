# Developer Reference

**Repository:** [CaszGamerMD/PrettyFrogs](https://github.com/CaszGamerMD/PrettyFrogs)  
**Mod ID:** `prettyfrogs`  
**Target:** Minecraft 26.2 / Fabric / Java 25  
**License:** All Rights Reserved (see mod metadata; obtain permission before redistributing derived code/assets).

## Building

The repository uses Fabric Loom/Gradle. Build from a configured development checkout with **Java 25** and matching dependencies:

```bash
gradle build
```

The [GitHub build workflow](https://github.com/CaszGamerMD/PrettyFrogs/blob/main/.github/workflows/build.yml) runs the build and publishes the JAR, a Blockbench texture ZIP and a 3D source/reference ZIP.

## How a frog becomes a custom form

| Source path | Responsibility |
| --- | --- |
| `src/main/java/com/casz/prettyfrogs/frog/FrogFormRegistry.java` | Form IDs, trigger items, variant families, crystal tag |
| `src/main/java/com/casz/prettyfrogs/frog/FrogInteractionHandler.java` | Player use, item consumption/remainders, reset, candles |
| `src/main/java/com/casz/prettyfrogs/mixin/FrogMixin.java` | Synced and persisted form/crystal/candle data; particles |
| `src/client/java/com/casz/prettyfrogs/mixin/client/FrogRendererMixin.java` | Custom textures and render-layer registration |
| `src/client/java/com/casz/prettyfrogs/client/FrogTextureResolver.java` | HD/classic asset selection |
| `src/client/java/com/casz/prettyfrogs/client/FrogAppearanceSettings.java` | Saved client-only preferences |
| `src/client/java/com/casz/prettyfrogs/client/FrogGuideScreen.java` | Catalog / Appearance GUI |
| `src/main/java/com/casz/prettyfrogs/guide/FrogGuideEntries.java` | In-game guide form list and preview item |
| `src/main/java/com/casz/prettyfrogs/recipe/FrogGuideRecipe.java` | Guide crafting and bucket remainder |

## Renderers and model layers

- `SkeletonFrogModel` / `SkeletonFrogLayer`: custom skeletal frog.
- `GhostFrogModel` / `GhostFrogLayer`: custom spectral frog and glow.
- `BasicFrogHoodModel`, `TadpoleCostumeModel`, `TadpoleCostumeLayer`: costume system foundation.
- `Frog3DDetailKind` / `Frog3DDetailsModel` / `Frog3DDetailsLayer`: shared optional 3D decorations.
- `FrogAccessoryModel` / `FrogAccessoryLayer`: Bumble/Cactus accessories.
- `CakeCandleModel` / `CakeCandleLayer`: independently drawn custom-colored candles.
- `FrogEyeModel` / `FrogEyeLayer`: selected extra glowing eyes.
- `PrettyFrogGlowLayer`: masks and animated RGB glow.
- Water, Ice, Ghost and Slime rendering have additional translucent shell/layer classes.

**Animation:** keep the vanilla frog model hierarchy and pivots when adding geometry. Model parts attached to `head` should follow head movement; geometry under `body` should follow torso movement. Render submissions are deferred, so mutable shared models can cause visibility bleed between frogs unless handled carefully.

## Data files

- `src/main/resources/data/prettyfrogs/recipe/frog_guide.json` — shapeless custom-serializer recipe.
- `src/main/resources/data/prettyfrogs/tags/item/crystal_clusters.json` — tag of valid cluster ingredients.
- `src/main/resources/assets/prettyfrogs/textures/entity/frog/` — skins, overlays, masks, and model atlases.
- `src/main/resources/assets/prettyfrogs/lang/en_us.json` — item/GUI translations.
- `CUSTOM_FROGS.txt` — human-readable canonical list (update when forms change).

## Adding a new frog form

1. Add the `Identifier`, `register` call, and trigger or variant mapping in `FrogFormRegistry`.
2. Add/modulate particles or special interaction behavior if appropriate.
3. Add its base PNG and any required model/overlay/glow masks.
4. Register the appropriate model layer in `PrettyFrogsClient` and render layer in `FrogRendererMixin`.
5. Update `FrogGuideEntries` and `CUSTOM_FROGS.txt`.
6. Update this wiki's [[Frog Forms]] and [[Special Effects and Models]] pages.
7. Build; test interaction, save/reload, client synchronization, hop/croak animations, and both appearance switches in-game.

## Keeping the wiki updated

Wiki-page source files are versioned in the repository under `wiki/`. The `publish-wiki` workflow attempts to synchronize them to the GitHub Wiki on push. If the Wiki is not initialized, create its first **Home** page in GitHub's Wiki tab and rerun the publishing workflow. Do not edit authentication credentials into documentation or source.

[[Home]] · [[Textures and Blockbench]] · [[Troubleshooting]]
