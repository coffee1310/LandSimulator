package com.example.landfx.Enteties.AbstractEnteties;

public class Plant {
    private int weight;
    private int maxCountInPlace;

    public Plant() {
        this.weight = 1;
        this.maxCountInPlace = 200;
    }

    public int getWeight() {
        return weight;
    }

    public int getMaxCountInPlace() {
        return maxCountInPlace;
    }
}
