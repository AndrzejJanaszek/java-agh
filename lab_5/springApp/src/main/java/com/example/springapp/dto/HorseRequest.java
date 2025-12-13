package com.example.springapp.dto;

import com.example.springapp.model.HorseCondition;
import com.example.springapp.model.HorseType;

public class HorseRequest {

    private String name;
    private String breed;
    private HorseType type;
    private HorseCondition status;
    private int age;
    private double price;
    private double weight;
    private Long stableId;

    public HorseRequest() {}

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

    public Long getStableId() {
        return stableId;
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

    public void setStableId(Long stableId) {
        this.stableId = stableId;
    }
}
