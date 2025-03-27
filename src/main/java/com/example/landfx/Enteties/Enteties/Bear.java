package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Predator;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Bear extends Predator {
    public Bear(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 2, 40, 60, new HashMap<>(), "Bear", 500, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);
        this.eatAnimalChances.put("Boa", 80);
        this.eatAnimalChances.put("Horse", 40);
        this.eatAnimalChances.put("Deer", 80);
        this.eatAnimalChances.put("Rabbit", 80);
        this.eatAnimalChances.put("Mouse", 90);
        this.eatAnimalChances.put("Goat", 70);
        this.eatAnimalChances.put("Sheep", 70);
        this.eatAnimalChances.put("Boar", 50);
        this.eatAnimalChances.put("Buffalo", 20);
        this.eatAnimalChances.put("Duck", 10);
    }
}
