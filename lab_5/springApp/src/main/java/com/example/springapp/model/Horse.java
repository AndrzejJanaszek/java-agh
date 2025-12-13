package com.example.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "horses")
public class Horse implements Comparable<Horse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String breed;

    @Enumerated(EnumType.STRING)
    private HorseType type;

    @Enumerated(EnumType.STRING)
    private HorseCondition status;

    private int age;
    private double price;
    private double weight;

    @ManyToOne
    @JoinColumn(name = "stable_id")
    @JsonIgnore
    private Stable stable;

    // --- PUSTY KONSTRUKTOR DLA JPA ---
    public Horse() {}

    public Horse(String name, String breed, HorseType type, HorseCondition condition,
                 int age, double price, double weight) {

        this.name = name;
        this.breed = breed;
        this.type = type;
        this.status = condition;
        this.age = age;
        this.price = price;
        this.weight = weight;
    }

    @Override
    public int compareTo(Horse o) {
        int nameCmp = this.name.compareToIgnoreCase(o.name);
        if (nameCmp != 0) return nameCmp;

        int breedCmp = this.breed.compareToIgnoreCase(o.breed);
        if (breedCmp != 0) return breedCmp;

        return Integer.compare(this.age, o.age);
    }

    /* --- GETTERY & SETTERY --- */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public HorseType getType() {
        return type;
    }

    public HorseCondition getStatus() {
        return status;
    }

    public int getAge() {
        return age;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public Stable getStable() {
        return stable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setType(HorseType type) {
        this.type = type;
    }

    public void setStatus(HorseCondition status) {
        this.status = status;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setStable(Stable stable) {
        this.stable = stable;
    }
}
