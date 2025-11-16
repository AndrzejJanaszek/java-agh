package com.example.koniary.controllers;

import com.example.koniary.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe LOGIKI AdminController – brak JavaFX.
 */
public class AdminControllerTest {

    private StableManager manager;
    private Stable stable;

    @BeforeEach
    void setup() {
        manager = new StableManager();
        manager.addStable("TestStable", 5);

        stable = manager.search("TestStable").orElseThrow();
        stable.addHorse(new Horse("Max", "Arab", HorseType.HOT_BLOODED, HorseCondition.HEALTHY, 5, 3000, 400));
        stable.addHorse(new Horse("Bella", "Fiord", HorseType.COLD_BLOODED, HorseCondition.SICK, 7, 2500, 450));
    }

    @Test
    void testAddHorse() {
        Horse h = new Horse("Płotka", "Witcher", HorseType.HOT_BLOODED,
                HorseCondition.HEALTHY, 4, 5000, 480);

        stable.addHorse(h);

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
