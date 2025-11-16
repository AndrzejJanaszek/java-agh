package com.example.koniary.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StableTest {

    private Stable stable;

    @BeforeEach
    void setup() {
        stable = new Stable("Test Stable", 3);
    }

    @Test
    void testAddHorse() {
        Horse h = new Horse("A", "B", HorseType.COLD_BLOODED, HorseCondition.HEALTHY, 3, 1000, 400);
        stable.addHorse(h);

        assertEquals(1, stable.getHorseList().size());
    }

    @Test
    void testAddHorseDuplicate() {
        Horse h = new Horse("A", "B", HorseType.HOT_BLOODED, HorseCondition.HEALTHY, 5, 2000, 500);

        stable.addHorse(h);
        stable.addHorse(h);  // drugi raz

        assertEquals(1, stable.getHorseList().size());
    }

    @Test
    void testAddHorseCapacityExceeded() {
        stable.addHorse(new Horse("1","A",HorseType.COLD_BLOODED,HorseCondition.SICK,1,1,1));
        stable.addHorse(new Horse("2","A",HorseType.COLD_BLOODED,HorseCondition.SICK,1,1,1));
        stable.addHorse(new Horse("3","A",HorseType.COLD_BLOODED,HorseCondition.SICK,1,1,1));

        // to już ponad pojemność
        stable.addHorse(new Horse("4","A",HorseType.COLD_BLOODED,HorseCondition.SICK,1,1,1));

        assertEquals(3, stable.getHorseList().size());
    }

    @Test
    void testRemoveHorse() {
        Horse h = new Horse("A","B",HorseType.HOT_BLOODED,HorseCondition.HEALTHY,2,2000,350);

        stable.addHorse(h);
        stable.removeHorse(h);

        assertEquals(0, stable.getHorseList().size());
    }

    @Test
    void testSearchExact() {
        Horse h = new Horse("Płotka","Temerian",HorseType.COLD_BLOODED,HorseCondition.HEALTHY,7,5000,450);
        stable.addHorse(h);

        assertTrue(stable.search("Płotka").isPresent());
    }

    @Test
    void testSearchPartial() {
        stable.addHorse(new Horse("Kasztanek","Winnica",HorseType.HOT_BLOODED,HorseCondition.HEALTHY,4,3000,350));

        List<Horse> results = stable.searchPartial("Kas");
        assertEquals(1, results.size());
    }

    @Test
    void testSortByName() {
        stable.addHorse(new Horse("Zenek","B",HorseType.COLD_BLOODED,HorseCondition.HEALTHY,3,2000,300));
        stable.addHorse(new Horse("Adam","A",HorseType.COLD_BLOODED,HorseCondition.HEALTHY,3,2000,300));

        List<Horse> sorted = stable.sortByName();

        assertEquals("Adam", sorted.get(0).getName());
    }

    @Test
    void testCountByStatus() {
        stable.addHorse(new Horse("A","B",HorseType.COLD_BLOODED,HorseCondition.HEALTHY,1,1,1));
        stable.addHorse(new Horse("B","C",HorseType.COLD_BLOODED,HorseCondition.SICK,1,1,1));

        assertEquals(1, stable.countByStatus(HorseCondition.SICK));
    }

    @Test
    void testIsEmpty() {
        assertTrue(stable.isEmpty());
        stable.addHorse(new Horse("A","B",HorseType.HOT_BLOODED,HorseCondition.HEALTHY,2,10,10));
        assertFalse(stable.isEmpty());
    }
}
