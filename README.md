# PrettyFrogs

Fabric 26.2 mod for cosmetic transformations of vanilla frogs.

## Wiki and Documentation

The [PrettyFrogs GitHub Wiki](https://github.com/CaszGamerMD/PrettyFrogs/wiki) documents installation, every frog form, transformation items, the Field Guide recipe, appearance settings, special effects, crystal support, Blockbench textures, troubleshooting, and developer information. Its [source pages](https://github.com/CaszGamerMD/PrettyFrogs/tree/main/wiki) are versioned in this repository and synchronized by the [Publish PrettyFrogs Wiki workflow](https://github.com/CaszGamerMD/PrettyFrogs/actions/workflows/publish-wiki.yml). GitHub requires an initial Home wiki page to be created via the Wiki tab before automatic publishing can start.

## Frog Controller

Craft a **Frog Controller** from **Fishing Rod + Lily Pad + Slimeball** (shapeless). Right-click an **adult frog** with the controller to possess it: **WASD** to move, **Space** to jump, **Left-click** to shoot its tongue, **Right-click** to croak, and **Sneak + Right-click** to return to your player. Sneak alone does not end possession. The camera becomes the frog's own low eye-level view in first person, and F5 follows the frog in third person. Your player model, equipment and name tag are hidden while possessing (visual-only; no invisibility effect).

The controlled tongue eats **size-1 Slimes/Magma Cubes** using vanilla frog attacks and deals **2 damage (one heart)** to other non-player mobs; it has a short cooldown and checks line of sight and range on the server. The controller does not change custom forms. See the [Frog Controller wiki](wiki/Frog-Controller.md) for details and binding information.

## Transformations

| Item used on frog | Form |
| --- | --- |
| Melon Slice | Watermelon Frog |
| Carved Pumpkin | Pumpkin Frog |
| Tadpole Bucket | Tadpole Costume Frog (hood + tail) |
| Ender Pearl | Red-Eyed Tree Frog |
| Bone | Skeleton Frog |
| Mud Block | Muddy Frog |
| Water Bottle | Water Frog |
| Magma Cream | Magma Frog |
| Ice Block | Ice Frog |
| Poisonous Potato | Random-color Dart Frog |
| `colorful_rods:rgb_end_rod` | Rainbow Frog |
| Pink Petals | Cherry Blossom Frog |
| Honeycomb | Bumblefrog |
| Red Mushroom | Mushroom Frog |
| Moss Block | Moss Frog |
| Chorus Fruit | Ender Frog |
| Soul Sand | Ghost Frog |
| Glow Ink Sac | Glow Frog |
| Cactus | Cactus Frog |
| Lightning Rod | Storm Frog |
| Soul Torch | Soulfire Frog |
| Sculk | Sculk Frog |
| Cake | Cake Frog |
| Slime Block | Slimy Frog |\n| Full crystal cluster | Crystal Frog matching that crystal |
| Milk Bucket | Reset to normal |

## Frog Field Guide crafting

Combine a **Book** and a **Tadpole Bucket** in either crafting grid to craft the **PrettyFrogs Field Guide** (shapeless). The tadpole is consumed, and this recipe returns an **empty Bucket** in the crafting grid. The Field Guide keeps its frog catalog and client-side Appearance settings.

## Behavior

PrettyFrogs forms are cosmetic identities layered over vanilla frogs. The underlying vanilla warm/temperate/cold variant is never replaced.

- Breeding remains normal vanilla behavior and special forms are not inherited.
- Froglight behavior stays linked to the underlying vanilla frog variant.
- Milk removes a transformation and reveals the original vanilla frog.
- Transformation state is synchronized and saved with the frog.
- Survival transformations consume the triggering item; creative mode does not.
- Transformations play a short confirmation chime and briefly stop the frog's current navigation so the visual change reads cleanly.
- Water Bottle leaves a Glass Bottle and Milk Bucket leaves a Bucket in survival.
- Water buckets are deliberately not used as transformation triggers, preserving compatibility with frog-bucketing mods.
- Caszual Additions is optional; Rainbow Frog integration detects its RGB End Rod by registry ID.\n- Crystal Frogs accept full clusters through the `prettyfrogs:crystal_clusters` item tag. Vanilla Amethyst, Crystal Depths 1.4.0, and Mythic Upgrades 5.1.1 clusters are included as optional entries. Other mods/datapacks can extend the tag without Java changes.\n- Crystal Frogs remember the exact source cluster; using a different supported cluster changes their crystal color, while using the same cluster again consumes nothing.

## Special rendering and effects

- **Muddy Frog:** movement leaves cosmetic ground-level mud-like particles.
- **Water Frog:** water particles plus a translucent animated outer shell.
- **Magma Frog:** harmless flame/smoke particles and a full-bright emissive layer.
- **Ice Frog:** snowflake particles plus a translucent icy shell.
- **Skeleton Frog:** dedicated animated skeletal geometry using the vanilla frog animation hierarchy.
- **Tadpole Costume Frog:** an open-front tadpole hood with raised eyes and a tapered tail. The frog keeps its original appearance underneath; the bucket is returned in survival.
- **Reusable hood template:** `BasicFrogHoodModel` and `basic_hood.png` are available for future costume variants.
- **Rainbow Frog:** RGB form with a full-bright emissive layer.
- **Cherry Blossom Frog:** drifting cherry-leaf petals, denser while airborne.
- **Ender Frog:** subtle portal particles.
- **Ghost Frog:** occasional soul wisps plus a translucent spectral shell.
- **Glow Frog:** soft glow particles plus a full-bright emissive layer.
- **Soulfire Frog:** harmless cyan soul-fire particles plus a full-bright emissive layer.
- **Sculk Frog:** occasional sculk particles near the ground plus a full-bright emissive layer.
- **Bumblefrog:** light wax/pollen-like particles while airborne.
- **Moss Frog:** sparse spore-blossom particles.
- **Storm Frog:** harmless electric sparks plus a full-bright emissive layer.\n- **Slimy Frog:** slime droplets, much more active while hopping, plus a translucent slime shell.\n- **Crystal Frog:** tinted crystal markings derived from the exact full cluster used; crystal identity persists and synchronizes to clients.

## Field Guide appearance settings

The **PrettyFrogs Field Guide** has **Frogs** and **Appearance** tabs.
The Appearance tab has two independent, live client-side toggles:

- **HD Textures** (default ON): switches between the selected HD skins
  integrated from `feature/hd-frog-textures` and their original pre-merge
  counterparts. The preserved classic files live in
  `assets/prettyfrogs/textures/entity/frog/classic/`. The switch affects
  Cake, Ice, Mushroom, Pumpkin, and Water textures (including their applicable
  shell/stem), plus the Crystal Frog fallback overlay. Other frogs, including
  Skeleton, Ghost, and Tadpole Costume, are unchanged.
- **Extra 3D Decorations** (default ON): toggles the separate decoration layer
  for Crystal spires, Mushroom caps, Water balloon knot, Cake frosting, and Ice
  snow. Does not hide built-in costume parts, the Skeleton Frog model, the
  Ghost Frog model, or independently added cake candles.

Both preferences take effect immediately, are per-player rather than
server-synchronized, and persist in
`config/prettyfrogs-client.properties`. No extra mod is required.

## Development status

Core form persistence, synchronization, interactions, particles, custom render-state plumbing, Water/Ghost/Slime shell rendering, Magma/Rainbow/Glow/Soulfire/Storm/Sculk emissive rendering, and Skeleton model geometry are implemented and compile against Minecraft 26.2.

A complete functional 48x48 technical texture/mask set is included for every implemented form and render layer. Detailed final pixel-art replacements remain visual polish only; no code changes are required to swap them in.
