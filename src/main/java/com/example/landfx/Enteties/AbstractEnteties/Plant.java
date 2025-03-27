package com.example.landfx.Enteties.AbstractEnteties;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plant {
    private int x;
    private int y;
    private int weight;
    private static int maxCountInPlace = 200;
    private ImageView image;

    public Plant(int x, int y, ImageView image) {
        this.x = x;
        this.y = y;
        this.weight = 1;
        this.image = image;
    }

    public int getWeight() {return weight;}
    public static int getMaxCountInPlace() {return maxCountInPlace;}
    public ImageView getImage() {return image;}
    public int getX() {return x;}
    public int getY() {return y;}
}
