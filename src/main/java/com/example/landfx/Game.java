package com.example.landfx;

import com.example.landfx.Enteties.AbstractEnteties.Animal;
import com.example.landfx.Enteties.Enteties.Rabbit;
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
import java.util.*;

public class Game {
    private static final int WIDTH = 20; // Ширина острова (в клетках)
    private static final int HEIGHT = 20; // Длина острова (в клетках)
    public static final int TACT_DURATION= 1000; // Длина такта в мс
    private static final int MIN_ANIMAL_COUNT = 3; // Минимальное количество животных при генерации
    private static final int MAX_ANIMAL_COUNT = 10; // Максимальное количество животных при генерации

    private static Image GROUND_IMAGE;

    static {
        try {
            GROUND_IMAGE = new Image(
                    new FileInputStream(HelloApplication.class.getResource("image/ground.jpg").getPath())
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Thread AnimalThread;
    private Thread GridThread;
    private List<Thread> AnimalThreads = new LinkedList<>();

    private List<Animal> animal_types;
    private Map<Animal, ImageView> animalViews = new HashMap<>();

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
            columnConstraints.setMinWidth(48);
            columnConstraints.setMaxWidth(48);
            columnConstraints.setPrefWidth(48);
            this.Grid.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < HEIGHT; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(48);
            rowConstraints.setMinHeight(48);
            rowConstraints.setMaxHeight(48);
            this.Grid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                this.Grid.add(new ImageView(GROUND_IMAGE), i, j);
            }
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
        animal_types.add(
                new Rabbit(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/rabbit.png").getPath())
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
                Thread animalBehaviorThread = new Thread(new AnimalBehaviorController(animal, this.Grid, this.animals, this.AnimalThreads, this.animalViews));
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
                        List<Animal> newAnimals = new ArrayList<>(animals);

                        animalViews.keySet().retainAll(newAnimals);

                        for (var animal : newAnimals) {
                            if (!animal.isAlive()) {
                                animals.remove(animal);
                                continue;
                            }
                            ImageView imageView = animalViews.get(animal);
                            if (imageView == null) {
                                imageView = new ImageView(animal.getImage());
                                animalViews.put(animal, imageView);
                                this.Grid.add(imageView, animal.getX(), animal.getY());
                            } else {
                                if (GridPane.getColumnIndex(imageView) != animal.getX() ||
                                        GridPane.getRowIndex(imageView) != animal.getY()) {
                                    this.Grid.getChildren().remove(imageView);
                                    this.Grid.add(imageView, animal.getX(), animal.getY());
                                }
                            }
                            System.out.println(animal);
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
                    List<Thread> animalThreads = new ArrayList<>(AnimalThreads);
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
