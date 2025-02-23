package com.example.landfx.Enteties.AbstractEnteties;

import com.example.landfx.Enum.Direction;
import java.util.HashMap;

abstract class Animal {
    public abstract void eat(Animal animal);

    public abstract void eat(Plant plant);

    public abstract void changeMove(Direction direction);

    public abstract void move();

    public abstract Animal reproduction(Animal animal);

    public abstract Animal kill();

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