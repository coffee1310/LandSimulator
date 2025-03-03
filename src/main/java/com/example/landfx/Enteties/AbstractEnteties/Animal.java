package com.example.landfx.Enteties.AbstractEnteties;

import com.example.landfx.Enum.Direction;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public abstract class Animal {
    public Animal(Animal other) {
        this.id = Animal.max_id++;
        this.XPosition = other.XPosition;
        this.YPosition = other.YPosition;
        this.image = other.image;
        this.maxMove = other.maxMove;
        this.eatAnimalChances = other.eatAnimalChances;
        this.satiety = other.satiety;
        this.maxWeightForFullSatiety = other.maxWeightForFullSatiety;
        this.name = other.name;
        this.weight = other.weight;
    }

    public Animal(int XPosition,
                  int YPosition,
                  Image image,
                  int maxMove,
                  float satiety,
                  float maxSatiety,
                  Map<String, Integer> eatAnimalChances,
                  String name,
                  float weight) {
        this.id = Animal.max_id++;
        this.XPosition = XPosition;
        this.YPosition = YPosition;
        this.image = image;
        this.maxMove = maxMove;
        this.satiety = satiety;
        this.maxWeightForFullSatiety = maxSatiety;
        this.eatAnimalChances = eatAnimalChances;
        this.name = name;
        this.weight = weight;
    }

    protected static int max_id = 0;

    protected int id = 0;

    protected String name;

    public abstract Animal eat(Animal animal);

    public abstract void eat(Plant plant);

    public abstract void move(int steps);

    public abstract Animal reproduction(Animal animal);

    public abstract Animal kill();

    public abstract Animal copy();

    public abstract Image getImage();

    public abstract void setCoordinates(int x, int y);

    public abstract void setDirection(Direction direction);

    public abstract Map<String, Integer> getEatAnimalChance();

    public abstract int getMaxMove();

    public abstract int addX(int value);

    public abstract int addY(int value);

    public abstract int getX();

    public abstract int getY();

    public abstract boolean getIsChild();

    public abstract void setIsChild(boolean value);

    public abstract float getSatiety();

    public abstract void setSatiety(float value);

    public abstract float getMaxSatiety();

    public abstract String getTitle();

    public abstract float getWeight();

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

    protected boolean isChild = true;

    protected boolean isAlive = true;

    protected int stepBeforeToBeAdult = 0;

    protected Map<String, Integer> eatAnimalChances;

    @Override
    public String toString() {
        return this.id+ " (" + this.getX() + ", " + this.getY() + ")";
    }


    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}