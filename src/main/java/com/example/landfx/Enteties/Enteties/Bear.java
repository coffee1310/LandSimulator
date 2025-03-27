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
    }
}
