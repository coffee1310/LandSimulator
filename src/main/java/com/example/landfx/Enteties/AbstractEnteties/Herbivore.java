package com.example.landfx.Enteties.AbstractEnteties;

import com.example.landfx.Enum.Direction;
import com.example.landfx.Game;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.Map;

public class Herbivore extends Animal {
    public Herbivore(int xPos, int yPos, Image image, int maxMove) {
        super(xPos, yPos, image, maxMove);
    }

    public Herbivore(Herbivore other) {
        super(other);
    }

    @Override
    public Animal eat(Animal animal) {
        if (this.satiety + animal.weight <= this.maxWeightForFullSatiety) this.satiety += animal.weight;
        else this.satiety = this.maxWeightForFullSatiety;
        return this;
    }

    @Override
    public void eat(Plant plant) {
        if (!this.eatPlants) return;
        if (this.satiety + plant.getWeight() <= this.maxWeightForFullSatiety) this.satiety += plant.getWeight();
        else this.satiety = this.maxWeightForFullSatiety;
    }

    @Override
    public void move(int steps) {
        switch (this.direction) {
            case UP:
                if (this.YPosition > 0) this.YPosition -= steps;
                break;
            case DOWN:
                if (this.YPosition < Game.getInstance().getHeight()) this.YPosition += steps;
                break;
            case LEFT:
                if (this.XPosition > 0) this.XPosition -= steps;
                break;
            case RIGHT:
                if (this.XPosition < Game.getInstance().getWidth()) this.XPosition += steps;
                break;
        }

        this.stepBeforeToBeAdult++;
        if (this.stepBeforeToBeAdult >= 10) this.isChild = true;
    }

    @Override
    public Animal reproduction(Animal animal) {
        if (!(animal instanceof Predator)) return null;
        return new Herbivore(this.XPosition, this.YPosition, this.image, this.maxMove);
    }

    @Override
    public Animal kill() {
        if (this.satiety <= 0) {

        }
        return null;
    }

    @Override
    public Animal copy() {return new Herbivore(this);}

    @Override
    public Image getImage() {return this.image;}

    @Override
    public void setCoordinates(int x, int y) {
        this.XPosition = x;
        this.YPosition = y;
    }

    @Override
    public void setDirection(Direction direction) {this.direction = direction;}

    @Override
    public Map<Animal, Integer> getEatAnimalChance() {return this.eatAnimalChances;}

    @Override
    public int getMaxMove() {return this.maxMove;}

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
    public int getX() {return this.XPosition;}

    @Override
    public int getY() {return this.YPosition;}

    @Override
    public boolean getIsChild() {return this.isChild;}
}
