# Variants and Special Forms

This page covers frogs that behave differently from ordinary single-item transformations.

## Watermelon frogs

**Trigger:** Melon Slice

Two variants share the same transformation item:

- **Watermelon** (`prettyfrogs:watermelon`): green striped upper body, red seeded underside.
- **Red-top Watermelon** (`prettyfrogs:watermelon_red_top`): red seeded top, green lower body.

The first slice chooses one variant randomly; subsequent slices alternate between the two.

## Poison Dart frogs

**Trigger:** Poisonous Potato

The first use randomly selects a color. Additional uses cycle in this fixed order:

`dart_red` → `dart_blue` → `dart_yellow` → `dart_green` → `dart_orange` → back to `dart_red`.

These are cosmetic variants; PrettyFrogs does **not** add actual poison attacks.

## Rainbow / RGB frogs

**Trigger:** Optional `colorful_rods:rgb_end_rod` item, if registered.

Cycling order:

1. `rainbow` — animated RGB areas.
2. `rainbow_dart` — RGB dart-frog appearance.
3. `rainbow_eyes` — animated RGB eyes only.
4. `rainbow_solid` — solid-color RGB treatment.
5. `rainbow_disco` — two separately animated color mask regions.

RGB effects are texture-mask driven and render with a full-bright emissive layer. Without the optional item, this family cannot be reached through its normal interaction trigger. See [[Textures and Blockbench]] for modifying RGB placement.

## Crystal frogs

**Trigger:** Any item in the `#prettyfrogs:crystal_clusters` item tag.

A crystal frog remembers **which exact cluster** was used. Different clusters change its color and appearance. Applying the same source cluster again does nothing; applying a different one changes the source. An optional 3D crystal crown and spines can be hidden with the Appearance settings.

See [[Crystal Frog Compatibility]].

## Tadpole Costume frog

**Trigger:** Tadpole Bucket. **Remainder:** Empty Bucket in survival.

This is **a frog wearing a tadpole outfit**, not a replacement by a tadpole:

- Reusable, open-front frog-sized hood.
- Raised tadpole costume eyes.
- Tapered 3D tadpole tail and tail fin.
- Original frog body and face remain visible.

Developers can reuse the generic `BasicFrogHoodModel` and `basic_hood.png` for other costumes. The plain hood is **not** currently a separate, obtainable form.

## Skeleton and Ghost frogs

- **Skeleton Frog:** a bespoke animated frog skeleton, with rib cage, raised eye socket skull, spine, articulated limbs, and separate bone atlas.
- **Ghost Frog:** separate translucent 3D spectral model with hovering silhouette, broad frog-like face, recessed glowing pupils, arms, wispy lower body, and trailing spectral tail.

These bespoke body models remain enabled even when **Extra 3D Decorations** is switched off (that toggle affects only the optional shared detail layer).

## Cake frog

**Trigger:** Cake. Then right-click that Cake Frog with candles.

Add up to **four candles**, one per interaction. Candle colors are stored per slot and synchronize with the frog. New candles do not require another cake. The optional frosting top is governed by **Extra 3D Decorations**; the candles render independently.

[[Home]] · [[Frog Forms]] · [[Appearance Settings]]
