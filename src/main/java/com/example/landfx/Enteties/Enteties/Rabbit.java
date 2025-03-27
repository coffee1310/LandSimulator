package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Animal;
import com.example.landfx.Enteties.AbstractEnteties.Herbivore;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Rabbit extends Herbivore {
    Map<Animal, Integer> eatAnimalsChance = new HashMap<>();
    public Rabbit(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 2, 0.45f, 0.45f, new HashMap<>(), "Rabbit", 2, false);
    }
}
