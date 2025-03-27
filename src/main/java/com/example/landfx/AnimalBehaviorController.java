package com.example.landfx;

import com.example.landfx.Enteties.AbstractEnteties.Animal;
import com.example.landfx.Enteties.AbstractEnteties.Herbivore;
import com.example.landfx.Enteties.AbstractEnteties.Plant;
import com.example.landfx.Enum.Direction;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.landfx.Game.TACT_DURATION;

public class AnimalBehaviorController implements Runnable {
    private Animal animal;
    private List<Animal> animals;
    private GridPane Grid;
    private LinkedHashMap<Animal, Thread> threads;
    private Map<Animal, ImageView> animalViews;
    private CopyOnWriteArrayList<Plant> plants;

    private int x;
    private int y;

    public AnimalBehaviorController(Animal animal,
                                    GridPane Grid,
                                    List<Animal> animals,
                                    LinkedHashMap<Animal, Thread> threads,
                                    Map<Animal, ImageView> animalViews,
                                    CopyOnWriteArrayList<Plant> plants) {
        this.animal = animal;
        this.Grid = Grid;
        this.animals = animals;
        this.threads = threads;
        this.animalViews = animalViews;
        this.plants = plants;
    }

    private synchronized void addAnimal(Animal animal) {
        animal.setIsChild(true);
        animals.add(animal);
        Thread animalBehaviorThread = new Thread(new AnimalBehaviorController(animal, this.Grid, this.animals, this.threads, this.animalViews, this.plants));
        this.threads.put(animal, animalBehaviorThread);
        animalBehaviorThread.start();

        Platform.runLater(() -> {
            ImageView imageView = new ImageView(animal.getImage());
            animalViews.put(animal, imageView);
            Grid.add(imageView, animal.getX(), animal.getY());
        });
    }

    private synchronized void removeAnimal(Animal animal) {
        synchronized (animals) {
            animals.remove(animal);
        }

        synchronized (animalViews) {
            ImageView imageView = animalViews.remove(animal);
            if (imageView != null) {
                Platform.runLater(() -> {
                    Grid.getChildren().remove(imageView);
                });
            }
        }
    }

    private synchronized void eat() {
        for (var another_animal : animals) {
            if (another_animal == animal) continue;
            if (another_animal.getX() != animal.getX() || another_animal.getY() != animal.getY()) continue;
            if (another_animal.getTitle().equals(this.animal.getTitle())) continue;
            if (!animal.getEatAnimalChance().containsKey(another_animal.getTitle())) continue;

            Random rand = new Random();
            int chance = animal.getEatAnimalChance().get(another_animal.getTitle());
            animal = rand.nextInt(1, chance + 1) < chance + 1? animal.eat(another_animal) : animal;

            if (animal == null) continue;
            another_animal.setAlive(false);
        }

        if (animal.getClass() != Herbivore.class) return;

        CopyOnWriteArrayList<Plant> plantsCopy = new CopyOnWriteArrayList<>(this.plants);
        for (var plant : plantsCopy) {
            if (plant.getX() != animal.getX() || plant.getY() != animal.getY()) continue;

            this.animal.eat(plant);
            plants.remove(plant);

            Platform.runLater(() -> {
                Node nodeToRemove = null;

                for (Node node : Grid.getChildren()) {
                    Integer colIndex = GridPane.getColumnIndex(node);
                    Integer rowIndex = GridPane.getRowIndex(node);

                    if (colIndex != null && rowIndex != null &&
                            colIndex == plant.getX() && rowIndex == plant.getY() &&
                            node instanceof ImageView) {

                        ImageView imageView = (ImageView) node;
                        if (imageView.getImage() == plant.getImage().getImage()) {
                            nodeToRemove = node;
                            break;
                        }
                    }
                }

                if (nodeToRemove != null) {
                    Grid.getChildren().remove(nodeToRemove);
                }
            });
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

            int steps = rand.nextInt(animal.getMaxMove() + 1);
            animal.move(steps);
        }
    }

    private void reproduction() {
        for (var another_animal : animals) {
            if (animal.reproduction(another_animal) == null) continue;
            addAnimal(animal.copy());
            break;
        }
    }

    @Override
    public synchronized void run() {
        if (!animal.isAlive()) {
            removeAnimal(animal);
            synchronized (threads) {
                threads.remove(this);
            }
            return;
        }

        eat();
        reproduction();
        move(animal);
    }
}
