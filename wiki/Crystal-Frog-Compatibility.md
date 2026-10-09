# Crystal Frog Compatibility

Crystal Frogs use the **full cluster item**, not the crystal block or shard.

Right-click an adult frog with any item in:

```text
#prettyfrogs:crystal_clusters
```

The frog's data remembers the exact cluster registry ID; a different crystal changes its color, and the same one again is ignored. Crystals can also have a client-side tinted or source-texture overlay and optional 3D spires.

## Built-in tag members

| Mod / source | Accepted cluster IDs |
| --- | --- |
| Vanilla Minecraft | `minecraft:amethyst_cluster` |
| Crystal Depths (optional) | `crystaldepths:coal_cluster`, `iron_cluster`, `gold_cluster`, `lapis_cluster`, `diamond_cluster`, `emerald_cluster` (all use `crystaldepths:`) |
| Mythic Upgrades (optional) | `mythicupgrades:ametrine_crystal_cluster`, `aquamarine_crystal_cluster`, `citrine_crystal_cluster`, `jade_crystal_cluster`, `peridot_crystal_cluster`, `ruby_crystal_cluster`, `sapphire_crystal_cluster`, `topaz_crystal_cluster` (all use `mythicupgrades:`) |

The optional entries are marked `required: false`: those other mods do **not** have to be installed.

## Add clusters from your own mod/datapack

Make a matching item tag:

```text
data/prettyfrogs/tags/item/crystal_clusters.json
```

Example (replace `othermod:example_cluster` with a real registered item):

```json
{
  "replace": false,
  "values": [
    { "id": "othermod:example_cluster", "required": false }
  ]
}
```

Combine this datapack with PrettyFrogs and use the actual item on an adult frog. The value `replace: false` preserves the existing accepted clusters.

## Optional appearance settings

The Field Guide's **Extra 3D Decorations** toggle hides/show the crystal crown and back spines. **HD Textures** affects the fallback crystal overlay, not the saved cluster identity. In every case, the server retains the source cluster data.

## Technical details

- Implementation: `FrogFormRegistry.CRYSTAL_CLUSTERS`.
- Saved form: `prettyfrogs:crystal`.
- Synced source: the original item registry ID.
- Current source tag: [crystal_clusters.json](https://github.com/CaszGamerMD/PrettyFrogs/blob/main/src/main/resources/data/prettyfrogs/tags/item/crystal_clusters.json).

[[Frog Forms]] · [[Variants and Special Forms]] · [[Developer Reference]]
