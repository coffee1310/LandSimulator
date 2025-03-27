package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Herbivore;
import com.example.landfx.Enteties.AbstractEnteties.Predator;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Deer extends Herbivore {
    public Deer(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 4, 30, 50, new HashMap<>(), "Deer", 300, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);
    }
}
