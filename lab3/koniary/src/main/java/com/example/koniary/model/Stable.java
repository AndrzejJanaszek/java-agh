package com.example.koniary.model;
import java.util.*;

public class Stable {
    private String stableName;
    private List<Horse> horseList;
    private int maxCapacity;

    public Stable(String stableName, int maxCapacity) {
        this.stableName = stableName;
        this.maxCapacity = maxCapacity;
        this.horseList = new ArrayList<>();
    }

    public void addHorse(Horse horse) {
        if (horseList.size() >= maxCapacity) {
            System.err.println("Brak miejsca w stadninie!");
            return;
        }
        if (horseList.stream().anyMatch(h -> h.equals(horse))) {
            System.out.println("Taki koń już istnieje.");
            return;
        }
        horseList.add(horse);
    }

    public void removeHorse(Horse horse) { horseList.remove(horse); }

    public void sickHorse(Horse horse) { horse.setStatus(HorseCondition.SICK); }

    public void changeCondition(Horse horse, HorseCondition condition) { horse.setStatus(condition); }

    public void changeWeight(Horse horse, double kg) { horse.setWeight(kg); }

    public long countByStatus(HorseCondition status) {
        return horseList.stream().filter(h -> h.getStatus() == status).count();
    }

    public List<Horse> sortByName() {
        return horseList.stream().sorted(Comparator.comparing(Horse::getName)).toList();
    }

    public List<Horse> sortByPrice() {
        return horseList.stream().sorted(Comparator.comparingDouble(Horse::getPrice)).toList();
    }

    public Optional<Horse> search(String name) {
        return horseList.stream().filter(h -> h.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<Horse> searchPartial(String fragment) {
        return horseList.stream().filter(h -> h.getName().contains(fragment) || h.getBreed().contains(fragment)).toList();
    }

    public void summary() {
        System.out.println("Stadnina: " + stableName);
        horseList.forEach(Horse::print);
    }

    public Horse max() {
        return Collections.max(horseList);
    }

    public boolean isEmpty() { return horseList.isEmpty(); }

    /* --- GETTERY & SETTER --- */

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public List<Horse> getHorseList() {
        return horseList;
    }

    public String getStableName() {
        return stableName;
    }

    public void setHorseList(List<Horse> horseList) {
        this.horseList = horseList;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setStableName(String stableName) {
        this.stableName = stableName;
    }
}
