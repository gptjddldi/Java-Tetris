package com.example.javatetris;

import com.example.SaveFile.SaveSetting;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TetrisBattleApplication extends Application {
    private final TetrisGame player1TetrisGame;
    private final TetrisGame player2TetrisGame;

    public TetrisBattleApplication(String mode) {
        super();
        player1TetrisGame = new TetrisGame(mode);
        player2TetrisGame = new TetrisGame(mode);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        TetrisUI player1UI = new TetrisUI(player1TetrisGame, root, primaryStage);
        TetrisUI player2UI = new TetrisUI(player2TetrisGame, root, primaryStage);


        HBox hbox = new HBox(10); // 10은 컴포넌트 사이의 간격
        hbox.getChildren().addAll(player1UI.getGameBoard(), player1UI.getSidePane(),
                player2UI.getGameBoard(), player2UI.getSidePane());

        Scene scene = new Scene(hbox, 800, 600);

        KeyCode[] player1Keys = new KeyCode[6];
        KeyCode[] player2Keys = new KeyCode[6];
        String[] keyNames = SaveSetting.loadKeySettingsFromFile();
        for (int i = 0; i < 6; i++) {
            player1Keys[i] = KeyCode.valueOf(keyNames[i+9]);
            player2Keys[i] = KeyCode.valueOf(keyNames[i]); // Assuming both players have the same key settings
        }

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == player1Keys[2]) player1TetrisGame.moveLeft();
            else if (code == player1Keys[3]) player1TetrisGame.moveRight();
            else if (code == player1Keys[1]) player1TetrisGame.moveDown();
            else if (code == player1Keys[0]) player1TetrisGame.rotateClockwise();
            else if (code == player1Keys[4]) player1TetrisGame.pauseGame();
            else if(code == player1Keys[5]) player1TetrisGame.moveDownAll();

            if (code == player2Keys[2]) player2TetrisGame.moveLeft();
            else if (code == player2Keys[3]) player2TetrisGame.moveRight();
            else if (code == player2Keys[1]) player2TetrisGame.moveDown();
            else if (code == player2Keys[0]) player2TetrisGame.rotateClockwise();
            else if (code == player2Keys[4]) player2TetrisGame.pauseGame();
            else if(code == player2Keys[5]) player2TetrisGame.moveDownAll();

            player1UI.updateGameBoard();
            player2UI.updateGameBoard();
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris Battle Mode");
        primaryStage.show();

        // Game loops
        Timeline player1GameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> player1UI.updateGameBoard()));
        player1GameLoop.setCycleCount(Timeline.INDEFINITE);
        player1GameLoop.play();
        player1UI.setGameLoop(player1GameLoop);

        Timeline player2GameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> player2UI.updateGameBoard()));
        player2GameLoop.setCycleCount(Timeline.INDEFINITE);
        player2GameLoop.play();
        player2UI.setGameLoop(player2GameLoop);
    }

    public static void main(String[] args) {
        launch(args);
    }
}