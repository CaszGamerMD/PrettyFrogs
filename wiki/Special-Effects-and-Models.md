# Special Effects and Models

PrettyFrogs changes appearances and renders effects **without changing frog combat, drops, collision, or vanilla froglight logic**.

## Particles and visual effects

| Frog | Visual effect |
| --- | --- |
| Muddy | Mud particles near the ground while moving |
| Water | Dripping water and bubbles when in water |
| Magma | Harmless flame, occasional smoke, lava-like trail |
| Ice | Snowflakes and small white ash near the ground |
| Rainbow | End Rod particles (primary Rainbow form) |
| Cherry Blossom | Falling cherry-leaf/petal particles, especially while airborne |
| Bumblefrog | Pollen/wax-like particles while airborne |
| Moss | Floating spores |
| Ender | Portal particles |
| Ghost | Occasional soul particles |
| Glow | Glow particles |
| Storm | Electric sparks |
| Soulfire | Soul Fire Flame particles |
| Sculk | Sculk Charge Pop particles near the ground |
| Slimy | Slime droplets, more active while airborne |
| Cake (with candles) | Small candle-flame particles |

The particle visual effects are cosmetic; they do not burn, poison, freeze, or electrocute other entities.

## Dedicated 3D models and accessories

| Model or accessory | Frog(s) | What it adds |
| --- | --- | --- |
| Custom Skeleton Frog | Skeleton | Skull, ribs, spine, bones, limbs |
| Custom Ghost Frog | Ghost | Floating ghost head/body, wisps, spectral tail |
| Tadpole Costume | Tadpole Costume | Hood eyes, costume hood, tapered tail |
| Pumpkin stem | Pumpkin | Small 3D stem |
| Cake candles | Cake | Up to four custom-colored candle bodies and flames |
| Bumble wings | Bumblefrog | Custom wing accessory |
| Cactus spikes | Cactus | 3D spikes |
| Shared extra 3D detail layer | Crystal | Crystal crown and back spines |
| Shared extra 3D detail layer | Mushroom | Three small mushrooms |
| Shared extra 3D detail layer | Water | Small balloon-style knot and hanging tail |
| Shared extra 3D detail layer | Cake | Frosting cap on top of head/back |
| Shared extra 3D detail layer | Ice | Settled snow cap |

**Only the bottom five shared-detail entries** are controlled by the Field Guide's **Extra 3D Decorations** switch. Custom skeletal/spectral anatomy and the costume hood are not removed by it.

## Shells, transparency and glow

- **Water** and **Ice** frogs have translucent outer shell layers.
- **Ghost** and **Slimy** frogs use special translucent/spectral or slime-style rendering.
- **Magma, Glow, Storm, Soulfire** and RGB frogs have emissive/full-bright texture masks.
- **Sculk** has bright teal eyes; **RGB Eyes** animates a special eye overlay.
- **Crystal** frogs use their source crystal or a tinted fallback overlay.

Because several forms use custom client rendering, a successful Gradle build does **not** guarantee every visual looks correct in-game. Check hopping, walking, croaking and viewing a frog from all angles.

[[Home]] · [[Appearance Settings]] · [[Troubleshooting]]
