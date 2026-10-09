# Frog Controller

**New item:** `prettyfrogs:frog_controller`

**Finding it in Creative:** Open **Tools & Utilities** or search **Frog Controller**. Both the controller and the PrettyFrogs Field Guide are listed there in builds with the Creative inventory fix. For an immediate test, use `/give @s prettyfrogs:frog_controller`.

Hold a **Frog Controller** and use it on an **adult** frog to temporarily **become the frog**. The camera moves to the frog's eye position (not the player's seat). In first person, you see through its eyes; in third person (F5), the camera follows the frog itself. The player's visible model, equipment and nameplate are suppressed while possessing without granting gameplay invisibility. The controller isn't consumed, and cosmetic frog forms remain unchanged. Use **Sneak + Right-click** to return to your player; Sneak by itself keeps you in frog mode. You may switch hotbar items while possessing without losing control.

## Controls

| Input | Effect |
| --- | --- |
| Right-click adult frog with Frog Controller | Begin controlling (mount) frog |
| WASD | Steer and move |
| Space | Jump (charge and release; native riding input) |
| Left-click | Tongue attack |
| Right-click | Croak sound with frog croak animation |
| Sneak + Right-click | Return to your human character (end possession) |
| Sneak alone | Stay in frog mode |

The actions follow your normal Minecraft **Attack**, **Use**, and **Sneak** keybinds, including remapped mouse buttons. Normal attacks, mining, and held-item use are suppressed during frog possession.

**Tongue:** Targets one living mob **or another player** in front of the frog, at most 3.5 blocks away, with an unobstructed line of sight. A **size-1 Slime** or **size-1 Magma Cube** is attacked through the frog's regular eating attack (so vanilla kill/drop behavior is used). Other mobs and players take **2 base damage (one heart before armor and resistance)**. Larger slimes/magma cubes are *not* swallowed: they take 2 damage. Attacks have a 16-tick cooldown. **Player targets obey the server's PvP setting and team friendly-fire rules**; creative-invulnerable players and spectators are excluded, and a frog cannot tongue its own controlling player.

The attack uses vanilla tongue pose/target rendering and `FROG_TONGUE` sound, and croaking uses `FROG_AMBIENT`. Neither command requires selecting an entity client-side; target detection and PvP validation are server-authoritative. Player hits are attributed to the person controlling the frog.

**Crafting:** Shapeless **Fishing Rod + Lily Pad + Slimeball** gives one Frog Controller. It has no durability or item-consumption cost after crafting.

**Implementation detail:** **Implementation:** Uses an internal passenger attachment to preserve native vanilla mounted movement, jump networking, and authoritative control. The camera and visible representation are fully changed to the frog. The player's actual entity still exists as a passenger (not a spectator or teleporting clone). The camera restores after Sneak + Right-click, death, invalid frog, or world change. Validate camera and animations in-game.

## Source

- `control/FrogControlPayload.java`, `FrogControlNetworking.java`: C2S action packet and server validation
- `mixin/FrogControlMixin.java`: riding movement, jump, action state, tongue and sound
- `client/FrogControllerKeys.java`: use/attack actions and debounced croak/exit packets
- `client/FrogPossessionCamera.java`: frog POV + camera lifecycle restoration
- `mixin/client/MinecraftPossessionActionsMixin.java`: redirect left/right clicks while possessing
- `mixin/PlayerFrogDismountMixin.java`: suppress default sneak-only dismount
- `mixin/client/LocalPlayerPossessionMixin.java`: preserve WASD when camera is redirected
- `mixin/client/AvatarPossessionRenderMixin.java`: hide player avatar and armor visually (does not change invisibility or AI)
- `FrogInteractionHandler.java`: start-control by using item on frog
- `data/prettyfrogs/recipe/frog_controller.json`: crafting recipe

The controller does not overwrite forms, frog breeding, or base NBT. Only the actual riding state is transient.
