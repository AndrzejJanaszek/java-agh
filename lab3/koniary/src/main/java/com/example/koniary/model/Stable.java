package com.example.koniary.model;

import com.example.koniary.exceptions.StableFullException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "stables")
public class Stable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stableName;
    private int maxCapacity;

    @OneToMany(mappedBy = "stable", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Horse> horseList;

    public Stable() {
    }

    public Stable(String stableName, int maxCapacity) {
        this.stableName = stableName;
        this.maxCapacity = maxCapacity;
        this.horseList = new ArrayList<>();
    }

    public void addHorse(Horse horse) throws StableFullException {
        if (horseList.size() >= maxCapacity) {
            throw new StableFullException("Brak miejsca w stajni " + stableName);
        }
        if (horseList.contains(horse)) {
            return; // ignorujemy duplikat
        }
        horseList.add(horse);
        horse.setStable(this); // ustaw relację z drugiej strony
    }

    public void removeHorse(Horse horse) {
        horseList.remove(horse);
        horse.setStable(null);
    }

    public void sickHorse(Horse horse) {
        horse.setStatus(HorseCondition.SICK);
    }

    public void changeCondition(Horse horse, HorseCondition condition) {
        horse.setStatus(condition);
    }

    public void changeWeight(Horse horse, double kg) {
        horse.setWeight(kg);
    }

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
        return horseList.stream().filter(
                h -> h.getName().contains(fragment) || h.getBreed().contains(fragment)
        ).toList();
    }

    public void summary() {
        System.out.println("Stadnina: " + stableName);
        horseList.forEach(Horse::print);
    }

    public Horse max() {
        return Collections.max(horseList);
    }

    public boolean isEmpty() {
        return horseList.isEmpty();
    }

    // === GETTERY I SETTERY ===

    public Long getId() {
        return id;
    }

    public String getStableName() {
        return stableName;
    }

    public void setStableName(String stableName) {
        this.stableName = stableName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public List<Horse> getHorseList() {
        return horseList;
    }

    public void setHorseList(List<Horse> horseList) {
        this.horseList = horseList;
    }
}
