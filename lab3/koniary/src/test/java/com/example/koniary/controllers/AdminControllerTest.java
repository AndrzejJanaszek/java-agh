package com.example.koniary.controllers;

import com.example.koniary.model.*;
import com.example.koniary.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy LOGIKI modelu używanej przez AdminController.
 * Brak JavaFX – testujemy tylko zachowanie Stable i Horse.
 */
public class AdminControllerTest {

    private StableManager manager;
    private Stable stable;

    @BeforeEach
    void setup() {
        manager = new StableManager();

        // addStable wymaga obsługi wyjątku
        try {
            manager.addStable("TestStable", 5);
        } catch (StableAlreadyExistsException e) {
            fail("Stable should not exist yet");
        }

        stable = manager.search("TestStable").orElseThrow();

        // addHorse wymaga obsługi 3 wyjątków
        try {
            stable.addHorse(new Horse("Max", "Arab",
                    HorseType.HOT_BLOODED, HorseCondition.HEALTHY,
                    5, 3000, 400));

            stable.addHorse(new Horse("Bella", "Fiord",
                    HorseType.COLD_BLOODED, HorseCondition.SICK,
                    7, 2500, 450));

        } catch (InvalidHorseDataException | StableFullException e ) {
            fail("Horse initialization should not fail: " + e.getMessage());
        }
    }

    @Test
    void testAddHorse() {
        Horse h;
        try {
            h = new Horse("Płotka", "Witcher",
                    HorseType.HOT_BLOODED, HorseCondition.HEALTHY,
                    4, 5000, 480);

            stable.addHorse(h);

        } catch (InvalidHorseDataException | StableFullException e) {
            fail("Adding horse should not fail");
            return;
        }

        assertEquals(3, stable.getHorseList().size());
        assertTrue(stable.getHorseList().contains(h));
    }

    @Test
    void testRemoveHorse() {
        Horse target = stable.getHorseList().get(0);

        stable.removeHorse(target);

        assertEquals(1, stable.getHorseList().size());
        assertFalse(stable.getHorseList().contains(target));
    }

    @Test
    void testSortHorsesByName() {
        List<Horse> sorted = stable.sortByName();

        assertEquals("Bella", sorted.get(0).getName());
        assertEquals("Max", sorted.get(1).getName());
    }

    @Test
    void testCountByStatus() {
        long sick = stable.countByStatus(HorseCondition.SICK);

        assertEquals(1, sick);
    }

    @Test
    void testSearchPartial() {
        List<Horse> found = stable.searchPartial("el");

        assertEquals(1, found.size());
        assertEquals("Bella", found.get(0).getName());
    }
}
