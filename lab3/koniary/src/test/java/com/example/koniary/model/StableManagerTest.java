package com.example.koniary.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StableManagerTest {

    private StableManager manager;

    @BeforeEach
    void setup() {
        manager = new StableManager();
        manager.addStable("Test", 5);
        manager.addStable("Płotkaberg", 3);
    }

    @Test
    void testAddStable() {
        manager.addStable("X", 10);
        assertTrue(manager.getStables().containsKey("X"));
    }

    @Test
    void testRemoveStable() {
        manager.removeStable("Test");
        assertFalse(manager.getStables().containsKey("Test"));
    }

    @Test
    void testSearchExact() {
        assertTrue(manager.search("Test").isPresent());
    }

    @Test
    void testSearchPartial() {
        List<Stable> found = manager.searchPartial("łotk");
        assertEquals(1, found.size());
    }

    @Test
    void testFindEmpty() {
        // wszystkie są puste, bo nie mają koni
        assertEquals(2, manager.findEmpty().size());
    }
}
