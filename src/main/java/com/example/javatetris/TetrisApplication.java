package com.example.javatetris;

import com.example.SaveFile.SaveSetting;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;

public class TetrisApplication extends Application {
    private final TetrisGame tetrisGame;
    public TetrisApplication() {
        super();
        tetrisGame = new TetrisGame("normal");
    }

    public TetrisApplication(String mode) {
        super();
        tetrisGame = new TetrisGame(mode);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        TetrisUI tetrisUI = new TetrisUI(tetrisGame, root, primaryStage);
        root.setCenter(tetrisUI.getGameBoard());
        root.setRight(tetrisUI.getSidePane());

        Scene scene = new Scene(root);

        KeyCode[] controlKeys = new KeyCode[11];
        String[] keyNames = SaveSetting.loadKeySettingsFromFile();
        for (int i = 0; i < 11; i++) {
            controlKeys[i] = KeyCode.valueOf(keyNames[i]);
        }

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == controlKeys[2]) tetrisGame.moveLeft();
            else if (code == controlKeys[3]) tetrisGame.moveRight();
            else if (code == controlKeys[1]) tetrisGame.moveDown();
            else if (code == controlKeys[0]) tetrisGame.rotateClockwise();
            else if (code == controlKeys[10]) tetrisUI.pauseGame();
            else if(code == controlKeys[4]) tetrisGame.moveDownAll();
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