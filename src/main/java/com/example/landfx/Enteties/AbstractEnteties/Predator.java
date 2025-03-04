package com.example.landfx.Enteties.AbstractEnteties;

import com.example.landfx.Enum.Direction;
import com.example.landfx.Game;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.Map;

public class Predator extends Animal {
    public Predator(int xPos,
                    int yPos,
                    Image image,
                    int maxMove,
                    int satiety,
                    int maxSatiety,
                    Map<String, Integer> eatAnimalChances,
                    String name,
                    float weight) {
        super(xPos, yPos, image, maxMove, satiety, maxSatiety, eatAnimalChances, name, weight);
        this.eatPlants = false;
    }

    public Predator(Predator other) {
        super(other);
    }

    @Override
    public Animal eat(Animal animal) {
        if (this == animal) return null;
        if (this.XPosition != animal.getX() || this.YPosition != animal.getY()) return null;
        if (animal.getTitle().equals(this.name)) return null;
        if (!this.eatAnimalChances.containsKey(animal.getTitle())) return null;

        if (this.satiety + animal.getWeight() <= this.maxWeightForFullSatiety) this.satiety += animal.getWeight();
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
        if (this.stepBeforeToBeAdult >= 10) this.isChild = false;
    }

    @Override
    public Animal reproduction(Animal another_animal) {
        if (!another_animal.getTitle().equals(this.name)) return null;
        if (another_animal.getIsChild()) return null;
        if (another_animal == this) return null;
        if (another_animal.getX() != this.getX() || another_animal.getY() != this.getY()) return null;
        if (another_animal.getClass() != this.getClass()) return null;
        if (this.getEatAnimalChance().containsKey(another_animal)) return null;
        if (this.getSatiety() != this.getMaxSatiety()
                || another_animal.getSatiety() != another_animal.getMaxSatiety()) return null;
        this.setSatiety(0);
        another_animal.setSatiety(0);
        return another_animal;
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
    public Map<String, Integer> getEatAnimalChance() {
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

    @Override
    public void setIsChild(boolean value) {
        this.isChild = value;
    }

    @Override
    public float getSatiety() {
        return this.satiety;
    }

    @Override
    public void setSatiety(float value) {
        this.satiety = value;
    }

    @Override
    public float getMaxSatiety() {
        return this.maxWeightForFullSatiety;
    }

    @Override
    public String getTitle() {
        return this.name;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }

    @Override
    public synchronized boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public synchronized void setAlive(boolean alive) {
        this.isAlive = alive;
    }
}
