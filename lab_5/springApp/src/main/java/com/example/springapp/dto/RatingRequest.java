package com.example.springapp.dto;

public class RatingRequest {

    private Long horseId;
    private int value;
    private String description;

    public RatingRequest() {}

    public Long getHorseId() {
        return horseId;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
