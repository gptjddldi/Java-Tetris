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
    private final VBox sidePane;
    private final Pane pausePane;
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
        this.player1GameBoard = createGameBoard();
        this.player2GameBoard = createGameBoard();
        this.sidePane = createSidePane();
        this.pausePane = createPausePane();
    }

    public GridPane getPlayer1GameBoard() {
        return player1GameBoard;
    }

    public GridPane getPlayer2GameBoard() {
        return player2GameBoard;
    }

    public VBox getSidePane() {
        return sidePane;
    }

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
        // Update any other UI elements here
    }

    private GridPane createGameBoard() {
        GridPane gridPane = new GridPane();
        // Create game board grid and add to the gridPane
        // Similar to the original createGameBoard method

        return gridPane;
    }

    private VBox createSidePane() {
        VBox sidePane = new VBox(50 * size());
        // Create side pane components (score labels, next tetromino displays, etc.)
        // Similar to the original createSidePane method

        return sidePane;
    }

    private Pane createPausePane() {
        Pane pausePane = new Pane();
        // Create pause pane components (continue button, exit button, etc.)
        // Similar to the original createPausePane method

        return pausePane;
    }

    private Pane createGameOverPane() {
        Pane gameOverPane = new Pane();
        // Create game over pane components (game over label, ranking button, etc.)
        // Similar to the original createGameOverPane method

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
