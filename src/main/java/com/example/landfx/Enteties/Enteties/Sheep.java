package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Herbivore;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Sheep extends Herbivore {
    public Sheep(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 3, 8, 15, new HashMap<>(), "Sheep", 70, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);

    }
}
