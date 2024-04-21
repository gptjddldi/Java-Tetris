package com.example.javatetris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TetrisApplication extends Application {
    private final TetrisGame tetrisGame = new TetrisGame();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        TetrisUI tetrisUI = new TetrisUI(tetrisGame, root, primaryStage);
        root.setCenter(tetrisUI.getGameBoard());
        root.setRight(tetrisUI.getSidePane());

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {
                case LEFT -> tetrisGame.moveLeft();
                case RIGHT -> tetrisGame.moveRight();
                case DOWN -> tetrisGame.moveDown();
                case UP -> tetrisGame.rotateClockwise();
                case SPACE -> tetrisUI.pauseGame();
            }
            tetrisUI.updateGameBoard();
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris");
        primaryStage.show();

        // Game loop
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> tetrisUI.updateGameBoard()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
        tetrisUI.setGameLoop(gameLoop);
    }


    public static void main(String[] args) {
        launch(args);
    }
}