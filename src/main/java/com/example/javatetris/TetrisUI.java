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

public class TetrisUI {
    private final int BOARD_WIDTH = 12;
    private final int BOARD_HEIGHT = 22;
    private final TetrisGame tetrisGame;
    private final GridPane gameBoard;
    private final VBox sidePane;
    private final Pane pausePane;
    private GridPane nextTetrominoDisplay;
    private final Text[][] boardGrid = new Text[BOARD_HEIGHT][BOARD_WIDTH];
    private Label scoreLabel;
    private final BorderPane root;
    private final Stage window;
    private Timeline gameLoop;
    public TetrisUI(TetrisGame tetrisGame, BorderPane root, Stage window) {
        this.tetrisGame = tetrisGame;
        this.root = root;
        this.window = window;
        this.gameBoard = createGameBoard();
        this.sidePane = createSidePane();
        this.pausePane = createPausePane();
    }

    public GridPane getGameBoard() {
        return gameBoard;
    }

    public VBox getSidePane() {
        return sidePane;
    }

    public void setGameLoop(Timeline gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void updateGameBoard() {
        if(tetrisGame.isGameOver()){
            root.getChildren().add(createGameOverPane());
            return;
        }
        char[][] boardState = tetrisGame.getBoardChar();
        Color[][] boardColor = tetrisGame.getBoardColor();

        for (int y = 1; y < BOARD_HEIGHT-1; y++) {
            for (int x = 1; x < BOARD_WIDTH-1; x++) {

                if (boardState[y-1][x-1] != 'N'){
                    boardGrid[y][x].setText(boardState[y-1][x-1] + "");
                    boardGrid[y][x].setFill(boardColor[y-1][x-1]);

                } else{
                    boardGrid[y][x].setText("");
                }
            }
        }
        scoreLabel.setText("Score: " + tetrisGame.getScore());
        updateNextTetrominoDisplay();
    }
    private GridPane createGameBoard() {
        GridPane gridPane = new GridPane();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                Text block = createText("O", Color.WHITE);
                boardGrid[y][x] = block;
                gridPane.add(block, x, y);
            }
        }

        for (int x = 0; x < BOARD_WIDTH; x++) {
            boardGrid[0][x].setFill(Color.TRANSPARENT);
            boardGrid[BOARD_HEIGHT - 1][x].setFill(Color.TRANSPARENT);
        }

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            boardGrid[y][0].setFill(Color.TRANSPARENT);
            boardGrid[y][BOARD_WIDTH - 1].setFill(Color.TRANSPARENT);
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

    private VBox createSidePane() {
        VBox sidePane = new VBox(50*size());

        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20*size()));

        nextTetrominoDisplay = new GridPane();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Text block = createText("", Color.WHITE);
                nextTetrominoDisplay.add(block, x, y);
            }
        }
        nextTetrominoDisplay.setPrefSize(100*size(), 100*size());
        nextTetrominoDisplay.setStyle("-fx-background-color: black;");
        updateNextTetrominoDisplay();

        sidePane.getChildren().addAll(scoreLabel, nextTetrominoDisplay);
        return sidePane;
    }

    private void updateNextTetrominoDisplay() {
        nextTetrominoDisplay.getChildren().clear();
        Tetromino nextTetromino = tetrisGame.getNextTetromino();
        char [][] nextState = nextTetromino.shape();
        Color nextColor = nextTetromino.color();

        for (int i = 0; i < nextState.length; i++) {
            for (int j = 0; j < nextState[i].length; j++) {
                if (nextState[i][j] != 'N') {
                    Text block = createText(nextState[i][j] + "", nextColor);
                    nextTetrominoDisplay.add(block, j, i);
                }
            }
        }
    }
    private Pane createPausePane() {
        Pane pausePane = new Pane();

        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-size: "+20*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+50*size()+"px;");
        continueButton.setLayoutX(80*size());
        continueButton.setLayoutY(200*size());
        continueButton.setOnAction(e -> continueGame());

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: "+20*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+50*size()+"px;");
        exitButton.setLayoutX(80*size());
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

        Button rankingButton = new Button("Ranking");
        rankingButton.setStyle("-fx-font-size: "+20*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+50*size()+"px;");
        rankingButton.setLayoutX(80*size());
        rankingButton.setLayoutY(300*size());
        rankingButton.setOnAction(e -> endGame());

        gameOverPane.getChildren().addAll(gameOverLabel, rankingButton);

        return gameOverPane;
    }

    public void pauseGame() {
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
        gameLoop.play();
        tetrisGame.resumeGame();
        root.getChildren().remove(pausePane);
    }

    private Text createText(String text, Color color) {
        Text t = new Text(text);
        t.setFont(Font.font("Arial", FontWeight.BOLD, 20*size()));
        t.setFill(color);
        return t;
    }
}
