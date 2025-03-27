package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Herbivore;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Caterpillar extends Herbivore {
    public Caterpillar(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 0, 0, 0, new HashMap<>(), "Caterpillar", 0.01F, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);
    }
}
