# PrettyFrogs reusable costume hood

The hood is an **accessory overlay**, not a replacement for the frog model.
It leaves the vanilla frog's own body and face visible, keeps its underlying
froglight/breeding variant unchanged, and follows normal frog head animations.

## Source assets

- `src/client/java/com/casz/prettyfrogs/client/BasicFrogHoodModel.java`
  - Reusable, blank frog-sized hood; model layer `prettyfrogs:basic_frog_hood`
  - Uses the vanilla frog animation part hierarchy
  - `createLayer(false)` creates the standalone blank hood
  - `createLayer(true)` adds the current tadpole eyes and tail via
    `TadpoleCostumeModel.addTadpoleDetails(...)`
- `src/main/resources/assets/prettyfrogs/textures/entity/frog/basic_hood.png`
  - Neutral hood texture for future costume variants
- `src/client/java/com/casz/prettyfrogs/client/TadpoleCostumeModel.java`
  - Reuses the hood and adds tadpole eye bulges and a tapered tail
- `src/main/resources/assets/prettyfrogs/textures/entity/frog/tadpole_costume.png`
  - Blue-green hood and tail texture

## Existing UV atlas regions

Both textures are **128x128 PNG** with 32x32 pixel style regions:

| UV area (x, y) | Intended use |
| --- | --- |
| (0, 0) | Hood cap |
| (32, 0) | Back of cap |
| (64, 0) | Dark tadpole eyes |
| (96, 0) | Eye glints |
| (0, 32) | Front rim |
| (32, 32) | Side panels |
| (64, 32) | Back panel |
| (0, 64) | Inner rim |
| (0, 96) | Tadpole eye bumps and tail base |
| (32, 96) | Tail second segment |
| (64, 96) | Tail third segment |
| (96, 96) | Tail tip |
| (96, 64) | Tail fin |

In Blockbench, keep the texture resolution **128x128**, use **Box UV**,
and keep the hood nested under the `body/head` model part. New costume
features can attach to the hood's `head` node, while tails attach to `body`
so head movement doesn't swing the tail.

## In-game test

Right-click an adult frog with a **Tadpole Bucket**. The frog should keep
its own coloring and wear the 3D hood and tail. In survival, the filled
bucket turns into an empty bucket. Milk Bucket resets the frog to normal.

This template is intentionally not a separate transformation yet; it is a
reusable base for future costume forms.
