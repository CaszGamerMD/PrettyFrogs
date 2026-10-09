# Frog Controller

**New item:** `prettyfrogs:frog_controller`

Hold a **Frog Controller** and use it on an **adult** frog to mount it and temporarily steer that frog. The controller is not consumed, and the frog retains any PrettyFrogs cosmetic appearance. Use the ordinary **Sneak** key to dismount.

## Controls

| Input | Effect |
| --- | --- |
| Right-click adult frog with Frog Controller | Begin controlling (mount) frog |
| WASD | Steer and move |
| Space | Jump (charge and release; native riding input) |
| C | Croak sound with frog croak animation |
| V | Tongue attack |
| Sneak | Dismount / stop controlling |

C and V are regular rebindable key mappings under the Minecraft controls menu.

**Tongue:** Targets one living non-player mob in front of the frog, at most 3.5 blocks away, with an unobstructed line of sight. A **size-1 Slime** or **size-1 Magma Cube** is attacked through the frog's regular eating attack (so vanilla kill/drop behavior is used). Other non-player living mobs take **2 damage (one heart)**. Larger slimes/magma cubes are *not* swallowed: they take 2 damage. Attacks have a 16-tick cooldown.

The attack uses vanilla tongue pose/target rendering and `FROG_TONGUE` sound, and croaking uses `FROG_AMBIENT`. Neither command requires selecting an entity client-side; target detection is server-authoritative. The player cannot strike other players with this feature.

**Crafting:** Shapeless **Fishing Rod + Lily Pad + Slimeball** gives one Frog Controller. It has no durability or item-consumption cost after crafting.

**Implementation detail:** This first iteration is **ride-based control**. The player is a passenger on the frog, not an invisible possession camera/remote drone. It should be evaluated in-game for look/feel, jump behavior and dismount safety.

## Source

- `control/FrogControlPayload.java`, `FrogControlNetworking.java`: C2S action packet and server validation
- `mixin/FrogControlMixin.java`: riding movement, jump, action state, tongue and sound
- `client/FrogControllerKeys.java`: rebindable keys and client send logic
- `FrogInteractionHandler.java`: start-control by using item on frog
- `data/prettyfrogs/recipe/frog_controller.json`: crafting recipe

The controller does not overwrite forms, frog breeding, or base NBT. Only the actual riding state is transient.
