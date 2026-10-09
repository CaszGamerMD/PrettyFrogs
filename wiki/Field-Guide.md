# The PrettyFrogs Field Guide

The in-game **PrettyFrogs Field Guide** is an item that provides a browseable frog catalog and client-only appearance options.

## Obtain the book

Craft **1 Book + 1 Tadpole Bucket** (shapeless). The recipe returns an **empty Bucket**. Alternatively:

```mcfunction
/give @s prettyfrogs:frog_guide
```

## Open and navigate

1. Hold the Field Guide and use/right-click it.
2. Open the **Frogs** tab to browse forms with the **<** and **>** navigation buttons. Each entry has a frog preview, an item icon, and its trigger name.
3. Open **Appearance** to choose between HD and classic textures and to enable or disable optional 3D decorations.
4. Use **Done** to close the screen.

The guide's frog preview is a locally created display entity, not a frog spawned into the world.

### Note on RGB previews

The guide currently uses **Redstone Dust as a display-only placeholder** for the RGB entries. The actual transformation item is the optional `colorful_rods:rgb_end_rod`, as listed in [[Frog Forms]].

## Appearance settings

| Setting | On (default) | Off |
| --- | --- | --- |
| HD Textures | Uses merged HD artwork for the supported texture set | Uses the eight preserved classic files |
| Extra 3D Decorations | Shows optional Crystal/Mushroom/Water/Cake/Ice detail layer | Hides that optional layer only |

These settings affect the local player's rendering, **not** server-side transformation data or another player's choices. They persist between restarts in `config/prettyfrogs-client.properties`.

More detail: [[Appearance Settings]].

## If the book does not open or render

See [[Troubleshooting]] for versions, missing assets, and client-only GUI diagnostics.

[[Home]] · [[Crafting and Recipes]] · [[Appearance Settings]]

## Rotating a frog preview

On the **Frogs** tab, **left-click and drag the frog image** to rotate the 3D frog around its vertical axis. Drag vertically to tilt the preview; the tilt is limited to keep it readable. Your chosen angle stays while turning guide pages, allowing comparisons from the same view.
