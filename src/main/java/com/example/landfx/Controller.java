package com.example.landfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileNotFoundException;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    private GridPane Grid;

    private Game game;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public GridPane getGrid() {
        return Grid;
    }

    public void setGrid(GridPane grid) {
        this.Grid = grid;
    }

    public void initialize() {
        this.game = Game.getInstance();

        try {
            this.game.setGrid(this.Grid);
        } catch (FileNotFoundException e) {
            System.out.println("[ОШИБКА!] Не найдены изображения!");
        }


        this.Grid.setGridLinesVisible(true);
    }
}