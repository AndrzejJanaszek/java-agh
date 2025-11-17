package com.example.koniary.model;

import com.example.koniary.exceptions.StableAlreadyExistsException;
import com.example.koniary.exceptions.StableNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StableManagerTest {

    private StableManager manager;

    @BeforeEach
    void setup() throws StableAlreadyExistsException {
        manager = new StableManager();
        manager.addStable("Test", 5);
        manager.addStable("Płotkaberg", 3);
    }

    @Test
    void testAddStable() throws StableAlreadyExistsException {
        manager.addStable("X", 10);
        assertTrue(manager.getStables().containsKey("X"));
    }

    @Test
    void testRemoveStable() throws StableNotFoundException {
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
        assertEquals(2, manager.findEmpty().size());
    }
}
