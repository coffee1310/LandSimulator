package com.example.landfx.Enteties.AbstractEnteties;

import com.example.landfx.Enum.Direction;
import com.example.landfx.Game;

public class Herbivore extends Animal {
    public Herbivore(int xPos, int yPos) {
        this.XPosition = xPos;
        this.YPosition = yPos;
    }

    @Override
    public void eat(Animal animal) {
        if (this.satiety + animal.weight <= this.maxWeightForFullSatiety) this.satiety += animal.weight;
        else this.satiety = this.maxWeightForFullSatiety;
    }

    @Override
    public void eat(Plant plant) {
        if (!this.eatPlants) return;
        if (this.satiety + plant.getWeight() <= this.maxWeightForFullSatiety) this.satiety += plant.getWeight();
        else this.satiety = this.maxWeightForFullSatiety;
    }

    @Override
    public void changeMove(Direction direction) {this.direction = direction;}

    @Override
    public void move() {
        switch (this.direction) {
            case UP:
                if (this.YPosition > 0) this.YPosition -= 1;
                break;
            case DOWN:
                if (this.YPosition < Game.getInstance().getHeight()) this.YPosition += 1;
                break;
            case LEFT:
                if (this.XPosition > 0) this.XPosition -= 1;
                break;
            case RIGHT:
                if (this.XPosition < Game.getInstance().getWidth()) this.XPosition += 1;
                break;
        }
    }

    @Override
    public Animal reproduction(Animal animal) {
        if (!(animal instanceof Predator)) return null;
        return new Herbivore(this.XPosition, this.YPosition);
    }

    @Override
    public Animal kill() {
        if (this.satiety <= 0) {

        }
        return null;
    }
}
