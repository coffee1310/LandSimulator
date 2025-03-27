package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Animal;
import com.example.landfx.Enteties.AbstractEnteties.Predator;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Wolf extends Predator {
    public Wolf(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 3, 8, 8, new HashMap<>(), "Wolf", 50, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        this.eatAnimalChances.put("Rabbit", 60);
        this.eatAnimalChances.put("Horse", 10);
        this.eatAnimalChances.put("Deer", 15);
        this.eatAnimalChances.put("Mouse", 80);
        this.eatAnimalChances.put("Goat", 60);
        this.eatAnimalChances.put("Duck", 40);
    }
}
