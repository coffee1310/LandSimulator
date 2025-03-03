package com.example.landfx.Enteties.Enteties;

import com.example.landfx.Enteties.AbstractEnteties.Predator;
import javafx.scene.image.Image;

public class Wolf extends Predator {
    public Wolf(int xPos, int yPos, Image image) {
        super(xPos, yPos, image, 3, 10, 10);
    }
}
