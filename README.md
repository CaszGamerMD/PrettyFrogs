# PrettyFrogs

Fabric 26.2 mod for cosmetic transformations of vanilla frogs.

## Planned transformations

| Item used on frog | Form |
| --- | --- |
| Melon Slice | Watermelon Frog |
| Carved Pumpkin | Pumpkin Frog |
| Ender Pearl | Red-Eyed Tree Frog |
| Bone | Skeleton Frog |
| Milk Bucket | Reset to normal |

## Behavior

A PrettyFrogs form is layered on top of the frog's vanilla identity. The vanilla frog variant is not replaced.

- Breeding remains normal vanilla behavior.
- Special forms are not inherited.
- Froglight behavior remains linked to the frog's underlying vanilla variant.
- Milk removes the transformation.
- Using the item for the form a frog already has should not consume another item.

## Next milestone

Compile against Minecraft 26.2 and implement:
1. synced/persistent form data on vanilla Frog
2. right-click transformation interactions
3. survival item consumption and milk bucket reset
4. 26.2 client render-state integration
5. a development command for setting frog forms
6. custom textures
