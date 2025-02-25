package com.example.landfx;

import com.example.landfx.Enteties.AbstractEnteties.Animal;
import com.example.landfx.Enteties.Enteties.Wolf;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {
    private static final int WIDTH = 25; // Ширина острова (в клетках)
    private static final int HEIGHT = 25; // Длина острова (в клетках)
    public static final int TACT_DURATION= 1000; // Длина такта в мс
    private static final int MIN_ANIMAL_COUNT = 3; // Минимальное количество животных при генерации
    private static final int MAX_ANIMAL_COUNT = 10; // Максимальное количество животных при генерации
    
    private Thread AnimalThread;
    private Thread GridThread;
    private List<Thread> AnimalThreads = new LinkedList<>();

    private List<Animal> animal_types;

    private GridPane Grid;
    private List<Animal> animals;

    private static Game intstance;

    private Game() {}

    public static Game getInstance() {
        if (intstance == null) {
            intstance = new Game();
        }
        return intstance;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void setGrid(GridPane Grid) throws FileNotFoundException {
        this.Grid = Grid;
        for (int i = 0; i < WIDTH; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(100);
            this.Grid.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < HEIGHT; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(100);
            this.Grid.getRowConstraints().add(rowConstraints);
        }

        initAnimals();
        updateTick();
    }

    private void initAnimals() throws FileNotFoundException {
        animals = new ArrayList<>();
        animal_types = new ArrayList<>();

        animal_types.add(
            new Wolf(
                -1,
                -1,
                new Image(
                        new FileInputStream(HelloApplication.class.getResource("image/wolf.png").getPath())
                )
            )
        );

        int animal_types_count;
        for (var animal_type : animal_types) {
            Animal animal = animal_type;
            Random rand = new Random();
            animal_types_count = rand.nextInt(MIN_ANIMAL_COUNT, MAX_ANIMAL_COUNT);

            for (int i = 0; i < animal_types_count; i++) {
                Animal newAnimal = animal.copy();
                animal.setCoordinates(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
                newAnimal.setCoordinates(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
                animals.add(newAnimal);
            }
        }

        AnimalThread = new Thread(() -> {
            for (var animal: animals) {
                Thread animalBehaviorThread = new Thread(new AnimalBehaviorController(animal, this.Grid, this.animals, this.AnimalThreads));
                this.AnimalThreads.add(animalBehaviorThread);
            }
        });
        AnimalThread.start();
        AnimalThread.interrupt();
    }

    private void updateTick() {
        GridThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);

                    Platform.runLater(() -> {
                        this.Grid.getChildren().clear();
                        List<Animal> newAnimals = animals;
                        for (var animal : newAnimals) {
                            int x = animal.getX();
                            int y = animal.getY();
                            this.Grid.add(new ImageView(animal.getImage()), x, y);
                        }
                        this.Grid.setGridLinesVisible(true);
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        AnimalThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    List<Thread> animalThreads = AnimalThreads;
                    for (var th : animalThreads) {
                        th.run();
                    }

                    Thread.sleep(TACT_DURATION);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        GridThread.start();
        AnimalThread.start();
    }

    public GridPane getGrid() {
        return this.Grid;
    }
}
