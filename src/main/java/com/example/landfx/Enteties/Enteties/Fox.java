package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Predator;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Fox extends Predator {
    public Fox(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 2, 1, 2, new HashMap<>(), "Fox", 8, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);
    }
}
