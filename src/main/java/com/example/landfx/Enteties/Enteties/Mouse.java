package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Herbivore;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Mouse extends Herbivore {
    public Mouse(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 1, 0, 0.01F, new HashMap<>(), "Mouse", 0.05F, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);
        this.eatAnimalChances.put("Caterpillar", 90);
    }
}
