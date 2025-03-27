package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Herbivore;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Boar extends Herbivore {
    public Boar(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 2, 30, 50, new HashMap<>(), "Boar", 400, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);
        this.eatAnimalChances.put("Mouse", 50);
        this.eatAnimalChances.put("Caterpillar", 90);
    }
}
