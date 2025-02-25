package com.example.landfx;

import com.example.landfx.Enteties.AbstractEnteties.Animal;
import com.example.landfx.Enum.Direction;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Random;

import static com.example.landfx.Game.TACT_DURATION;

public class AnimalBehaviorController implements Runnable {
    private Animal animal;
    private List<Animal> animals;
    private GridPane Grid;
    private List<Thread> threads;

    private int x;
    private int y;

    public AnimalBehaviorController(Animal animal, GridPane Grid, List<Animal> animals, List<Thread> threads) {
        this.animal = animal;
        this.Grid = Grid;
        this.animals = animals;
        this.threads = threads;
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

        animal.move(rand.nextInt(0, animal.getMaxMove() + 1));
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
            Thread thread = new Thread(new AnimalBehaviorController(new_animal, this.Grid, this.animals, this.threads));
            threads.add(thread);
        }
    }

    @Override
    public void run() {
        System.out.println(animal.getX() + " " + animal.getY());
        eat();
        reproduction();
        move(animal);
        try {
            Thread.sleep(TACT_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
