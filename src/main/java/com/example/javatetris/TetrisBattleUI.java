package com.example.javatetris;

import com.example.page.ScoreBoardAtGameEnd;
import com.example.page.StartPage;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
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
import static com.example.page.StartPage.mode;

public class TetrisBattleUI {
    private final int BOARD_WIDTH = 12;
    private final int BOARD_HEIGHT = 22;
    private final TetrisGameBattle player1TetrisGame;
    private final TetrisGameBattle player2TetrisGame;
    private final GridPane player1GameBoard;
    private final GridPane player2GameBoard;
    private final VBox side1Pane;
    private final VBox side2Pane;
    private final Pane pausePane;
    private GridPane nextTetrominoDisplay1;
    private GridPane nextTetrominoDisplay2;
    private GridPane nextAttackDisplay1;
    private GridPane nextAttackDisplay2;
    private Text[][] player1DisplayGrid = new Text[BOARD_HEIGHT-2][BOARD_WIDTH-2];
    private Text[][] player2DisplayGrid = new Text[BOARD_HEIGHT-2][BOARD_WIDTH-2];
    private Text[][] player1BoardGrid = new Text[BOARD_HEIGHT][BOARD_WIDTH];
    private Text[][] player2BoardGrid = new Text[BOARD_HEIGHT][BOARD_WIDTH];
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;
    private final BorderPane root;
    private final Stage window;
    private Timeline player1GameLoop;
    private Timeline player2GameLoop;
    private Timeline timer;
    private String mode;

    public TetrisBattleUI(TetrisGameBattle player1TetrisGame, TetrisGameBattle player2TetrisGame, BorderPane root, Stage window) {
        this.player1TetrisGame = player1TetrisGame;
        this.player2TetrisGame = player2TetrisGame;
        this.root = root;
        this.window = window;
        this.player1GameBoard = createGameBoard("player1");
        this.player2GameBoard = createGameBoard("player2");
        this.side1Pane = createSidePane("player1");
        this.side2Pane = createSidePane("player2");
        this.pausePane = createPausePane();
        //this.mode = mode;
        //this.timer = timer;
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

    public void setTimer(Timeline timer){this.timer = timer;}
    public void setMode(String mode){this.mode = mode;}

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
            if(mode.equals("time")){
                timer.pause();
            }
            //timer.pause();
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
        updateNextTetrominoDisplay("player1");
        updateNextTetrominoDisplay("player2");
        updateNextAttackDisplay("player1", player1DisplayGrid);
        updateNextAttackDisplay("player2", player2DisplayGrid);
    }

    private GridPane createGameBoard(String player) {
        Text [][] boardGrid = new Text[BOARD_HEIGHT][BOARD_WIDTH];

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

        if (player.equals("player1")) {
            player1BoardGrid = boardGrid;
        } else {
            player2BoardGrid = boardGrid;
        }

        return gridPane;
    }

