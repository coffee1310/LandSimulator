package com.example.landfx.Enteties.AbstractEnteties;

import com.example.landfx.Enum.Direction;
import com.example.landfx.Game;
import javafx.scene.image.Image;

public class Predator extends Animal {
    public Predator(int xPos, int yPos, Image image) {
        this.XPosition = xPos;
        this.YPosition = yPos;
        this.eatPlants = false;
        this.image = image;
    }

    @Override
    public void eat(Animal animal) {
        if (this.satiety + animal.weight <= this.maxWeightForFullSatiety) this.satiety += animal.weight;
        else this.satiety = this.maxWeightForFullSatiety;
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

        return animal;
    }

    @Override
    public Animal kill() {
        if (this.satiety <= 0) {

        }
        return null;
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
}
