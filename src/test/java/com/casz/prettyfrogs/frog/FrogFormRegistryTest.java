package com.casz.prettyfrogs.frog;

import com.casz.prettyfrogs.PrettyFrogs;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FrogFormRegistryTest {
    @BeforeClass public static void initialize() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        FrogFormRegistry.bootstrap();
    }

    @Test public void everyOriginalKeepsItsIdentityAndGainsOneCounterpart() throws Exception {
        int originals = 0;
        for (var field : FrogFormRegistry.class.getFields()) {
            if (field.getType() != Identifier.class) continue;
            Identifier original = (Identifier) field.get(null);
            if (original.equals(FrogFormRegistry.NORMAL) || !FrogFormRegistry.isKnown(original)) continue;
            originals++;
            Identifier polished = FrogFormRegistry.polished(original);
            assertEquals(original, FrogFormRegistry.get(original).id());
            assertEquals(polished, FrogFormRegistry.get(polished).id());
            assertTrue(FrogFormRegistry.isPolished(polished));
            assertTrue(FrogFormRegistry.isFamily(polished, original));
            if (FrogFormRegistry.get(original).texture() != null) {
                assertNotEquals(FrogFormRegistry.get(original).texture(), FrogFormRegistry.get(polished).texture());
                assertTrue(FrogFormRegistry.get(polished).texture().getPath().contains("/polished/"));
            }
        }
        assertEquals(37, originals);
        assertEquals(FrogFormRegistry.NORMAL, FrogFormRegistry.get(PrettyFrogs.id("unknown")).id());
    }

    private static void assertCycle(Item item, Identifier first, int count) {
        Set<Identifier> seen = new HashSet<>();
        Identifier current = first;
        for (int i = 0; i < count; i++) {
            assertTrue("Repeated early: " + current, seen.add(current));
            assertTrue(FrogFormRegistry.isKnown(current));
            current = FrogFormRegistry.variantForUse(item, current, RandomSource.create(42));
        }
        assertEquals(first, current);
        assertEquals(count, seen.size());
    }

    @Test public void poolsRetainOriginalOrderAndWrapThroughNewForms() {
        assertCycle(Items.POISONOUS_POTATO, FrogFormRegistry.DARTFROG_1, 16);
        assertCycle(Items.MELON_SLICE, FrogFormRegistry.WATERMELON, 4);
        assertCycle(Items.CAKE, FrogFormRegistry.CAKE, 2);
        assertCycle(Items.POTION, FrogFormRegistry.WATER, 2);
        assertCycle(Items.TADPOLE_BUCKET, FrogFormRegistry.TADPOLE_COSTUME, 2);
        assertEquals(FrogFormRegistry.DARTFROG_2, FrogFormRegistry.nextDart(FrogFormRegistry.DARTFROG_1));
        assertEquals(FrogFormRegistry.polished(FrogFormRegistry.DARTFROG_1), FrogFormRegistry.nextDart(FrogFormRegistry.DARTFROG_8));
        assertEquals(FrogFormRegistry.WATERMELON_RED_TOP, FrogFormRegistry.nextWatermelon(FrogFormRegistry.WATERMELON));
    }

    @Test public void legacyDartsStillResolveAndCanCycle() {
        Identifier legacy = PrettyFrogs.id("dart_red");
        assertEquals(FrogFormRegistry.DARTFROG_6, FrogFormRegistry.get(legacy).id());
        assertTrue(FrogFormRegistry.isDart(legacy));
        assertEquals(FrogFormRegistry.DARTFROG_7, FrogFormRegistry.variantForUse(Items.POISONOUS_POTATO, legacy, RandomSource.create(1)));
    }

    @Test public void crystalStyleTogglesWithoutLosingClusterChoice() {
        Identifier amethyst = Identifier.withDefaultNamespace("amethyst_cluster");
        Identifier other = Identifier.fromNamespaceAndPath("crystal_depths", "diamond_cluster");
        Identifier polished = FrogFormRegistry.polished(FrogFormRegistry.CRYSTAL);
        assertEquals(polished, FrogFormRegistry.crystalVariantForUse(FrogFormRegistry.CRYSTAL, amethyst, amethyst));
        assertEquals(FrogFormRegistry.CRYSTAL, FrogFormRegistry.crystalVariantForUse(polished, amethyst, amethyst));
        assertEquals(polished, FrogFormRegistry.crystalVariantForUse(polished, amethyst, other));
    }
}
