package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Herbivore;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Duck extends Herbivore {
    public Duck(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 4, 0.1F, 0.15F, new HashMap<>(), "Duck", 1, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);
    }
}
