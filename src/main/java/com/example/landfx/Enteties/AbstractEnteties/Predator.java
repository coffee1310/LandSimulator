package com.example.landfx.Enteties.AbstractEnteties;

import com.example.landfx.Enum.Direction;
import com.example.landfx.Game;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.Map;

public class Predator extends Animal {
    public Predator(int xPos, int yPos, Image image, int maxMove) {
        super(xPos, yPos, image, maxMove);
        this.eatPlants = false;
    }

    public Predator(Predator other) {
        super(other);
    }

    @Override
    public Animal eat(Animal animal) {
        if (this.satiety + animal.weight <= this.maxWeightForFullSatiety) this.satiety += animal.weight;
        else this.satiety = this.maxWeightForFullSatiety;

        return this;
    }

    @Override
    public void eat(Plant plant) {}

    @Override
    public void move(int steps) {
        switch (this.direction) {
            case UP:
                this.YPosition = Math.max(0, this.YPosition - steps);
                break;
            case DOWN:
                this.YPosition = Math.min(Game.getInstance().getHeight() - 1, this.YPosition + steps);
                break;
            case LEFT:
                this.XPosition = Math.max(0, this.XPosition - steps);
                break;
            case RIGHT:
                this.XPosition = Math.min(Game.getInstance().getWidth() - 1, this.XPosition + steps);
                break;
        }

        this.stepBeforeToBeAdult++;
        if (this.stepBeforeToBeAdult >= 10) this.isChild = true;
    }

    @Override
    public Animal reproduction(Animal animal) {
        if (!(animal instanceof Predator)) return null;

        return animal;
    }

    @Override
    public Animal kill() {
        if (this.satiety <= 0) {

        }
        return null;
    }

    @Override
    public Animal copy() {
        return new Predator(this);
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.XPosition = x;
        this.YPosition = y;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Map<Animal, Integer> getEatAnimalChance() {
        return this.eatAnimalChances;
    }

    @Override
    public int getMaxMove() {
        return this.maxMove;
    }

    @Override
    public int addX(int value) {
        this.XPosition += value;
        return this.XPosition;
    }

    @Override
    public int addY(int value) {
        this.YPosition += value;
        return YPosition;
    }

    @Override
    public int getX() {
        return this.XPosition;
    }

    @Override
    public int getY() {
        return this.YPosition;
    }

    @Override
    public boolean getIsChild() {return this.isChild;}
}
