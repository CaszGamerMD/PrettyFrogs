# Troubleshooting

PrettyFrogs is a **Fabric Minecraft 26.2** mod; version mismatches are a common reason for it not loading.

## The mod does not load

Check these first:

1. Minecraft is exactly the compatible **26.2** target.
2. You are using **Fabric Loader 0.19.3+** and the matching **Fabric API**.
3. You are running **Java 25+**.
4. The `mods` folder contains **one** PrettyFrogs JAR, not multiple old versions.
5. Check `latest.log` or the crash report for the first actual exception (not only the final crash summary).

If the mod is on a server, check that its version and dependencies match the clients.

## A frog will not transform

- Transformations work on **adult frogs only**, not tadpoles.
- Use the correct item from [[Frog Forms]].
- Water Frog requires a **Water Bottle** (Potion with water contents), not a Water Bucket or another potion.
- Crystal Frogs require a **full cluster item** in `#prettyfrogs:crystal_clusters`.
- RGB forms require the optional registry item `colorful_rods:rgb_end_rod`, not ordinary Redstone or an End Rod.
- Reusing the same single-form trigger on the same form does not consume or change anything.

## The Field Guide is missing

Craft it using **Book + Tadpole Bucket** (shapeless) or use:

```mcfunction
/give @s prettyfrogs:frog_guide
```

Make sure you are running a build that includes the Field Guide crafting recipe. For a server, ensure the recipe exists on the **server** and not only a modified client.

If a guide recipe returns no bucket, report whether it happened in the inventory grid or crafting table, your game version, and the mod JAR version; the bucket remainder was added in a custom recipe implementation and should be verified in-game.

## Textures show missing / pink-black patterns

- Check that the correct PNG files and lowercase resource names are included in the JAR.
- If you edited textures, keep their declared sizes and UV coordinates.
- RGB glow masks must be transparent outside the intended glowing pixels.
- The **HD Textures** switch only replaces certain assets; Skeleton, Ghost and Tadpole Costume atlases have their own UV definitions.
- Check for resource packs that override `prettyfrogs` assets.

See [[Textures and Blockbench]].

## Strange 3D parts, clipping or invisible models

- Try **Extra 3D Decorations: OFF** in the [[Appearance Settings]] to isolate the shared-detail models. This does **not** hide the custom Skeleton/Ghost/Tadpole bodies.
- Check front, rear, side, jumping and croaking views.
- The skeleton, ghost, hood, candle, and accessory models use Java model layers; using a new Blockbench UV without a matching Java change can distort them.
- Some fullbright or translucent effects may look different with shaders/resource packs.

## Book shows wrong RGB trigger

The current book displays **Redstone as a placeholder** for RGB entries. The real interaction trigger is the optional `colorful_rods:rgb_end_rod`.

## Reporting an issue

Create an issue on [GitHub Issues](https://github.com/CaszGamerMD/PrettyFrogs/issues) and include:

- Minecraft/Fabric Loader/Fabric API/Java versions.
- PrettyFrogs commit or JAR build artifact.
- Whether it happens in singleplayer or multiplayer.
- Active shaders/resource packs and relevant other mods.
- Precise reproduction steps and the affected frog form.
- Screenshot/video for visual bugs or `latest.log`/crash trace for crashes.

The repository build checks **compilation and packaging**, not every visual in a running client.

[[Home]] · [[Getting Started]] · [[Appearance Settings]]
