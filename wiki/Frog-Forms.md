# Frog Forms

Use the listed item on an **adult vanilla frog**. Each form is cosmetic; the frog's original warm/temperate/cold type and froglight behavior remain unchanged.

## Complete transformation index

| Frog form | Right-click item | Form ID / notes |
| --- | --- | --- |
| Watermelon Frog | Melon Slice | `watermelon`, green top/red seeded belly |
| Red-top Watermelon Frog | Melon Slice | `watermelon_red_top`, red top/green belly |
| Pumpkin Frog | Carved Pumpkin | `pumpkin`, pumpkin styling and stem |
| Tadpole Costume Frog | Tadpole Bucket | `tadpole_costume`, 3D hood + tail; bucket returned |
| Red-Eyed Tree Frog | Ender Pearl | `red_eyed_tree` |
| Skeleton Frog | Bone | `skeleton`, articulated 3D skeletal model |
| Muddy Frog | Mud Block | `muddy`, mud particles |
| Water Frog | Water Bottle | `water`, translucent shell; bottle returned |
| Magma Frog | Magma Cream | `magma`, glow and flame particles |
| Ice Frog | Ice Block | `ice`, icy shell and snow particles |
| Red Dart Frog | Poisonous Potato | `dart_red` |
| Blue Dart Frog | Poisonous Potato | `dart_blue` |
| Yellow Dart Frog | Poisonous Potato | `dart_yellow` |
| Green Dart Frog | Poisonous Potato | `dart_green` |
| Orange Dart Frog | Poisonous Potato | `dart_orange` |
| Rainbow Frog | RGB End Rod* | `rainbow` |
| RGB Dart Frog | RGB End Rod* | `rainbow_dart` |
| RGB Eyes Frog | RGB End Rod* | `rainbow_eyes` |
| Solid RGB Frog | RGB End Rod* | `rainbow_solid` |
| Disco Frog | RGB End Rod* | `rainbow_disco` |
| Cherry Blossom Frog | Pink Petals | `cherry_blossom` |
| Bumblefrog | Honeycomb | `bumble`, wing accessory |
| Mushroom Frog | Red Mushroom | `mushroom`, optional 3D mushrooms |
| Moss Frog | Moss Block | `moss`, airborne spore particles |
| Ender Frog | Chorus Fruit | `ender`, portal particles |
| Ghost Frog | Soul Sand | `ghost`, custom floating spectral model |
| Glow Frog | Glow Ink Sac | `glow` |
| Cactus Frog | Cactus | `cactus`, 3D spikes |
| Storm Frog | Lightning Rod | `storm`, electric particles |
| Soulfire Frog | Soul Torch | `soulfire` |
| Sculk Frog | Sculk Block | `sculk`, glowing teal eyes |
| Cake Frog | Cake | `cake`, can add four candles |
| Slimy Frog | Slime Block | `slimy`, translucent slime shell |
| Crystal Frog | Any cluster in `#prettyfrogs:crystal_clusters` | `crystal`, source-specific color |

* The optional RGB trigger is the registered item `colorful_rods:rgb_end_rod`. It is not a vanilla End Rod or Redstone Dust.

## Cycling multi-form families

- **Watermelon:** on first Melon Slice, one of two colors is selected; future Melon Slices alternate.
- **Poison Dart:** first Poisonous Potato chooses a random dart color; future uses advance Red → Blue → Yellow → Green → Orange → Red.
- **RGB:** when the optional RGB End Rod is present, repeat-use cycles Rainbow → RGB Dart → RGB Eyes → Solid RGB → Disco → Rainbow.
- **Crystals:** use another supported crystal cluster to change the source and color. Repeating the *same* cluster does not consume it.

Single-form triggers cannot be spent repeatedly on an already-matching frog. See [[Variants and Special Forms]] for further details.

## Carrying frogs without losing their appearance

Make a **Pretty Frog Bucket** with a Bucket and Lily Pad (shapeless). Use the empty Pretty Frog Bucket on a frog to capture it; use the filled bucket on a block face to release it. It saves the full frog data, including all PrettyFrogs forms, crystal cluster, and Cake Frog candle colors.

The third-party Bucket of Frog mod is separate. Its vanilla-variant bucket items may recreate a normal frog and lose PrettyFrogs' special forms. For transformed frogs, use the Pretty Frog Bucket until the other mod provides complete custom-data preservation.

## Resetting

**Milk Bucket** removes the special form, returning the frog to its vanilla appearance. In survival, the milk bucket becomes an empty bucket.

For render behavior, see [[Special Effects and Models]]. For recipes, see [[Crafting and Recipes]].
