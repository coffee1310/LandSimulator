package com.example.landfx;

import com.example.landfx.Enum.CellType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class ProcedureGeneration {
    private static final int WIDTH = 20; // Ширина острова (в клетках)
    private static final int HEIGHT = 20; // Длина острова (в клетках)
    public static final int TACT_DURATION= 1000; // Длина такта в мс
    private static final int MIN_ANIMAL_COUNT = 3; // Минимальное количество животных при генерации
    private static final int MAX_ANIMAL_COUNT = 10; // Максимальное количество животных при генерации
    private static final int MAP_SCALE = WIDTH * HEIGHT / 100;

    private GridPane Grid;
    private ArrayList<ArrayList<Integer>> lake;
    private ArrayList<ArrayList<Integer>> creek;

    public static Image GROUND_IMAGE;
    public static Image WATER_IMAGE;
    private static ArrayList<ArrayList<CellType>> areaTypes = new ArrayList<>();

    static {
        for (int i = 0; i < WIDTH; i++) {
            ArrayList<CellType> row = new ArrayList<>();
            for (int j = 0; j < HEIGHT; j++) {
                row.add(CellType.LAND); // Заполняем каждую ячейку значением по умолчанию
            }
            areaTypes.add(row);
        }
    }

    static {
        try {
            GROUND_IMAGE = new Image(
                    new FileInputStream(HelloApplication.class.getResource("image/ground.jpg").getPath())
            );

            WATER_IMAGE = new Image(
                    new FileInputStream(HelloApplication.class.getResource("image/water.jpg").getPath())
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static ProcedureGeneration intstance;

    private ProcedureGeneration() {}

    public static ProcedureGeneration getInstance() {
        if (intstance == null) {
            intstance = new ProcedureGeneration();
        }
        return intstance;
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

        Random rand = new Random();
        int maxCreek = rand.nextInt(0, 3);
        int maxLake = rand.nextInt(0, 3);

        for (int k = 0; k < maxCreek; k++) {
            ArrayList<ArrayList<Integer>> creek = initCreek();
            int startI = rand.nextInt(0, WIDTH);
            int startJ = rand.nextInt(0, HEIGHT);

            for (int i = startI, x = 0; i < WIDTH && x < creek.get(0).size(); i++, x++) {
                ArrayList<Integer> row = creek.get(x);
                for (int j = startJ, y = 0; j < HEIGHT && y < row.size(); j++, y++) {
                    if (row.get(y) == 1) {
                        this.Grid.add(new ImageView(WATER_IMAGE), j, i);
                        this.areaTypes.get(i).set(j, CellType.WATER); // Обновляем тип клетки
                    }
                }
            }
        }

        for (int k = 0; k < maxLake; k++) {
            ArrayList<ArrayList<Integer>> lake = initLake();
            int startI = rand.nextInt(0, WIDTH);
            int startJ = rand.nextInt(0, HEIGHT);

            for (int i = startI, x = 0; i < WIDTH && x < lake.get(0).size(); i++, x++) {
                ArrayList<Integer> row = lake.get(x);
                for (int j = startJ, y = 0; j < HEIGHT && y < row.size(); j++, y++) {
                    if (row.get(y) == 1) {
                        this.Grid.add(new ImageView(WATER_IMAGE), j, i);
                        this.areaTypes.get(i).set(j, CellType.WATER); // Обновляем тип клетки
                    }
                }
            }
        }
        printAreaTypes();
        InitLandObjects();
    }

    private ArrayList<ArrayList<Integer>> initLake() {
        Random rand = new Random();
        ArrayList<ArrayList<Integer>> lake = new ArrayList<>();

        int size = (int)(MAP_SCALE * 1.5);
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            lake.add(row);
        }

        double centerX = (size - 1) / 2.0;
        double centerY = (size - 1) / 2.0;

        double radius = Math.min(centerX, centerY);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double distance = Math.sqrt(Math.pow(j - centerX, 2) + Math.pow(i - centerY, 2));
                if (distance <= radius) {
                    lake.get(i).set(j, 1);
                }
            }
        }

        return lake;
    }

    public void printAreaTypes() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                System.out.print(areaTypes.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }


    private ArrayList<ArrayList<Integer>> initCreek() {
        Random rand = new Random();
        ArrayList<ArrayList<Integer>> creekList = new ArrayList<ArrayList<Integer>>(rand.nextInt(0, MAP_SCALE * 3));

        for (int i = 0; i < MAP_SCALE * 2; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < MAP_SCALE * 2; j++) {
                list.add(0);
            }
            creekList.add(list);
        }

        int rows = creekList.size();
        int cols = creekList.get(0).size();

        Random random = new Random();

        int startX = cols / 2;
        int startY = 0;

        int currentX = startX;
        int currentY = startY;

        while (currentY < rows) {
            creekList.get(currentY).set(currentX, 1);

            int direction = random.nextInt(3) - 1;
            currentX += direction;

            if (currentX < 0) {
                currentX = 0;
            } else if (currentX >= cols) {
                currentX = cols - 1;
            }

            currentY++;
        }

        return creekList;
    }

    private void InitLandObjects() {
        initLake();
        initCreek();
    }

    public GridPane getGrid() {
        return this.Grid;
    }

    public ArrayList<ArrayList<CellType>> getAreaTypes() {
        return this.areaTypes;
    }

    public CellType getCellType(int x, int y) {
        // Проверяем, что координаты находятся в пределах карты
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return CellType.LAND; // или выбросьте исключение
        }

        // Возвращаем тип клетки
        return areaTypes.get(x).get(y);
    }
}
