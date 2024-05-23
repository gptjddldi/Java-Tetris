package com.example.javatetris;

import com.example.SaveFile.SaveSetting;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.example.SaveFile.size.size;

public class TetrisBattleApplication extends Application {
    private final int TIMER_DURATION = 180;
    private final TetrisGameBattle player1TetrisGame;
    private final TetrisGameBattle player2TetrisGame;
    private boolean timerEnded = false;
    private int timeRemaining = TIMER_DURATION;
    private Label timerLabel;
    private Timeline timer;
    private String mode;

    public TetrisBattleApplication(String mode) {
        super();
        this.mode = mode;
        player1TetrisGame = new TetrisGameBattle(mode);
        player2TetrisGame = new TetrisGameBattle(mode);

        ((TetrisGameBattle) player1TetrisGame).setOpponent((TetrisGameBattle) player2TetrisGame);
        ((TetrisGameBattle) player2TetrisGame).setOpponent((TetrisGameBattle) player1TetrisGame);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        if (mode.equals("time")) {
            addTimer();
        }
        TetrisBattleUI playerUI = new TetrisBattleUI(player1TetrisGame, player2TetrisGame, root, primaryStage, timer);

        GridPane player1GameBoard = playerUI.getPlayer1GameBoard();
        player1GameBoard.setMaxSize(240*size(), 480*size());
        VBox side1Pane = playerUI.getSide1Pane();
        side1Pane.setMaxSize(120*size(), 480*size());

        GridPane player2GameBoard = playerUI.getPlayer2GameBoard();
        player2GameBoard.setMaxSize(240*size(), 480*size());
        VBox side2Pane = playerUI.getSide2Pane();
        side2Pane.setMaxSize(120*size(), 480*size());

        HBox player1Layout = new HBox();
        player1Layout.getChildren().addAll(player1GameBoard, side1Pane);

        HBox player2Layout = new HBox();
        player2Layout.getChildren().addAll(player2GameBoard, side2Pane);

        VBox timerBox = createTimerUI();

        HBox mainLayout = new HBox();
        mainLayout.getChildren().addAll(player1Layout, timerBox, player2Layout);
        mainLayout.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(mainLayout, 800*size(), 500*size());

        KeyCode[] player1Keys = new KeyCode[6];
        KeyCode[] player2Keys = new KeyCode[6];
        String[] keyNames = SaveSetting.loadKeySettingsFromFile();
        for (int i = 0; i < 6; i++) {
            player1Keys[i] = KeyCode.valueOf(keyNames[i+5]);
            player2Keys[i] = KeyCode.valueOf(keyNames[i]); // Assuming both players have the same key settings
        }

        scene.setOnKeyPressed(event -> {
            if (timerEnded) return;

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
        Timeline player1GameLoop = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> playerUI.updatePlayer1GameBoard()));
        player1GameLoop.setCycleCount(Timeline.INDEFINITE);
        player1GameLoop.play();
        playerUI.setPlayer1GameLoop(player1GameLoop);

        Timeline player2GameLoop = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> playerUI.updatePlayer2GameBoard()));
        player2GameLoop.setCycleCount(Timeline.INDEFINITE);
        player2GameLoop.play();
        playerUI.setPlayer2GameLoop(player2GameLoop);
    }

    private VBox createTimerUI() {
        timerLabel = new Label();
        updateTimerLabel();
        timerLabel.setFont(new Font(20 * size())); // 원하는 폰트 크기로 설정
        VBox timerBox = new VBox(timerLabel);
        timerBox.setPrefWidth(100 * size()); // 필요한 경우 타이머 UI의 크기를 설정
        timerBox.setStyle("-fx-alignment: center;"); // 타이머를 가운데 정렬
        return timerBox;
    }

    private void updateTimerLabel() {
        System.out.println("timeRemaining: " + timeRemaining);
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void addTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (timeRemaining > 0) {
                timeRemaining--;
                updateTimerLabel();
            } else {
                timerEnded = true;
                player1TetrisGame.setGameOver(true);
                player2TetrisGame.setGameOver(true);
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}