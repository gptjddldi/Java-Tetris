package org.example.myjavafxtetris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.example.myjavafxtetris.TetrisGame;

public class TetrisUI extends Application {
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final int BLOCK_SIZE = 30;
    private final TetrisGame tetrisGame = new TetrisGame();
    private Rectangle[][] boardGrid;
    private Timeline gameLoop;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane gameBoard = createGameBoard();
        root.setCenter(gameBoard);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {
                case LEFT:
                    tetrisGame.moveLeft();
                    break;
                case RIGHT:
                    tetrisGame.moveRight();
                    break;
                case DOWN:
                    tetrisGame.moveDown();
                    break;
                case UP:
                    tetrisGame.rotateClockwise();
                    break;
            }
            updateGameBoard();
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris");
        primaryStage.show();

        // Game loop
        gameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            tetrisGame.moveDown();
            updateGameBoard();
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private GridPane createGameBoard() {
        GridPane gridPane = new GridPane();
        boardGrid = new Rectangle[BOARD_HEIGHT][BOARD_WIDTH];

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                Rectangle block = new Rectangle(BLOCK_SIZE, BLOCK_SIZE, Color.WHITE);
                block.setStroke(Color.BLACK);
                boardGrid[y][x] = block;
                gridPane.add(block, x, y);
            }
        }
        return gridPane;
    }

    private void updateGameBoard() {
        boolean[][] boardState = tetrisGame.getBoardState();

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                boardGrid[y][x].setFill(boardState[y][x] ? Color.GREEN : Color.WHITE);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
