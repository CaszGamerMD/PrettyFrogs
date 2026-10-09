# Crafting and Recipes

PrettyFrogs adds **three craftable mod items**: the **PrettyFrogs Field Guide**, **Frog Controller**, and **Pretty Frog Bucket**. The frogs themselves are transformations, not separate items; their triggers are described in [[Frog Forms]].

## PrettyFrogs Field Guide

**Shapeless crafting recipe:**

| Ingredient | Quantity |
| --- | --- |
| Book (`minecraft:book`) | 1 |
| Tadpole Bucket (`minecraft:tadpole_bucket`) | 1 |
| **Result**: PrettyFrogs Field Guide (`prettyfrogs:frog_guide`) | **1** |

Place a Book and a Tadpole Bucket in **either** the 2×2 inventory grid or a 3×3 crafting table in any positions.

**Container return:** Crafting returns **one empty Bucket** from the Tadpole Bucket input; the tadpole and book are consumed. This remainder is implemented specifically for the guide recipe, not globally for all tadpole-bucket crafting.

**Creative command:**

```mcfunction
/give @s prettyfrogs:frog_guide
```

The guide has a **Frogs** catalog and **Appearance** settings. See [[Field Guide]].

## Frog Controller

**Shapeless crafting recipe:** Fishing Rod + Lily Pad + Slimeball → **1 Frog Controller** (`prettyfrogs:frog_controller`). No items are returned from this crafting recipe. The controller itself is reusable.

Use it on an adult frog to mount and steer. See [[Frog Controller]] for controls, croaking, tongue attacks, and cooldown.

## Pretty Frog Bucket

**Shapeless crafting recipe:** 1 Bucket + 1 Lily Pad → **1 reusable Pretty Frog Bucket** (`prettyfrogs:frog_keeper_bucket`). Use the empty bucket on a frog and the filled bucket on a block face to release it, preserving all of the frog's persistent data, including custom skins. See [[Pretty Frog Bucket]].

## Other item interactions (not crafting recipes)

| Use an item on an adult frog | Result | Survival remainder |
| --- | --- | --- |
| Water Bottle (water potion) | Water Frog | Glass Bottle |
| Tadpole Bucket | Tadpole Costume Frog | Empty Bucket |
| Milk Bucket | Clear a current custom form | Empty Bucket |
| Any appropriate non-container trigger | Transform frog | Usually consumes 1 trigger |
| Candle on a Cake Frog | Add 1 candle, up to 4 | Candle consumed |

Water Bottles must actually contain **water**; other potion contents do not transform the frog. **Water Buckets are deliberately not a transformation trigger**.

Note: The Field Guide crafting implementation has passed compilation, but interaction and container-return behavior should be checked in-game when updating the mod.

[[Home]] · [[Frog Forms]] · [[Field Guide]]
