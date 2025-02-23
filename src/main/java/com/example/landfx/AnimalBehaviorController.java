package com.example.landfx;

import com.example.landfx.Enteties.AbstractEnteties.Animal;
import com.example.landfx.Enum.Direction;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Random;

public class AnimalBehaviorController implements Runnable {
    private Animal animal;
    private List<Animal> animals;
    private GridPane Grid;
    private int x;
    private int y;

    public AnimalBehaviorController(Animal animal, GridPane Grid) {
        this.animal = animal;
        this.Grid = Grid;
    }

    private void eat() {
        for (var another_animal : animals) {
            if (another_animal == animal) continue;
            if (another_animal.getX() != x || another_animal.getY() != y) continue;
            if (another_animal.getClass() == this.animal.getClass()) continue;
            if (!animal.getEatAnimalChance().containsKey(another_animal)) continue;

            Random rand = new Random();
            int chance = animal.getEatAnimalChance().get(another_animal);
            animal = rand.nextInt(1, chance + 1) < chance + 1? animal.eat(another_animal) : animal;
        }
    }

    private void move(Animal animal) {
        Random rand = new Random();
        switch (rand.nextInt(0, 4)) {
            case 0:
                animal.setDirection(Direction.UP);
                break;
            case 1:
                animal.setDirection(Direction.DOWN);
                break;
            case 2:
                animal.setDirection(Direction.LEFT);
                break;
            case 3:
                animal.setDirection(Direction.RIGHT);
                break;
            default:
                break;
        }

        move(animal);
    }

    private void reproduction() {
        for (var another_animal : animals) {
            if (another_animal == animal) continue;
            if (another_animal.getX() != x || another_animal.getY() != y) continue;
            if (another_animal.getClass() != this.animal.getClass()) continue;
            if (!animal.getEatAnimalChance().containsKey(another_animal)) continue;

            Animal new_animal = animal.reproduction(another_animal);
            animals.add(new_animal);
            move(new_animal);
        }
    }

    @Override
    public void run() {
        animal.move();
        eat();
        reproduction();
    }
}
