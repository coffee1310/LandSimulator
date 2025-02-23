package com.example.landfx;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Game {
    private static final int WIDTH = 25;
    private static final int HEIGHT = 25;

    private GridPane Grid;

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

    public void setGrid(GridPane Grid) {
        this.Grid = Grid;
        for (int i = 0; i < WIDTH; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(100);
            this.Grid.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < HEIGHT; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(50);
            this.Grid.getRowConstraints().add(rowConstraints);
        }
    }

    public GridPane getGrid() {
        return this.Grid;
    }
}
