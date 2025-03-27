package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Predator;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Eagle extends Predator {
    public Eagle(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 3, 0, 1, new HashMap<>(), "Eagle", 6, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);
    }
}
