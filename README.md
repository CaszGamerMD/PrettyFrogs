# PrettyFrogs

Fabric 26.2 mod for cosmetic transformations of vanilla frogs.

## Transformations

| Item used on frog | Form |
| --- | --- |
| Melon Slice | Watermelon Frog |
| Carved Pumpkin | Pumpkin Frog |
| Ender Pearl | Red-Eyed Tree Frog |
| Bone | Skeleton Frog |
| Mud Block | Muddy Frog |
| Water Bottle | Water Frog |
| Magma Cream | Magma Frog |
| Ice Block | Ice Frog |
| Poisonous Potato | Random-color Dart Frog |
| `colorful_rods:rgb_end_rod` | Rainbow Frog |
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
- Caszual Additions is optional; Rainbow Frog integration detects its RGB End Rod by registry ID.

## Special rendering and effects

- **Muddy Frog:** movement leaves cosmetic ground-level mud-like particles.
- **Water Frog:** water particles plus a translucent animated outer shell.
- **Magma Frog:** harmless flame/smoke particles and a full-bright emissive layer.
- **Ice Frog:** snowflake particles.
- **Skeleton Frog:** dedicated animated skeletal geometry using the vanilla frog animation hierarchy.
- **Rainbow Frog:** RGB form with a full-bright emissive layer.

## Development status

Core form persistence, synchronization, interactions, particles, custom render-state plumbing, Water shell rendering, Magma/Rainbow emissive rendering, and Skeleton model geometry are implemented and compile against Minecraft 26.2.

Visual texture/mask assets and further polish are still in progress.
