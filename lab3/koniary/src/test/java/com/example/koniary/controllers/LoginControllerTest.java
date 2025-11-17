package com.example.koniary.controllers;

import com.example.koniary.exceptions.StableAlreadyExistsException;
import com.example.koniary.model.StableManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testuje logikę związaną z StableManager – bez JavaFX.
 */
public class LoginControllerTest {

    @Test
    void testStableManagerCreation() {
        StableManager manager = new StableManager();

        assertNotNull(manager);
        assertTrue(manager.getStables().isEmpty());
    }

    @Test
    void testAddingDefaultData() {
        StableManager manager = new StableManager();

        try {
            manager.addStable("Test", 10);
        } catch (StableAlreadyExistsException e) {
            fail("Stable should not already exist");
        }

        assertEquals(1, manager.getStables().size());
        assertTrue(manager.search("Test").isPresent());
    }
}
