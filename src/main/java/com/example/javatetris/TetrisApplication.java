package com.example.javatetris;

import com.example.page.ScoreBoardAtGameEnd;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TetrisApplication extends Application {
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final TetrisGame tetrisGame = new TetrisGame();
    private Rectangle[][] boardGrid;
    private Label scoreLabel;
    private Timeline gameLoop;
    private BorderPane root;
    private Boolean isPause = false;
    private static Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        root = new BorderPane();
        GridPane gameBoard = createGameBoard();
        VBox scorePane = createScorePane();
        root.setCenter(gameBoard);
        root.setRight(scorePane);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if(isPause) return;
            KeyCode code = event.getCode();
            switch (code) {
                case LEFT -> tetrisGame.moveLeft();
                case RIGHT -> tetrisGame.moveRight();
                case DOWN -> tetrisGame.moveDown();
                case UP -> tetrisGame.rotateClockwise();
            }
            updateGameBoard();
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris");
        primaryStage.show();

        // Game loop
        gameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            if(isPause) return;
            updateGameBoard();
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }
    public void endGame(){
        ScoreBoardAtGameEnd page = new ScoreBoardAtGameEnd(tetrisGame.getScore());
        page.start(window);
    }


    private GridPane createGameBoard() {
        GridPane gridPane = new GridPane();
        boardGrid = new Rectangle[BOARD_HEIGHT][BOARD_WIDTH];

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                int BLOCK_SIZE = 30;
                Rectangle block = new Rectangle(BLOCK_SIZE, BLOCK_SIZE, Color.WHITE);
                block.setStroke(Color.BLACK);
                boardGrid[y][x] = block;
                gridPane.add(block, x, y);
            }
        }
        return gridPane;
    }

    private VBox createScorePane() {
        VBox scorePane = new VBox();
        scorePane.setSpacing(10);

        // Score label
        scoreLabel = new Label("Score: 0"); // Initial score
        scorePane.getChildren().add(scoreLabel);

        // Add any additional score-related UI elements here

        return scorePane;
    }

    private Pane createGameOverPane() {
        Pane gameOverPane = new Pane();

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: red;");
        gameOverLabel.setLayoutX(50);
        gameOverLabel.setLayoutY(200);

        Button rankingButton = new Button("Ranking");
        rankingButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 50px;");
        rankingButton.setLayoutX(80);
        rankingButton.setLayoutY(300);
        rankingButton.setOnAction(e -> {
            endGame();
        });

        gameOverPane.getChildren().addAll(gameOverLabel, rankingButton);

        return gameOverPane;
    }

    private void updateGameBoard() {
        if(tetrisGame.isGameOver()){
            root.getChildren().add(createGameOverPane());
            return;
        }
        boolean[][] boardState = tetrisGame.getBoardState();

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                boardGrid[y][x].setFill(boardState[y][x] ? Color.GREEN : Color.WHITE);
            }
        }
        scoreLabel.setText("Score: " + tetrisGame.getScore());
    }

    public static void main(String[] args) {
        launch(args);
    }
}