package com.example.landfx.Enteties.AbstractEnteties;

import com.example.landfx.Enum.Direction;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public abstract class Animal {
    public Animal(Animal other) {
        this.XPosition = other.XPosition;
        this.YPosition = other.YPosition;
        this.image = other.image;
        this.maxMove = other.maxMove;
        this.eatAnimalChances = other.eatAnimalChances;
    }

    public Animal(int XPosition, int YPosition, Image image, int maxMove) {
        this.XPosition = XPosition;
        this.YPosition = YPosition;
        this.image = image;
        this.maxMove = maxMove;
        this.eatAnimalChances = new HashMap<>();
    }

    public abstract Animal eat(Animal animal);

    public abstract void eat(Plant plant);

    public abstract void move(int steps);

    public abstract Animal reproduction(Animal animal);

    public abstract Animal kill();

    public abstract Animal copy();

    public abstract Image getImage();

    public abstract void setCoordinates(int x, int y);

    public abstract void setDirection(Direction direction);

    public abstract Map<Animal, Integer> getEatAnimalChance();

    public abstract int getMaxMove();

    public abstract int addX(int value);

    public abstract int addY(int value);

    public abstract int getX();

    public abstract int getY();

    public abstract boolean getIsChild();

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

    protected boolean isChild = false;

    protected int stepBeforeToBeAdult = 0;

    protected Map<Animal, Integer> eatAnimalChances;

}