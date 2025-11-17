package com.example.koniary.model;

import com.example.koniary.exceptions.InvalidHorseDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HorseTest {

    @Test
    void testConstructorAndGetters() throws InvalidHorseDataException {
        Horse h = new Horse("Płotka", "Temerian", HorseType.COLD_BLOODED,
                HorseCondition.HEALTHY, 7, 5000, 450);

        assertEquals("Płotka", h.getName());
        assertEquals("Temerian", h.getBreed());
        assertEquals(HorseType.COLD_BLOODED, h.getType());
        assertEquals(HorseCondition.HEALTHY, h.getStatus());
        assertEquals(7, h.getAge());
        assertEquals(5000, h.getPrice());
        assertEquals(450, h.getWeight());
    }

    @Test
    void testSetters() throws InvalidHorseDataException {
        Horse h = new Horse("A", "B", HorseType.HOT_BLOODED,
                HorseCondition.SICK, 3, 1000, 200);

        h.setName("Nowe");
        h.setBreed("Rasa");
        h.setType(HorseType.COLD_BLOODED);
        h.setStatus(HorseCondition.TRAINING);
        h.setAge(10);
        h.setPrice(900);
        h.setWeight(333);

        assertEquals("Nowe", h.getName());
        assertEquals("Rasa", h.getBreed());
        assertEquals(HorseType.COLD_BLOODED, h.getType());
        assertEquals(HorseCondition.TRAINING, h.getStatus());
        assertEquals(10, h.getAge());
        assertEquals(900, h.getPrice());
        assertEquals(333, h.getWeight());
    }

    @Test
    void testCompareTo() throws InvalidHorseDataException {
        Horse h1 = new Horse("Anna", "Arabian", HorseType.HOT_BLOODED,
                HorseCondition.HEALTHY, 4, 1000, 400);
        Horse h2 = new Horse("Bartek", "Mustang", HorseType.HOT_BLOODED,
                HorseCondition.HEALTHY, 5, 2000, 500);

        assertTrue(h1.compareTo(h2) < 0);
    }
}
