# Polished 48px collection

This collection adds 37 appearances alongside the 37 existing forms. Original
IDs and textures remain available; the only original texture repaired is the
previously corrupt water PNG, restored from its last valid artwork.

Use the same transformation items. Repeated uses cycle through the original
pool first and the polished pool second. The eight original dart frogs retain
their order, followed by eight polished darts. Crystal frogs toggle styles when
given the same cluster again; changing cluster preserves the current style.

All new PNGs live under `textures/entity/frog/polished/` and are exactly 48x48.
Base skins use the vanilla frog UV layout. Skeleton, ghost, tadpole and accessory
textures retain their existing normalized atlas coordinates. Their model UV
canvas sizes are deliberately unchanged: PNG dimensions need not equal the
logical model UV dimensions.

The artwork adds clustered highlights, material shading and family-specific
markings. Emissive passes are selected from each new skin so cracks, veins and
RGB markings align. Water, ice and slime use matching translucent glints.

Rear crystals, mushrooms, icing, snow and cactus spines are anchored to the
visible dorsal head surface. The vanilla animation hierarchy is retained.
Existing forms share these attachment repairs.

Run `python tools/validate_polished_assets.py` (Pillow required) to check image
decoding, dimensions, required assets, skin coverage and glow-mask containment.
Run `gradle build` with Java 25 to compile and test pool ordering, legacy IDs and
crystal-style selection.
