package com.example.landfx;

import com.example.landfx.Enteties.AbstractEnteties.Animal;
import com.example.landfx.Enteties.AbstractEnteties.Plant;
import com.example.landfx.Enteties.Enteties.*;
import com.example.landfx.Enum.CellType;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private static final int WIDTH = 20; // Ширина острова (в клетках)
    private static final int HEIGHT = 20; // Длина острова (в клетках)
    public static final int TACT_DURATION= 1000; // Длина такта в мс
    private static final int MIN_ANIMAL_COUNT = 3; // Минимальное количество животных при генерации
    private static final int MAX_ANIMAL_COUNT = 10; // Максимальное количество животных при генерации
    private static final int PLANT_GROWTH_INTERVAL = 5; // Время роста растений

    private static Image GROUND_IMAGE;
    private static Image PLANT_IMAGE;
    private static ArrayList<ArrayList<CellType>> areaTypes;
    private ProcedureGeneration procedureGeneration;

    static {
        try {
            GROUND_IMAGE = new Image(
                    new FileInputStream(HelloApplication.class.getResource("image/ground.jpg").getPath())
            );
            PLANT_IMAGE = new Image(new FileInputStream(HelloApplication.class.getResource("image/plant.png").getPath()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Thread AnimalThread;
    private Thread GridThread;
    private LinkedHashMap<Animal, Thread> AnimalThreads = new LinkedHashMap<>();

    private List<Animal> animal_types;
    private Map<Animal, ImageView> animalViews = new HashMap<>();

    private GridPane Grid;
    private List<Animal> animals;
    private CopyOnWriteArrayList<Plant> plants = new CopyOnWriteArrayList<>();

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
        initPlants(this.Grid);
        updateTick();
//        procedureGeneration = ProcedureGeneration.getInstance();
//        areaTypes = procedureGeneration.getAreaTypes();
    }

    public void initPlants(GridPane gridPane) {
        Random rand = new Random();
        int plantCount = rand.nextInt(10, 20);

        for (int i = 0; i < plantCount; i++) {
            addRandomPlant(gridPane);
        }
    }

    private void addRandomPlant(GridPane gridPane) {
        if (plants.size() >= Plant.getMaxCountInPlace()) return;

        Random rand = new Random();
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HEIGHT);

        ImageView plantView = new ImageView(PLANT_IMAGE);
        Plant plant = new Plant(x, y, plantView);
        plants.add(plant);
        Platform.runLater(() -> {
            gridPane.add(plantView, x, y);
        });
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
        animal_types.add(
                new Bear(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/bear.png").getPath())
                        )
                )
        );
        animal_types.add(
                new Boa(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/boa.png").getPath())
                        )
                )
        );

        animal_types.add(
                new Boar(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/boar.png").getPath())
                        )
                )
        );

        animal_types.add(
                new Buffalo(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/Buffalo.png").getPath())
                        )
                )
        );

        animal_types.add(
                new Caterpillar(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/caterpillar.png").getPath())
                        )
                )
        );

        animal_types.add(
                new Deer(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/deer.png").getPath())
                        )
                )
        );

        animal_types.add(
                new Duck(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/duck.png").getPath())
                        )
                )
        );

        animal_types.add(
                new Eagle(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/eagle.png").getPath())
                        )
                )
        );

        animal_types.add(
                new Fox(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/fox.png").getPath())
                        )
                )
        );

        animal_types.add(
                new Goat(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/goat.png").getPath())
                        )
                )
        );

        animal_types.add(
                new Horse(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/horse.png").getPath())
                        )
                )
        );

        animal_types.add(
                new Mouse(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/mouse.png").getPath())
                        )
                )
        );

        animal_types.add(
                new Sheep(
                        -1,
                        -1,
                        new Image(
                                new FileInputStream(HelloApplication.class.getResource("image/sheep.png").getPath())
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
                Thread animalBehaviorThread = new Thread(new AnimalBehaviorController(animal, this.Grid, this.animals, this.AnimalThreads, this.animalViews, this.plants));
                this.AnimalThreads.put(animal, animalBehaviorThread);
            }
        });

        AnimalThread.start();
        AnimalThread.interrupt();
    }

    private void updateTick() {
        GridThread = new Thread(() -> {
            AtomicInteger tickCount = new AtomicInteger();
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);

                    Platform.runLater(() -> {
                        List<Animal> newAnimals = new ArrayList<>(animals);

                        animalViews.keySet().retainAll(newAnimals);

                        for (var animal : newAnimals) {
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
                        }

                        if (tickCount.incrementAndGet() % PLANT_GROWTH_INTERVAL == 0) {
                            int newPlants = new Random().nextInt(1, 4);
                            for (int i = 0; i < newPlants; i++) {
                                addRandomPlant(this.Grid);
                            }
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
                    Map<Animal, Thread> animalThreads = new LinkedHashMap<>(AnimalThreads);
                    for (Map.Entry<Animal, Thread> th: animalThreads.entrySet()) {
                        th.getValue().run();
                        if (!th.getKey().isAlive()) {
                            th.getValue().interrupt();
                            AnimalThreads.remove(th.getKey());
                        }
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
