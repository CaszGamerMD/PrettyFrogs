# Textures and Blockbench

PrettyFrogs uses **standard frog skins**, **overlay/mask PNGs**, and **separate Java model layers** for 3D body parts and accessories.

## Where the texture files live

```text
src/main/resources/assets/prettyfrogs/textures/entity/frog/
```

The majority of base frog skins and glow masks are **48×48 PNGs** matching the normal Java frog UV map. Some custom model textures use other dimensions:

- `skeleton.png` — **128×128** skeletal model atlas.
- `ghost.png`, `ghost_eyes_glow.png` — **128×128** spectral custom model atlases.
- `basic_hood.png`, `tadpole_costume.png` — **128×128** costume atlases.
- `frog_3d_details.png` — **64×64** shared 3D-detail atlas.
- Other specialized accessories use the dimensions declared by their matching `*Model.java` layer.

Never change a texture's resolution or UV layout without updating the matching model's UVs.

## Working on a normal skin

1. Import a suitable frog model into Blockbench (Java/Modded Entity style).
2. Open the matching frog PNG from the folder above.
3. Paint in **pixel art** mode, keeping UV locations.
4. Export as the **same filename**, preserving transparency and alpha.
5. Replace the texture in the mod resources and rebuild.

The Watermelon textures are useful references for regular frog UV placement.

## Painting RGB effects only where you want them

The mod cycles tint values over **mask PNGs** drawn on top of the base skin. The base skin itself can be painted normally.

| RGB form | Base art | Emissive color mask |
| --- | --- | --- |
| Rainbow | `rainbow.png` | `rainbow_glow.png` |
| Rainbow Dart | `rainbow_dart.png` | `rainbow_dart_glow.png` |
| Rainbow Solid | `rainbow_solid.png` | `rainbow_solid_glow.png` |
| Disco | `rainbow_disco.png` | `rainbow_disco_a.png`, `rainbow_disco_b.png` |
| Rainbow Eyes | `rainbow_eyes.png` | `frog_eyes_rgb.png` (separate eye-model UV) |

To choose which pixels animate:

1. Copy the base texture into its corresponding mask, preserving UV alignment.
2. Make all non-RGB regions **fully transparent** (alpha 0).
3. Paint affected pixels **opaque white** (`#FFFFFF`) to receive the animated color strongly.
4. Keep the mask the same dimensions as its matching base UV (except `frog_eyes_rgb.png`, which uses its own eye model).
5. For Disco, divide desired pixels between the A and B masks for independent cycling.

Pure black with alpha 255 is **not** the same as transparent. Unwanted opaque pixels in a mask can cover the base texture.

RGB effects are currently full-bright/emissive; this guide documents the present implementation rather than promising shader-colored light.

## Custom 3D geometry

Source model layers include:

- `SkeletonFrogModel.java`, `GhostFrogModel.java`.
- `BasicFrogHoodModel.java`, `TadpoleCostumeModel.java`.
- `Frog3DDetailsModel.java`, `FrogAccessoryModel.java`.
- `PumpkinStemModel.java`, `CakeCandleModel.java`, `FrogEyeModel.java`.

For animations, keep the vanilla frog model hierarchy (notably `root → body → head → eyes` and the arm/leg parts). Put head decorations under `head`; body/tail items under `body`.

## Asset exports from GitHub Actions

The [build workflow](https://github.com/CaszGamerMD/PrettyFrogs/actions) uploads:

- `PrettyFrogs-Blockbench-Textures` ZIP.
- `PrettyFrogs-Blockbench-3D-Addons` ZIP.
- Compiled `PrettyFrogs-1.0.0` JAR.

These exports include texture PNGs and Java model source for reference; they are **not automatically generated editable `.bbmodel` projects**.

Additional guidance: [Costume hood source guide](https://github.com/CaszGamerMD/PrettyFrogs/blob/main/docs/COSTUME_HOODS.md) and [3D details source guide](https://github.com/CaszGamerMD/PrettyFrogs/blob/main/FROG_3D_DETAILS.md).

[[Home]] · [[Appearance Settings]] · [[Developer Reference]]
