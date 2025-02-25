package com.example.landfx;

import com.example.landfx.Enteties.AbstractEnteties.Animal;
import com.example.landfx.Enteties.Enteties.Wolf;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
                animal.setCoordinates(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
                animals.add(animal);
            }
        }

        AnimalThread = new Thread(() -> {
            for (var animal: animal_types) {
                Thread animalBehaviorThread = new Thread(new AnimalBehaviorController(animal, this.Grid, this.animal_types, this.AnimalThreads));
                this.AnimalThreads.add(animalBehaviorThread);
            }
        });
        AnimalThread.start();
        AnimalThread.interrupt();
    }

    private void updateTick() {
        AnimalThread = new Thread(() -> {
            for (var th: AnimalThreads) {
                th.start();
            }
            while (true) {
                for (var th: AnimalThreads) {
                    th.run();
                }
            }
        });
        
        GridThread = new Thread(() -> {
           for (var animal: animals) {
               int x = animal.getX();
               int y = animal.getY();

           }
        });

        AnimalThread.start();
    }

    public GridPane getGrid() {
        return this.Grid;
    }
}
