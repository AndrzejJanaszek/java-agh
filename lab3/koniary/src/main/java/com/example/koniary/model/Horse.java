package com.example.koniary.model;

import com.example.koniary.exceptions.InvalidHorseDataException;

import javax.persistence.*;

@Entity
@Table(name = "horses")
public class Horse implements Comparable<Horse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // <-- jedyny nowy field

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
    private Stable stable;

    // --- PUSTY KONSTRUKTOR DLA HIBERNATE ---
    public Horse() {}

    // --- TWÓJ ORYGINALNY KONSTRUKTOR Z WALIDACJĄ (NIE ZMNIENIAM ANI ZNAKU!) ---
    public Horse(String name, String breed, HorseType type, HorseCondition condition,
                 int age, double price, double weight) throws InvalidHorseDataException {

        if (age < 0) throw new InvalidHorseDataException("Wiek nie może być ujemny!");
        if (price < 0) throw new InvalidHorseDataException("Cena nie może być ujemna!");
        if (weight <= 0) throw new InvalidHorseDataException("Waga musi być > 0!");
        if (name == null || name.isBlank())
            throw new InvalidHorseDataException("Koń musi mieć imię!");

        this.name = name;
        this.breed = breed;
        this.type = type;
        this.status = condition;
        this.age = age;
        this.price = price;
        this.weight = weight;
    }

    public void print() {
        System.out.printf("%s (%s, %s) - %d lat, %.2f zł, %.1f kg, status: %s%n",
                name, breed, type, age, price, weight, status);
    }

    @Override
    public int compareTo(Horse o) {
        int nameCmp = this.name.compareToIgnoreCase(o.name);
        if (nameCmp != 0) return nameCmp;

        int breedCmp = this.breed.compareToIgnoreCase(o.breed);
        if (breedCmp != 0) return breedCmp;

        return Integer.compare(this.age, o.age);
    }

    /* --- GETTERY & SETTER --- */
    public Long getId() {
        return id;
    }

    public Stable getStable() {
        return stable;
    }

    public void setStable(Stable stable) {
        this.stable = stable;
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

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(HorseCondition status) {
        this.status = status;
    }

    public void setType(HorseType type) {
        this.type = type;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
