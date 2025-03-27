package com.example.landfx.Enteties.AbstractEnteties;

import com.example.landfx.Enum.CellType;
import com.example.landfx.Enum.Direction;
import com.example.landfx.Game;
import com.example.landfx.ProcedureGeneration;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.Map;

public class Herbivore extends Animal {
    public Herbivore(int xPos,
                     int yPos,
                     Image image,
                     int maxMove,
                     float satiety,
                     float maxSatiety,
                     Map<String, Integer> eatAnimalChances,
                     String name,
                     float weight,
                     boolean isSwimming) {
        super(xPos, yPos, image, maxMove, satiety, maxSatiety, eatAnimalChances, name, weight, isSwimming);
    }

    public Herbivore(Herbivore other) {
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
    public void eat(Plant plant) {
        if (!this.eatPlants) return;
        if (this.satiety + plant.getWeight() <= this.maxWeightForFullSatiety) this.satiety += plant.getWeight();
        else this.satiety = this.maxWeightForFullSatiety;
    }

    @Override
    public void move(int steps) {
        int newX = this.XPosition;
        int newY = this.YPosition;

        switch (this.direction) {
            case UP:
                newY = this.YPosition - steps;
                break;
            case DOWN:
                newY = this.YPosition + steps;
                break;
            case LEFT:
                newX = this.XPosition - steps;
                break;
            case RIGHT:
                newX = this.XPosition + steps;
                break;
        }

        int deltaX = Integer.compare(newX, this.XPosition);
        int deltaY = Integer.compare(newY, this.YPosition);

        int currentX = this.XPosition;
        int currentY = this.YPosition;

        while (currentX != newX || currentY != newY) {
            int nextX = currentX + deltaX;
            int nextY = currentY + deltaY;

            if (!canMoveTo(nextX, nextY)) {
                break;
            }

            currentX = nextX;
            currentY = nextY;
        }

        this.XPosition = currentX;
        this.YPosition = currentY;

        this.stepBeforeToBeAdult++;
        if (this.stepBeforeToBeAdult >= 10) this.isChild = false;
    }

    private synchronized boolean canMoveTo(int x, int y) {
        if (x < 0 || x >= Game.getInstance().getWidth() || y < 0 || y >= Game.getInstance().getHeight()) {
            return false;
        }

        CellType cellType = ProcedureGeneration.getInstance().getCellType(x, y);
        System.out.println("Cell type at (" + x + ", " + y + "): " + cellType);

        if (cellType.equals(CellType.WATER) && !this.isSwimming) {
            return false;
        }

        return true;
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
    public Map<String, Integer> getEatAnimalChance() {return this.eatAnimalChances;}

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

    @Override
    public void setIsChild(boolean value) {
        this.isChild = value;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.isAlive = alive;
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
}
