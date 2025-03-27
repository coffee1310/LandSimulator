package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Predator;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Boa extends Predator {
    public Boa(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 1, 2, 3, new HashMap<>(), "Boa", 15, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);
    }
}
