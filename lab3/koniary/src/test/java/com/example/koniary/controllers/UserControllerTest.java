package com.example.koniary.controllers;

import com.example.koniary.exceptions.HorseAlreadyExistsException;
import com.example.koniary.exceptions.InvalidHorseDataException;
import com.example.koniary.exceptions.StableFullException;
import com.example.koniary.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private Stable stable;

    @BeforeEach
    void setup()
            throws InvalidHorseDataException, StableFullException, HorseAlreadyExistsException {

        stable = new Stable("UserStable", 10);

        stable.addHorse(new Horse("Zebra", "Mix", HorseType.HOT_BLOODED,
                HorseCondition.HEALTHY, 3, 2000, 300));

        stable.addHorse(new Horse("Alfa", "Mustang", HorseType.HOT_BLOODED,
                HorseCondition.SICK, 6, 5000, 450));

        stable.addHorse(new Horse("Płotka", "Witcher", HorseType.COLD_BLOODED,
                HorseCondition.HEALTHY, 4, 3200, 410));
    }

    @Test
    void testSortByPrice() {
        List<Horse> sorted = stable.getHorseList().stream()
                .sorted((a, b) -> Double.compare(a.getPrice(), b.getPrice()))
                .toList();

        assertEquals("Zebra", sorted.get(0).getName());
        assertEquals("Płotka", sorted.get(1).getName());
        assertEquals("Alfa", sorted.get(2).getName());
    }

    @Test
    void testSortByAge() {
        List<Horse> sorted = stable.getHorseList().stream()
                .sorted((a, b) -> Integer.compare(a.getAge(), b.getAge()))
                .toList();

        assertEquals(3, sorted.get(0).getAge());
        assertEquals(4, sorted.get(1).getAge());
        assertEquals(6, sorted.get(2).getAge());
    }

    @Test
    void testFilterByName() {
        List<Horse> filtered = stable.getHorseList().stream()
                .filter(h -> h.getName().toLowerCase().contains("a"))
                .toList();

        assertEquals(3, filtered.size());
    }

    @Test
    void testFilterByCondition() {
        List<Horse> filtered = stable.getHorseList().stream()
                .filter(h -> h.getStatus() == HorseCondition.SICK)
                .toList();

        assertEquals(1, filtered.size());
        assertEquals("Alfa", filtered.get(0).getName());
    }
}
