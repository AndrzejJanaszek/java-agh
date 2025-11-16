package com.example.koniary.model;

import java.util.*;

public class StableManager {
    private Map<String, Stable> stables = new HashMap<>();

    public void addStable(String name, int capacity) {
        stables.put(name, new Stable(name, capacity));
    }

    public void removeStable(String name) {
        stables.remove(name);
    }

    public List<Stable> findEmpty() {
        return stables.values().stream().filter(Stable::isEmpty).toList();
    }

    public void summary() {
        stables.forEach((name, stable) -> {
            double fill = (double) stable.sortByName().size() / stable.getMaxCapacity() * 100;
            System.out.printf("%s – zapełnienie: %.1f%%%n", name, fill);
        });
    }

    public Optional<Stable> search(String name) {
        return Optional.ofNullable(stables.get(name));
    }

    public List<Stable> searchPartial(String fragment) {
        return stables.entrySet().stream()
                .filter(e -> e.getKey().toLowerCase().contains(fragment.toLowerCase()))
                .map(Map.Entry::getValue)
                .toList();
    }

    public Map<String, Stable> getStables() {
        return stables;
    }

    public void loadDefaultData() {

        // ------ Stadnina 1 ------
        Stable s1 = new Stable("Stadnina Jednorożców", 8);
        s1.addHorse(new Horse("Błyskotek", "Jednorożec Górski", HorseType.HOT_BLOODED, HorseCondition.HEALTHY, 120, 20000, 380));
        s1.addHorse(new Horse("Pastelozaur", "Jednorożec Pastelowy", HorseType.HOT_BLOODED, HorseCondition.TRAINING, 45, 18000, 350));

        // ------ Stadnina 2 ------
        Stable s2 = new Stable("Wiedźmińska Zagroda", 12);
        s2.addHorse(new Horse("Płotka", "Koń Geralta", HorseType.COLD_BLOODED, HorseCondition.HEALTHY, 7, 5000, 420));
        s2.addHorse(new Horse("Kasztanek", "Koń Temerski", HorseType.COLD_BLOODED, HorseCondition.SICK, 10, 2400, 480));
        s2.addHorse(new Horse("Bucefał", "Koń Nilfgaardzki", HorseType.HOT_BLOODED, HorseCondition.TRAINING, 6, 5200, 460));

        // ------ Stadnina 3 ------
        Stable s3 = new Stable("Zagroda Podlaska", 6);
        s3.addHorse(new Horse("Zenek", "Koń Disco Polo", HorseType.COLD_BLOODED, HorseCondition.HEALTHY, 4, 3000, 500));
        s3.addHorse(new Horse("Klarynek", "Koń Podlaski", HorseType.COLD_BLOODED, HorseCondition.QUARANTINE, 5, 2500, 550));

        // ------ Stadnina 4 ------
        Stable s4 = new Stable("Stajnia Konia Płotki", 4);
        s4.addHorse(new Horse("Płotka 2.0", "Koń Mutant", HorseType.HOT_BLOODED, HorseCondition.TRAINING, 3, 7000, 390));
        s4.addHorse(new Horse("Bugowóz", "Koń, który przenika przez ściany", HorseType.HOT_BLOODED, HorseCondition.HEALTHY, 2, 9999, 300));

        // ------ Stadnina 5 ------
        Stable s5 = new Stable("Koński Raj Mordoru", 10);
        s5.addHorse(new Horse("Ognik", "Koń Ognisty", HorseType.HOT_BLOODED, HorseCondition.HEALTHY, 12, 8000, 610));
        s5.addHorse(new Horse("Czarny Jeździec", "Rumak Nazgula", HorseType.HOT_BLOODED, HorseCondition.SICK, 100, 15000, 700));

        stables.put(s1.getStableName(), s1);
        stables.put(s2.getStableName(), s2);
        stables.put(s3.getStableName(), s3);
        stables.put(s4.getStableName(), s4);
        stables.put(s5.getStableName(), s5);
    }

}
