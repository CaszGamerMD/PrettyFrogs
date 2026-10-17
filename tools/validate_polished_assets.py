"""Validate the additive collection without launching Minecraft. Requires Pillow."""
from pathlib import Path
import re
from PIL import Image

ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT / 'src/main/resources/assets/prettyfrogs/textures/entity/frog'
POLISHED = ASSETS / 'polished'
SKINS = '''watermelon watermelon_red_top pumpkin red_eyed_tree muddy water magma ice
dartfrog_1 dartfrog_2 dartfrog_3 dartfrog_4 dartfrog_5 dartfrog_6 dartfrog_7 dartfrog_8
rainbow rainbow_dart rainbow_eyes rainbow_solid rainbow_disco cherry_blossom bumble
mushroom moss ender glow cactus storm soulfire sculk cake slimy'''.split()
CUSTOM = ['skeleton', 'ghost', 'tadpole_costume', 'crystal_overlay']
UTILITY = '''frog_eyes frog_eyes_rgb cake_candles cactus_spikes ghost_eyes_glow
skeleton_base ghost_base frog_3d_details pumpkin_stem bumble_wings
water_shell ice_shell slimy_shell magma_glow glow_glow soulfire_glow storm_glow
sculk_glow rainbow_glow rainbow_dart_glow rainbow_solid_glow rainbow_disco_a rainbow_disco_b'''.split()

def pixels(path):
    with Image.open(path) as im:
        im.load()
        return im.convert('RGBA').copy()

def validate():
    errors = []
    for path in ASSETS.rglob('*.png'):
        try:
            pixels(path)
        except Exception as exc:
            errors.append(f'{path.relative_to(ROOT)}: {exc}')
    required = set(SKINS + CUSTOM + UTILITY)
    actual = {p.stem for p in POLISHED.glob('*.png')}
    errors += [f'Missing polished texture: {name}' for name in sorted(required - actual)]
    mask = list(pixels(ASSETS / 'watermelon.png').getchannel('A').getdata())
    for path in POLISHED.glob('*.png'):
        im = pixels(path)
        if im.size != (48, 48):
            errors.append(f'{path.name}: expected 48x48, got {im.size}')
            continue
        alpha = list(im.getchannel('A').getdata())
        if path.stem in SKINS and alpha != mask:
            errors.append(f'{path.name}: UV coverage differs from vanilla frog mask')
        if path.stem in SKINS and len(set(im.getdata())) < 10:
            errors.append(f'{path.name}: unexpectedly flat/empty artwork')
        if path.stem.endswith('_glow') and path.stem != 'ghost_eyes_glow' or path.stem.startswith('rainbow_disco_'):
            if not any(alpha):
                errors.append(f'{path.name}: empty emissive pass')
            if any(a and not m for a, m in zip(alpha, mask)):
                errors.append(f'{path.name}: emissive pixels outside frog UVs')
    # Every layer atlas that the form-aware resolver can redirect must exist.
    client = ROOT / 'src/client/java/com/casz/prettyfrogs/client'
    for path in client.glob('*Layer.java'):
        source = path.read_text()
        if 'FrogTextureResolver.selectTexture' not in source:
            continue
        for name in re.findall(r'textures/entity/frog/([a-z0-9_]+)\.png', source):
            if not (POLISHED / (name + '.png')).exists():
                errors.append(f'{path.name}: redirected atlas missing: {name}')
    assert not errors, '\n'.join(errors)
    print(f'PASS: {len(SKINS)+len(CUSTOM)} new appearances; {len(actual)} valid 48x48 atlases; all layer references present.')

if __name__ == '__main__':
    validate()
