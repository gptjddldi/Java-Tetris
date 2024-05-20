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
    private final TetrisGameBattle player1TetrisGame;
    private final TetrisGameBattle player2TetrisGame;

    public TetrisBattleApplication(String mode) {
        super();
        player1TetrisGame = new TetrisGameBattle(mode);
        player2TetrisGame = new TetrisGameBattle(mode);

        ((TetrisGameBattle) player1TetrisGame).setOpponent((TetrisGameBattle) player2TetrisGame);
        ((TetrisGameBattle) player2TetrisGame).setOpponent((TetrisGameBattle) player1TetrisGame);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        TetrisBattleUI playerUI = new TetrisBattleUI(player1TetrisGame, player2TetrisGame, root, primaryStage);

        HBox hbox = new HBox(10); // 10은 컴포넌트 사이의 간격
        hbox.getChildren().addAll(playerUI.getPlayer1GameBoard(), playerUI.getSide1Pane(),
                playerUI.getPlayer2GameBoard(), playerUI.getSide2Pane());

        hbox.setPrefSize(1000, 0); // 필요한 크기로 설정, 이 부분을 지우면 크기 잘리는 문제 발생
        //hbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);



        root.setCenter(hbox);

        Scene scene = new Scene(root);

        KeyCode[] player1Keys = new KeyCode[6];
        KeyCode[] player2Keys = new KeyCode[6];
        String[] keyNames = SaveSetting.loadKeySettingsFromFile();
        for (int i = 0; i < 6; i++) {
            player1Keys[i] = KeyCode.valueOf(keyNames[i+5]);
            player2Keys[i] = KeyCode.valueOf(keyNames[i]); // Assuming both players have the same key settings
        }

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            if (code == player1Keys[2]) player1TetrisGame.moveLeft();
            else if (code == player1Keys[3]) player1TetrisGame.moveRight();
            else if (code == player1Keys[1]) player1TetrisGame.moveDown();
            else if (code == player1Keys[0]) player1TetrisGame.rotateClockwise();
            if (code == player1Keys[5]) {
                playerUI.pauseGame();
            }

            else if(code == player1Keys[4]) player1TetrisGame.moveDownAll();

            if (code == player2Keys[2]) player2TetrisGame.moveLeft();
            else if (code == player2Keys[3]) player2TetrisGame.moveRight();
            else if (code == player2Keys[1]) player2TetrisGame.moveDown();
            else if (code == player2Keys[0]) player2TetrisGame.rotateClockwise();
            /*if (code == player2Keys[10]) {
                playerUI.pauseGame();
            }*/
                //else if (code == player2Keys[4]) player2UI.pauseGame();
            else if(code == player2Keys[4]) player2TetrisGame.moveDownAll();

            playerUI.updatePlayer1GameBoard();
            playerUI.updatePlayer2GameBoard();
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris Battle Mode");
        primaryStage.show();

        // Game loops
        Timeline player1GameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> playerUI.updatePlayer1GameBoard()));
        player1GameLoop.setCycleCount(Timeline.INDEFINITE);
        player1GameLoop.play();
        playerUI.setPlayer1GameLoop(player1GameLoop);

        Timeline player2GameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> playerUI.updatePlayer2GameBoard()));
        player2GameLoop.setCycleCount(Timeline.INDEFINITE);
        player2GameLoop.play();
        playerUI.setPlayer2GameLoop(player2GameLoop);
    }

    public static void main(String[] args) {
        launch(args);
    }
}