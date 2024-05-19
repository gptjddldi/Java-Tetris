package com.example.javatetris;

import com.example.page.ScoreBoardAtGameEnd;
import com.example.page.StartPage;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.example.SaveFile.size.size;

public class TetrisBattleUI {
    private final int BOARD_WIDTH = 12;
    private final int BOARD_HEIGHT = 22;
    private final TetrisGame player1TetrisGame;
    private final TetrisGame player2TetrisGame;
    private final GridPane player1GameBoard;
    private final GridPane player2GameBoard;
    private final VBox side1Pane;
    private final VBox side2Pane;
    private final Pane pausePane;
    private GridPane nextTetrominoDisplay1;
    private GridPane nextTetrominoDisplay2;
    private final Text[][] player1BoardGrid = new Text[BOARD_HEIGHT][BOARD_WIDTH];
    private final Text[][] player2BoardGrid = new Text[BOARD_HEIGHT][BOARD_WIDTH];
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;
    private final BorderPane root;
    private final Stage window;
    private Timeline player1GameLoop;
    private Timeline player2GameLoop;

    public TetrisBattleUI(TetrisGame player1TetrisGame, TetrisGame player2TetrisGame, BorderPane root, Stage window) {
        this.player1TetrisGame = player1TetrisGame;
        this.player2TetrisGame = player2TetrisGame;
        this.root = root;
        this.window = window;
        this.player1GameBoard = createPlayer1GameBoard();
        this.player2GameBoard = createPlayer2GameBoard();
        this.side1Pane = createPlayer1SidePane();
        this.side2Pane = createPlayer2SidePane();
        this.pausePane = createPausePane();
    }

    public GridPane getPlayer1GameBoard() {
        return player1GameBoard;
    }

    public GridPane getPlayer2GameBoard() {
        return player2GameBoard;
    }

    public VBox getSide1Pane() {
        return side1Pane;
    }

    public VBox getSide2Pane(){return side2Pane;}

    public void setPlayer1GameLoop(Timeline player1GameLoop) {
        this.player1GameLoop = player1GameLoop;
    }

    public void setPlayer2GameLoop(Timeline player2GameLoop) {
        this.player2GameLoop = player2GameLoop;
    }

    public void updatePlayer1GameBoard() {
        updateGameBoard(player1TetrisGame, player1BoardGrid, player1ScoreLabel);
    }

    public void updatePlayer2GameBoard() {
        updateGameBoard(player2TetrisGame, player2BoardGrid, player2ScoreLabel);
    }

    private void updateGameBoard(TetrisGame tetrisGame, Text[][] boardGrid, Label scoreLabel) {
        if (tetrisGame.isGameOver()) {
            player1GameLoop.pause();
            player2GameLoop.pause();
            player1TetrisGame.pauseGame();
            player2TetrisGame.pauseGame();
            root.getChildren().add(createGameOverPane());
            return;
        }
        char[][] boardState = tetrisGame.getBoardChar();
        Color[][] boardColor = tetrisGame.getBoardColor();

        for (int y = 1; y < BOARD_HEIGHT - 1; y++) {
            for (int x = 1; x < BOARD_WIDTH - 1; x++) {
                if (boardState[y - 1][x - 1] != 'N') {
                    boardGrid[y][x].setText(boardState[y - 1][x - 1] + "");
                    boardGrid[y][x].setFill(boardColor[y - 1][x - 1]);
                } else {
                    boardGrid[y][x].setText("");
                }
            }
        }
        scoreLabel.setText("Score: " + tetrisGame.getScore());
        updatePlayer1NextTetrominoDisplay();
        updatePlayer2NextTetrominoDisplay();
    }