    private VBox createSidePane(String player) {
        Label scoreLabel;
        VBox sidePane = new VBox(5*size());
        Text[][] displayGrid = new Text[BOARD_HEIGHT-2][BOARD_WIDTH-2];

        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20*size()));

        GridPane nextTetrominoDisplay = new GridPane();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Text block = createText("O", Color.TRANSPARENT);
                nextTetrominoDisplay.add(block, x, y);
            }
        }
        nextTetrominoDisplay.setMinSize(5 * size() * 20, 5 * size() * 20);
        nextTetrominoDisplay.setMaxSize(5 * size() * 20, 5 * size() * 20);
        nextTetrominoDisplay.setStyle("-fx-background-color: black;");

        GridPane nextAttackDisplay = new GridPane();
        for (int y = 0; y < BOARD_HEIGHT-2; y++) {
            for (int x = 0; x < BOARD_WIDTH-2; x++) {
                Text block = createSizedText("O", Color.CYAN, 15);
                nextAttackDisplay.add(block, x, y);
                displayGrid[y][x] = block;
            }
        }

        nextAttackDisplay.setMaxSize((BOARD_WIDTH-4) * size() * 15, (BOARD_HEIGHT+1) * size() * 15);
        nextAttackDisplay.setMinSize((BOARD_WIDTH-4) * size() * 15, (BOARD_HEIGHT+1) * size() * 15); // 300*size
        nextAttackDisplay.setStyle("-fx-background-color: black;");

        if (player.equals("player1")) {
            player1ScoreLabel = scoreLabel;
            nextTetrominoDisplay1 = nextTetrominoDisplay;
            nextAttackDisplay1 = nextAttackDisplay;
            player1DisplayGrid = displayGrid;
            updateNextTetrominoDisplay(player);
            sidePane.getChildren().addAll(player1ScoreLabel, nextTetrominoDisplay1, nextAttackDisplay1);

            VBox.setMargin(nextTetrominoDisplay1, new Insets(0, 0, 0, 0));
        } else {
            player2ScoreLabel = scoreLabel;
            nextTetrominoDisplay2 = nextTetrominoDisplay;
            nextAttackDisplay2 = nextAttackDisplay;
            player2DisplayGrid = displayGrid;
            updateNextTetrominoDisplay(player);
            sidePane.getChildren().addAll(player2ScoreLabel, nextTetrominoDisplay2, nextAttackDisplay2);

            VBox.setMargin(nextTetrominoDisplay2, new Insets(0, 0, 0, 0));
        }


        return sidePane;
    }

    private void updateNextTetrominoDisplay(String player) {
        GridPane nextTetrominoDisplay;
        TetrisGame tetrisGame;
        if (player.equals("player1")) {
            nextTetrominoDisplay = nextTetrominoDisplay1;
            tetrisGame = player1TetrisGame;
        } else {
            nextTetrominoDisplay = nextTetrominoDisplay2;
            tetrisGame = player2TetrisGame;
        }
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

        if(player.equals("player1")){
            nextTetrominoDisplay1 = nextTetrominoDisplay;
        } else {
            nextTetrominoDisplay2 = nextTetrominoDisplay;
        }
    }

    private void updateNextAttackDisplay(String player, Text[][] displayGrid) {
        GridPane nextAttackDisplay;
        TetrisGameBattle tetrisGame;
        if (player.equals("player1")) {
            nextAttackDisplay = nextAttackDisplay1;
            tetrisGame = player1TetrisGame;
        } else {
            nextAttackDisplay = nextAttackDisplay2;
            tetrisGame = player2TetrisGame;
        }

        char[][] nextAttack = tetrisGame.getNextAttack();
        if (nextAttack == null) {
            for (int y = 0; y < BOARD_HEIGHT-2; y++) {
                for (int x = 0; x < BOARD_WIDTH-2; x++) {
                    displayGrid[y][x].setText("O");
                    displayGrid[y][x].setFill(Color.TRANSPARENT);
                }
            }
            return;
        }

        Color nextColor = Color.rgb(136, 204, 238); //cyan
        int num = nextAttack.length;
        for(int y = BOARD_HEIGHT-3; y > BOARD_HEIGHT-3-num; y--) {
            for (int x = 0; x < BOARD_WIDTH-2; x++) {
                if (nextAttack[BOARD_HEIGHT-3-y][x] != 'N') {
                    displayGrid[y][x].setText("O");
                    displayGrid[y][x].setFill(nextColor);
                } else {
                    displayGrid[y][x].setText("N");
                    displayGrid[y][x].setFill(Color.TRANSPARENT);
                }
            }
        }

        if(player.equals("player1")){
            nextAttackDisplay1 = nextAttackDisplay;
        } else {
            nextAttackDisplay2 = nextAttackDisplay;
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

        String winner = calculateWinner();
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

    private String calculateWinner() {
        String winner;
        if (player1TetrisGame.isGameOver() && !player2TetrisGame.isGameOver()) {
            winner = "Player 2";
        } else if (!player1TetrisGame.isGameOver() && player2TetrisGame.isGameOver()) {
            winner = "Player 1";
        }
        // Timer 모드 or 동시에 죽은 경우
        else {
            int player1Score = player1TetrisGame.getScore();
            int player2Score = player2TetrisGame.getScore();
            if (player1Score > player2Score) {
                winner = "Player 1";
            } else if (player1Score < player2Score) {
                winner = "Player 2";
            } else {
                winner = "Draw";
            }
        }
        return winner;
    }

    public void pauseGame() {
        player1GameLoop.pause();
        player2GameLoop.pause();
        player1TetrisGame.pauseGame();
        player2TetrisGame.pauseGame();
        if(mode.equals("time")){
            timer.pause();
        }
        //timer.pause();
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
        //timer.play();
        if(mode.equals("time")){
            timer.play();
        }
        root.getChildren().remove(pausePane);
    }

    private Text createText(String text, Color color) {
        Text t = new Text(text);
        t.setFont(Font.font("Arial", FontWeight.BOLD, 20 * size()));
        t.setFill(color);
        return t;
    }
    private Text createSizedText(String text, Color color, double size) {
        Text t = new Text(text);
        t.setFont(Font.font("Arial", FontWeight.BOLD, size * size()));
        t.setFill(color);
        return t;
    }
}
