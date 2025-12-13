package com.example.springapp.dto;

public class StableRequest {

    private String name;
    private int capacity;

    public StableRequest() {}

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
