package com.example.javatetris;

import com.example.SaveFile.SaveSetting;
import com.example.page.ScoreBoardAtGameEnd;
import com.example.page.ScreenSizeSettingsWindow;
import com.example.page.StartPage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TetrisApplication extends Application {
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final TetrisGame tetrisGame = new TetrisGame();
    private Text[][] boardGrid;
    private Label scoreLabel;
    private Timeline gameLoop;
    private Pane pausePane;
    private BorderPane root;
    private Boolean isPause = false;
    private static Stage window;
    private KeyCode[] controlKeys;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        root = new BorderPane();
        GridPane gameBoard = createGameBoard();
        VBox scorePane = createSidePane();
        root.setCenter(gameBoard);
        root.setRight(scorePane);
        pausePane = createPausePane();

        Scene scene = new Scene(root);
        KeyCode[] controlKeys = new KeyCode[5];
        //키 로드 한 후 각각 키에 키값 넣어주기
        String[] keyNames = SaveSetting.loadKeySettingsFromFile();
        for (int i = 0; i < keyNames.length; i++) {
            controlKeys[i] = KeyCode.valueOf(keyNames[i]);
        }


        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (controlKeys != null ) {
                if (code == controlKeys[0]) tetrisGame.moveLeft();
                else if (code == controlKeys[1]) tetrisGame.moveRight();
                else if (code == controlKeys[2]) tetrisGame.moveDown();
                else if (code == controlKeys[3]) tetrisGame.rotateClockwise();
                else if (code == controlKeys[4]) tetrisGame.pauseGame();
            } else {
                // 기본 조작키 설정
                switch (code) {
                    case LEFT -> tetrisGame.moveLeft();
                    case RIGHT -> tetrisGame.moveRight();
                    case DOWN -> tetrisGame.moveDown();
                    case UP -> tetrisGame.rotateClockwise();
                    case SPACE -> tetrisGame.pauseGame();
                }
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
        boardGrid = new Text[BOARD_HEIGHT][BOARD_WIDTH]; // Change to Text array

        // Create the game board with 'o's
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                Text block = new Text("O");
                block.setFill(Color.GREEN); // Set color for the text
                block.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Set font size and style
                boardGrid[y][x] = block;
                gridPane.add(block, x, y);
            }
        }

        for (int x = 0; x < BOARD_WIDTH; x++) {
            boardGrid[0][x].setFill(Color.TRANSPARENT); // Make the top border 'o's invisible
            boardGrid[BOARD_HEIGHT - 1][x].setFill(Color.TRANSPARENT); // Make the bottom border 'o's invisible
        }

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            boardGrid[y][0].setFill(Color.TRANSPARENT); // Make the left border 'o's invisible
            boardGrid[y][BOARD_WIDTH - 1].setFill(Color.TRANSPARENT); // Make the right border 'o's invisible
        }


        // Add 'x's for the border
        for (int x = 0; x < BOARD_WIDTH; x++) {
            Text borderTextTop = new Text("X");
            Text borderTextBottom = new Text("X");
            borderTextTop.setFill(Color.WHITE); // Make the rectangle invisible
            borderTextBottom.setFill(Color.WHITE); // Make the rectangle invisible
            borderTextTop.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Set font size and style
            borderTextBottom.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Set font size and style
            gridPane.add(borderTextTop, x, 0); // Add to the top row
            gridPane.add(borderTextBottom, x, BOARD_HEIGHT - 1); // Add to the bottom row
        }

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            Text borderTextLeft = new Text("X");
            Text borderTextRight = new Text("X");
            borderTextLeft.setFill(Color.WHITE); // Make the rectangle invisible
            borderTextRight.setFill(Color.WHITE); // Make the rectangle invisible
            borderTextLeft.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Set font size and style
            borderTextRight.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Set font size and style
            gridPane.add(borderTextLeft, 0, y); // Add to the left column
            gridPane.add(borderTextRight, BOARD_WIDTH - 1, y); // Add to the right column
        }

        gridPane.setStyle("-fx-background-color: black;");

        return gridPane;
    }

    private VBox createSidePane() {
        VBox sidePane = new VBox(10); // 상대적인 여백을 설정합니다.

        // 점수 표시 레이블 추가
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 14px;"); // 폰트 크기 설정
        VBox.setMargin(scoreLabel, new Insets(10, 10, 10, 10)); // 여백 설정
        VBox.setVgrow(scoreLabel, Priority.ALWAYS); // 확장 여부 설정
        sidePane.getChildren().add(scoreLabel);

        // 다음 테트로미노 레이블 추가
        Label nextTetrominoLabel = new Label("Next Tetromino:");
        nextTetrominoLabel.setStyle("-fx-font-size: 14px;"); // 폰트 크기 설정
        VBox.setMargin(nextTetrominoLabel, new Insets(10, 10, 10, 10)); // 여백 설정
        VBox.setVgrow(nextTetrominoLabel, Priority.ALWAYS); // 확장 여부 설정
        sidePane.getChildren().add(nextTetrominoLabel);

        // 다음 테트로미노 모양을 나타내는 레이블 추가
        Text nextTetrominoText = new Text(tetrisGame.getNextTetromino().toString());
        nextTetrominoText.setStyle("-fx-font-size: 14px;"); // 폰트 크기 설정
        VBox.setMargin(nextTetrominoText, new Insets(10, 10, 10, 10)); // 여백 설정
        VBox.setVgrow(nextTetrominoText, Priority.ALWAYS); // 확장 여부 설정
        sidePane.getChildren().add(nextTetrominoText);

        // 확장 가능한 UI 요소 추가 가능

        return sidePane;
    }


    private Pane createPausePane() {
        Pane pausePane = new Pane();

        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 50px;");
        continueButton.setLayoutX(80);
        continueButton.setLayoutY(200);
        continueButton.setOnAction(e -> continueGame());

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 50px;");
        exitButton.setLayoutX(80);
        exitButton.setLayoutY(300);
        exitButton.setOnAction((ActionEvent e) -> quitGame());

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
        rankingButton.setOnAction(e -> endGame());

        gameOverPane.getChildren().addAll(gameOverLabel, rankingButton);

        return gameOverPane;
    }

    private void updateGameBoard() {
        if(tetrisGame.isGameOver()){
            root.getChildren().add(createGameOverPane());
            return;
        }
        boolean[][] boardState = tetrisGame.getBoardState();

        for (int y = 1; y < BOARD_HEIGHT-1; y++) {
            for (int x = 1; x < BOARD_WIDTH-1; x++) {

                if (boardState[y-1][x-1]){
                    boardGrid[y][x].setText("O");

                }else{
                    boardGrid[y][x].setText("");
                }
            }
        }
        scoreLabel.setText("Score: " + tetrisGame.getScore());
    }

    public static void main(String[] args) {
        launch(args);
    }
}