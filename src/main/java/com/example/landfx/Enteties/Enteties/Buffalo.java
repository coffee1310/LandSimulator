package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Herbivore;
import com.example.landfx.Enteties.AbstractEnteties.Predator;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Buffalo extends Herbivore {
    public Buffalo(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 3, 50, 100, new HashMap<>(), "Buffalo", 700, false);
        initializeEatAnimalsChance();
    }

    private void initializeEatAnimalsChance() {
        //this.eatAnimalChances.put("Rabbit", 60);
    }
}
