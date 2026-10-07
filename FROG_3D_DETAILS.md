# Pretty Frogs — experimental 3D details branch

Branch: `feature/frog-3d-decorations` (do not merge until in-game QA).

## What this adds

- **Crystal Frog:** a crown of 3D crystal points plus smaller back spikes. Uses the existing crystal form and current source-cluster color for tinting; the normal frog body and existing crystal overlay still render.
- **Mushroom Frog:** three blocky red-and-ivory mushrooms rising from the head and back, textured with spotted caps.
- **Water (balloon) Frog:** a little 3D balloon neck/knot with a hanging tail under the body. Existing water shell remains.
- Every normal/other form is unchanged; no new transforms, slots, NBT, behaviors or hitboxes.

## Adding the next frog detail

1. Add a `Frog3DDetailKind` enum value and its `forForm` mapping.
2. Add a named mesh group under the correct frog bone (head or body) inside `Frog3DDetailsModel.createBodyLayer`.
3. Store the baked model part and hide/show it inside `setDetail`.
4. Reserve non-overlapping texture coordinates in the 64x64 `frog_3d_details.png` atlas. Do **not** alter the 48x48 base skins or their UV masks.
5. If a detail needs distinct translucency/emissive behavior, select its render type in `Frog3DDetailsLayer`. Do not add duplicate layers to every frog.
6. Verify head/body animation binding while hopping, walking, croaking, swimming, and facing each direction before merge.

## In-game QA before merge

- Crystal points shouldn't cover the eyes; tint must match **each** selectable crystal cluster. The underlying frog must not show missing-texture pixels.
- Mushroom caps should look red/spotted with light stalks, remain attached during movement, and not duplicate or obstruct the face.
- Balloon tie should hang *under* the Water Frog and not pass visibly through ground or the water shell.
- Other forms should not accidentally show 3D details. Test both Cake candles and Pumpkin stem to check independent layers still render correctly.
- Check all three from front/side/back and while moving; CI only validates compilation and asset packaging.

This is a visual experiment on a separate branch; keep `main` as the stable QA baseline.
