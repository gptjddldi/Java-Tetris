package com.example.javatetris;

import com.example.page.ScoreBoardAtGameEnd;
import com.example.page.StartPage;
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
    private Pane pausePane;
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
        pausePane = createPausePane();

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if(isPause) return;
            KeyCode code = event.getCode();
            switch (code) {
                case LEFT -> tetrisGame.moveLeft();
                case RIGHT -> tetrisGame.moveRight();
                case DOWN -> tetrisGame.moveDown();
                case UP -> tetrisGame.rotateClockwise();
                case SPACE -> pauseGame();
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

    private void pauseGame() {
        isPause = true;
        gameLoop.pause();
        tetrisGame.pauseGame();
        root.getChildren().add(pausePane);
    }

    public void quitGame(){
        StartPage startPage = new StartPage();
        startPage.start(window);
    }
    public void endGame(){
        ScoreBoardAtGameEnd page = new ScoreBoardAtGameEnd(tetrisGame.getScore());
        page.start(window);
    }

    public void continueGame(){
        isPause = false;
        gameLoop.play();
        tetrisGame.resumeGame();
        root.getChildren().remove(pausePane);
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

    private Pane createPausePane() {
        Pane pausePane = new Pane();

        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 50px;");
        continueButton.setLayoutX(80);
        continueButton.setLayoutY(200);
        continueButton.setOnAction(e -> {
            continueGame();
        });

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 50px;");
        exitButton.setLayoutX(80);
        exitButton.setLayoutY(300);
        exitButton.setOnAction(e -> {
            quitGame();
        });

        pausePane.getChildren().addAll(continueButton, exitButton);

        return pausePane;
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