    private GridPane createPlayer1GameBoard() {
        GridPane gridPane = new GridPane();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                Text block = createText("O", Color.WHITE);
                player1BoardGrid[y][x] = block;
                gridPane.add(block, x, y);
            }
        }

        for (int x = 0; x < BOARD_WIDTH; x++) {
            player1BoardGrid[0][x].setFill(Color.TRANSPARENT);
            player1BoardGrid[BOARD_HEIGHT - 1][x].setFill(Color.TRANSPARENT);
        }

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            player1BoardGrid[y][0].setFill(Color.TRANSPARENT);
            player1BoardGrid[y][BOARD_WIDTH - 1].setFill(Color.TRANSPARENT);
        }


        for (int x = 0; x < BOARD_WIDTH; x++) {
            Text borderTextTop = createText("X", Color.WHITE);
            Text borderTextBottom = createText("X", Color.WHITE);
            gridPane.add(borderTextTop, x, 0);
            gridPane.add(borderTextBottom, x, BOARD_HEIGHT - 1);
        }

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            Text borderTextLeft = createText("X", Color.WHITE);
            Text borderTextRight = createText("X", Color.WHITE);
            gridPane.add(borderTextLeft, 0, y);
            gridPane.add(borderTextRight, BOARD_WIDTH - 1, y);
        }

        gridPane.setStyle("-fx-background-color: black;");

        return gridPane;
    }

    private GridPane createPlayer2GameBoard() {
        GridPane gridPane = new GridPane();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                Text block = createText("O", Color.WHITE);
                player2BoardGrid[y][x] = block;
                gridPane.add(block, x, y);
            }
        }

        for (int x = 0; x < BOARD_WIDTH; x++) {
            player2BoardGrid[0][x].setFill(Color.TRANSPARENT);
            player2BoardGrid[BOARD_HEIGHT - 1][x].setFill(Color.TRANSPARENT);
        }

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            player2BoardGrid[y][0].setFill(Color.TRANSPARENT);
            player2BoardGrid[y][BOARD_WIDTH - 1].setFill(Color.TRANSPARENT);
        }


        for (int x = 0; x < BOARD_WIDTH; x++) {
            Text borderTextTop = createText("X", Color.WHITE);
            Text borderTextBottom = createText("X", Color.WHITE);
            gridPane.add(borderTextTop, x, 0);
            gridPane.add(borderTextBottom, x, BOARD_HEIGHT - 1);
        }

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            Text borderTextLeft = createText("X", Color.WHITE);
            Text borderTextRight = createText("X", Color.WHITE);
            gridPane.add(borderTextLeft, 0, y);
            gridPane.add(borderTextRight, BOARD_WIDTH - 1, y);
        }

        gridPane.setStyle("-fx-background-color: black;");

        return gridPane;
    }
    private VBox createPlayer1SidePane() {
        VBox sidePane = new VBox(50*size());

        player1ScoreLabel = new Label("Score: 0");
        player1ScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20*size()));

        nextTetrominoDisplay1 = new GridPane();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Text block = createText("", Color.WHITE);
                nextTetrominoDisplay1.add(block, x, y);
            }
        }
        nextTetrominoDisplay1.setPrefSize(100*size(), 100*size());
        nextTetrominoDisplay1.setStyle("-fx-background-color: black;");
        updatePlayer1NextTetrominoDisplay();

        sidePane.getChildren().addAll(player1ScoreLabel, nextTetrominoDisplay1);
        return sidePane;
    }

    private VBox createPlayer2SidePane() {

        VBox sidePane = new VBox(50*size());

        player2ScoreLabel = new Label("Score: 0");
        player2ScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20*size()));

        nextTetrominoDisplay2 = new GridPane();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Text block = createText("", Color.WHITE);
                nextTetrominoDisplay2.add(block, x, y);
            }
        }
        nextTetrominoDisplay2.setPrefSize(100*size(), 100*size());
        nextTetrominoDisplay2.setStyle("-fx-background-color: black;");
        updatePlayer2NextTetrominoDisplay();

        sidePane.getChildren().addAll(player2ScoreLabel, nextTetrominoDisplay2);
        return sidePane;
    }

    private void updatePlayer1NextTetrominoDisplay() {
        nextTetrominoDisplay1.getChildren().clear();
        Tetromino nextTetromino = player1TetrisGame.getNextTetromino();
        char [][] nextState = nextTetromino.shape();
        Color nextColor = nextTetromino.color();

        for (int i = 0; i < nextState.length; i++) {
            for (int j = 0; j < nextState[i].length; j++) {
                if (nextState[i][j] != 'N') {
                    Text block = createText(nextState[i][j] + "", nextColor);
                    nextTetrominoDisplay1.add(block, j, i);
                }
            }
        }
    }

    private void updatePlayer2NextTetrominoDisplay() {
        nextTetrominoDisplay2.getChildren().clear();
        Tetromino nextTetromino = player2TetrisGame.getNextTetromino();
        char [][] nextState = nextTetromino.shape();
        Color nextColor = nextTetromino.color();

        for (int i = 0; i < nextState.length; i++) {
            for (int j = 0; j < nextState[i].length; j++) {
                if (nextState[i][j] != 'N') {
                    Text block = createText(nextState[i][j] + "", nextColor);
                    nextTetrominoDisplay2.add(block, j, i);
                }
            }
        }
    }

    private Pane createPausePane() {
        Pane pausePane = new Pane();

        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-size: "+20*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+50*size()+"px;");
        continueButton.setLayoutX(180*size());
        continueButton.setLayoutY(200*size());
        continueButton.setOnAction(e -> continueGame());

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: "+20*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+50*size()+"px;");
        exitButton.setLayoutX(180*size());
        exitButton.setLayoutY(300*size());
        exitButton.setOnAction((ActionEvent e) -> quitGame());

        pausePane.getChildren().addAll(continueButton, exitButton);

        return pausePane;
    }

    private Pane createGameOverPane() {
        Pane gameOverPane = new Pane();

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setStyle("-fx-font-size: "+ 40*size() +"px; -fx-font-weight: bold; -fx-text-fill: red;");
        gameOverLabel.setLayoutX(50*size());
        gameOverLabel.setLayoutY(200*size());

        String winner;
        if (player1TetrisGame.isGameOver() && !player2TetrisGame.isGameOver()) {
            winner = "Player 2";
        } else if (!player1TetrisGame.isGameOver() && player2TetrisGame.isGameOver()) {
            winner = "Player 1";
        } else {
            winner = "Draw"; // Both players' games ended at the same time
        }
        Label winnerLabel = new Label("Winner: " + winner);
        winnerLabel.setStyle("-fx-font-size: "+ 30*size() +"px; -fx-font-weight: bold;-fx-text-fill: red;");
        winnerLabel.setLayoutX(50*size());
        winnerLabel.setLayoutY(250*size());


        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: "+20*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+50*size()+"px;");
        exitButton.setLayoutX(80*size());
        exitButton.setLayoutY(300*size());
        exitButton.setOnAction((ActionEvent e) -> quitGame());

        gameOverPane.getChildren().addAll(gameOverLabel,winnerLabel, exitButton);

        return gameOverPane;
    }

    public void pauseGame() {
        player1GameLoop.pause();
        player2GameLoop.pause();
        player1TetrisGame.pauseGame();
        player2TetrisGame.pauseGame();
        root.getChildren().add(pausePane);
    }

    public void quitGame() {
        StartPage startPage = new StartPage();
        startPage.start(window);
    }

    public void endGame() {
        ScoreBoardAtGameEnd page = new ScoreBoardAtGameEnd(player1TetrisGame.getScore());
        page.start(window);
    }

    public void continueGame() {
        player1GameLoop.play();
        player2GameLoop.play();
        player1TetrisGame.resumeGame();
        player2TetrisGame.resumeGame();
        root.getChildren().remove(pausePane);
    }

    private Text createText(String text, Color color) {
        Text t = new Text(text);
        t.setFont(Font.font("Arial", FontWeight.BOLD, 20 * size()));
        t.setFill(color);
        return t;
    }
}
