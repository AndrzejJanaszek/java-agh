package com.example.koniary.controllers;

import com.example.koniary.model.StableManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testuje tylko logikę – bez JavaFX, bez SceneManagera.
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

        manager.addStable("Test", 10);

        assertEquals(1, manager.getStables().size());
        assertTrue(manager.search("Test").isPresent());
    }
}
