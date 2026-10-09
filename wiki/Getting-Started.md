# Getting Started

## Requirements

| Requirement | Current target |
| --- | --- |
| Minecraft Java Edition | **26.2** |
| Mod loader | **Fabric Loader 0.19.3 or newer**, compatible with MC 26.2 |
| Dependency | **Fabric API** for MC 26.2 |
| Java runtime | **Java 25 or newer** |
| Mod ID | `prettyfrogs` |

For developers, the repository currently uses **Gradle 9.7**, Fabric Loom, and Java 25. The mod is designed for both client and server use: the server manages persistent frog forms; clients render the custom appearances.

## Install

1. Set up a Minecraft 26.2 Fabric instance and install Fabric API.
2. Download a successful PrettyFrogs JAR from the [GitHub Actions builds](https://github.com/CaszGamerMD/PrettyFrogs/actions) (open a successful **build** run and find its **PrettyFrogs-1.0.0** artifact), or use a published release if available.
3. Put **one** PrettyFrogs JAR in the instance's `mods` folder. Remove older duplicate copies.
4. On a multiplayer server, install the mod and matching dependencies on the server **and** participating clients for consistent transformations/rendering.
5. Launch the game.

The RGB End Rod trigger is **optional integration** with an item registered as `colorful_rods:rgb_end_rod`. Without that item, the rest of PrettyFrogs still works, but the RGB family has no standard transformation trigger.

## Creative inventory

The **Frog Controller** and **PrettyFrogs Field Guide** are under **Tools & Utilities** and appear in Creative search. Commands: `/give @s prettyfrogs:frog_controller` or `/give @s prettyfrogs:frog_guide`.

## Carry a transformed frog

For bucket transport of a Watermelon, Skeleton, Ghost, Crystal, Cake or other custom frog, use a **Pretty Frog Bucket** (Bucket + Lily Pad). It saves the entire frog, unlike vanilla-variant-only third-party bucket mods. See [[Pretty Frog Bucket]].

## Control a frog

Make the **Frog Controller** by combining a **Fishing Rod + Lily Pad + Slimeball** in any crafting grid. Use the controller on an adult frog to become it; WASD moves at frog speed, tapping Space performs one normal hop, left-click shoots its tongue (visible in first-person), right-click croaks, and Sneak + right-click returns you to your human character. See [[Frog Controller]].

## Transform your first frog

Right-click an **adult** frog with a trigger item. For example:

- **Melon Slice** → Watermelon Frog (two color arrangements).
- **Bone** → Skeleton Frog with custom 3D bones.
- **Soul Sand** → Ghost Frog.
- **Tadpole Bucket** → Tadpole Costume Frog, with hood and tail.

The transformation item is consumed in survival unless the code returns a container. In creative mode, transformation items are not consumed. Repeating a trigger for a single-version form does nothing; on certain multi-version families it cycles appearances.

Use a **Milk Bucket** to reset the frog; it returns an empty bucket in survival.

See the full [[Frog Forms]] index and [[Crafting and Recipes]] for container returns.

## Useful command

To get a Field Guide for testing:

```mcfunction
/give @s prettyfrogs:frog_guide
```

Or make one in survival using a **Book + Tadpole Bucket**, arranged anywhere in a crafting grid.

## Unchanged vanilla behavior

- Warm / temperate / cold underlying frog type is preserved.
- Froglight drops remain tied to the original vanilla frog type.
- Breeding follows vanilla behavior; custom appearance is **not inherited**.
- Tadpoles and baby frogs are not transformable.

[[Home]] · [[Field Guide]] · [[Troubleshooting]]
