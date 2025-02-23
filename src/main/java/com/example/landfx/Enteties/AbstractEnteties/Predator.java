package com.example.landfx.Enteties.AbstractEnteties;

import com.example.landfx.Enum.Direction;
import com.example.landfx.Game;

public class Predator extends Animal {
    public Predator(int xPos, int yPos) {
        this.XPosition = xPos;
        this.YPosition = yPos;
        this.eatPlants = false;
    }

    @Override
    public void eat(Animal animal) {
        if (this.satiety + animal.weight <= this.maxWeightForFullSatiety) {
            this.satiety += animal.weight;
        }
        else {
            this.satiety = this.maxWeightForFullSatiety;
        }
    }

    @Override
    public void eat(Plant plant) {}

    @Override
    public void changeMove(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void move() {
        switch (this.direction) {
            case UP:
                if (this.YPosition > 0) {
                    this.YPosition -= 1;
                }
                break;
            case DOWN:
                if (this.YPosition < Game.getInstance().getHeight()) {
                    this.YPosition += 1;
                }
                break;
            case LEFT:
                if (this.XPosition > 0) {
                    this.XPosition -= 1;
                }
                break;
            case RIGHT:
                if (this.XPosition < Game.getInstance().getWidth()) {
                    this.XPosition += 1;
                }
                break;
        }
    }

    @Override
    public Animal reproduction(Animal animal) {
        if (!(animal instanceof Predator)) {
            return null;
        }

        return animal;
    }

    @Override
    public Animal kill() {
        if (this.satiety <= 0) {

        }
        return null;
    }
}
