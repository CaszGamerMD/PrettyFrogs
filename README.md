# PrettyFrogs

Fabric 26.2 mod for cosmetic transformations of vanilla frogs.

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

## Development status

Core form persistence, synchronization, interactions, particles, custom render-state plumbing, Water/Ghost/Slime shell rendering, Magma/Rainbow/Glow/Soulfire/Storm/Sculk emissive rendering, and Skeleton model geometry are implemented and compile against Minecraft 26.2.

A complete functional 48x48 technical texture/mask set is included for every implemented form and render layer. Detailed final pixel-art replacements remain visual polish only; no code changes are required to swap them in.
