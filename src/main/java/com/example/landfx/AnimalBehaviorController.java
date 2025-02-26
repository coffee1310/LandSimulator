package com.example.landfx;

import com.example.landfx.Enteties.AbstractEnteties.Animal;
import com.example.landfx.Enum.Direction;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    private synchronized void addAnimal(Animal animal) {
        animals.add(animal);
        Thread animalBehaviorThread = new Thread(new AnimalBehaviorController(animal, this.Grid, this.animals, this.threads));
        this.threads.add(animalBehaviorThread);
        animalBehaviorThread.start();
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
        synchronized (animal) {
            Random rand = ThreadLocalRandom.current();

            switch (rand.nextInt(4)) {
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
            int direction = rand.nextInt(4);
            System.out.println("Direction: " + direction);
            int steps = rand.nextInt(animal.getMaxMove() + 1);
            animal.move(steps);
        }
    }

    private void reproduction() {
        for (var another_animal : animals) {
            if (another_animal == animal) continue;
            if (another_animal.getX() != animal.getX() || another_animal.getY() != animal.getY()) continue;
            if (another_animal.getClass() != this.animal.getClass()) continue;
            if (animal.getEatAnimalChance().containsKey(another_animal)) continue;

            addAnimal(another_animal);
            break;
        }
    }

    @Override
    public void run() {
        eat();
        //reproduction();
        move(animal);
    }
}
