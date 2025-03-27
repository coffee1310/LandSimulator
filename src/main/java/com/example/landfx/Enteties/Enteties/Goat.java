package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Herbivore;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Goat extends Herbivore {
    public Goat(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 3, 5, 10, new HashMap<>(), "Goat", 60, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);
    }
}
