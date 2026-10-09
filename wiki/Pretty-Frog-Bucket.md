# Pretty Frog Bucket

The **Pretty Frog Bucket** is a reusable, self-contained frog carrier created for compatibility with PrettyFrogs' custom forms.

## Why a separate bucket?

The third-party **Bucket of Frog** mod focuses on the three vanilla warm, temperate and cold frog variants. Its buckets can recreate a frog without all of the PrettyFrogs extras. The Pretty Frog Bucket stores an entire frog entity's persistent data instead, so special skins survive capture and release.

This is a separate item; the third-party mod can stay installed. The capture does **not** change its behavior or require a mixin into its private code.

## Crafting and using

- **Craft:** Bucket + Lily Pad, anywhere in a crafting grid, shapeless → **1 Pretty Frog Bucket**.
- **Creative:** Tools & Utilities tab; search for **Pretty Frog Bucket**.
- **Capture:** Hold an empty Pretty Frog Bucket, right-click any unmounted living frog. The bucket becomes filled, and the frog disappears from the world.
- **Release:** Right-click the face of a block with the filled bucket. The frog reappears in an unobstructed location next to that face; the bucket becomes empty and can be reused.
- **Command:** `/give @s prettyfrogs:frog_keeper_bucket`.

The filled bucket remembers its frog even when carried in inventory, stored in a chest, dropped, moved between players, or saved and reloaded.

## What gets saved?

The **entire persistent Frog entity data**, not just a texture ID:

- Exact PrettyFrogs form, including Watermelon, Skeleton, Ghost, Tadpole Costume, RGB and more.
- The exact crystal cluster source and Cake Frog candle count/colors.
- Original temperate/warm/cold frog variant and other vanilla frog data.
- Health, age, custom name and other saved entity attributes.

Data is stored in the item's vanilla `minecraft:entity_data` component. The original frog is removed only after its data is serialized successfully; the new frog is placed only if the destination is valid. Releasing gives the new entity a fresh UUID.

## Important compatibility detail

**Use the Pretty Frog Bucket for custom frogs**, not the third-party Bucket of Frog item. It cannot restore form data that was already lost in an older third-party bucket release. An existing frog returned with a normal form will need to be transformed again.

This feature still requires an in-game test in a modpack containing both mods; successful compilation alone does not prove every third-party interaction path.

[[Home]] · [[Frog Forms]] · [[Crafting and Recipes]]
