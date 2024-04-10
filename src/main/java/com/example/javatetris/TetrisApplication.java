package com.example.javatetris;

import com.example.page.ControlsSettingsWindow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    private KeyCode[] controlKeys;

    @Override
    public void start(Stage primaryStage) { // 게임 보드와 점수 패널 생성, 사용자 입력 처리
        BorderPane root = new BorderPane();
        GridPane gameBoard = createGameBoard();
        VBox scorePane = createScorePane();
        root.setCenter(gameBoard);
        root.setRight(scorePane);

        Scene scene = new Scene(root);
<<<<<<< HEAD
=======

        KeyCode[] controlKeys = new KeyCode[4];
        //키 로드 한 후 각각 키에 키값 넣어주기
        String[] keyNames = SaveSetting.loadKeySettingsFromFile();
        for (int i = 0; i < keyNames.length; i++) {
            controlKeys[i] = KeyCode.valueOf(keyNames[i]);
        }
>>>>>>> 0c71ecdb82b6365122f01b85436ab827611fae77

        // ControlsSettingsWindow 인스턴스 생성
        ControlsSettingsWindow settingsWindow = new ControlsSettingsWindow();
        // 조작키 설정 창 표시
        settingsWindow.show();
        // 설정된 조작키 가져오기
        controlKeys = settingsWindow.getNewKeys();

        // 키 바인딩 설정
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == controlKeys[2]) tetrisGame.moveLeft();
            else if (code == controlKeys[3]) tetrisGame.moveRight();
            else if (code == controlKeys[1]) tetrisGame.moveDown();
            else if (code == controlKeys[0]) tetrisGame.rotateClockwise();
            //여기에 게임 중지 넣으시면 됩니다
            //else if () pauseGame();
            updateGameBoard();
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris");
        primaryStage.show();

        // Game loop
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            tetrisGame.moveDown();
            updateGameBoard();
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private GridPane createGameBoard() { // 게임 보드를 그리드 형태로 만듦
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

    private void updateGameBoard() {
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
