package com.example.landfx.Enteties.AbstractEnteties;

import com.example.landfx.Enum.Direction;
import javafx.scene.image.Image;

import java.util.HashMap;

public abstract class Animal {
    public abstract void eat(Animal animal);

    public abstract void eat(Plant plant);

    public abstract void changeMove(Direction direction);

    public abstract void move();

    public abstract Animal reproduction(Animal animal);

    public abstract Animal kill();

    public abstract Image getImage();

    public abstract void setCoordinates(int x, int y);

    public abstract int addX(int value);

    public abstract int addY(int value);

    public abstract int getX();

    public abstract int getY();

    protected Image image;

    protected int XPosition;

    protected int YPosition;

    protected Direction direction;

    protected int maxMove;

    protected int maxCountInPlace;

    protected float maxWeightForFullSatiety;

    protected float satiety;

    protected float weight;

    protected boolean eatPlants;

    protected HashMap<Animal, Float> eatAnimalChances;

}