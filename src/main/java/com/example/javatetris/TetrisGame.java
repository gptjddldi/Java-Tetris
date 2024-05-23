package com.example.javatetris;

import com.example.SaveFile.SaveSetting;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Objects;

public class TetrisGame {
    protected final int BOARD_WIDTH = 10;
    protected final int BOARD_HEIGHT = 20;
    protected final char[][] charBoard;

    protected final Color[][] colorBoard;
    protected int currentX, currentY;
    protected Tetromino currentTetromino;
    private Tetromino nextTetromino;
    private Timeline gameLoop;
    private boolean gameOver = false;
    protected int score = 0;
    private int targetLineCount = 0;
    protected int clearedLines = 0;
    protected int totalClearedLines = 0;
    private TetrominoFactory tetrominoFactory;
    private String mode;
    protected Difficulty difficulty;

    private boolean sideFixed = false;

    public TetrisGame(String mode) {
        this.mode = mode;
        String colorSetting = SaveSetting.loadOneSettingFromFile(12);
        tetrominoFactory = new TetrominoFactory(colorSetting);
        difficulty = Difficulty.valueOf(SaveSetting.loadOneSettingFromFile(13));

        charBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];
        for (char[] chars : charBoard) {
            Arrays.fill(chars, 'N');
        }
        colorBoard = new Color[BOARD_HEIGHT][BOARD_WIDTH];

        spawnNewTetromino();
        setupGameLoop();
    }

    protected void setupGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }

        double interval = calculateInterval();

        gameLoop = new Timeline(new KeyFrame(Duration.seconds(interval), event -> moveDown()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private double calculateInterval() {
        double speedFactor = Math.pow(1.02, totalClearedLines);
        return difficulty.getBaseInterval() / speedFactor;
    }


    protected void spawnNewTetromino() {
        generateTetromino();
        currentX = BOARD_WIDTH / 2 - currentTetromino.getWidth() / 2;
        currentY = 0;
        if (!canMove(currentX, currentY, currentTetromino)) {
            gameOver = true;
            gameLoop.stop();
        }
    }

    public void moveLeft() {
        if (canMove(currentX - 1, currentY, currentTetromino) && !sideFixed) {
            currentX--;
        }
    }

    public void moveRight() {
        if (canMove(currentX + 1, currentY, currentTetromino) && !sideFixed) {
            currentX++;
        }
    }

    public void moveDown() {
        if (canMove(currentX, currentY + 1, currentTetromino)) {
            currentY++;
            score += difficulty.getBasePoint() / 6;
            if(currentTetromino.tetrominoType() == SpecialTetrominoType.HEAVY_SHAPE) {
                int cnt = clearCell(currentX, currentY, currentTetromino);
                if (cnt > 0) {
                    sideFixed = true;
                }
            }
        } else {
            fixTetromino();
            clearLines();
            spawnNewTetromino();
        }
    }

    public void moveDownAll() {
        if(currentTetromino.tetrominoType() == SpecialTetrominoType.HEAVY_SHAPE || currentTetromino.tetrominoType() == SpecialTetrominoType.BOMB_SHAPE) {
            return;
        }
        while (canMove(currentX, currentY + 1, currentTetromino)) {
            currentY++;
            score += difficulty.getBasePoint() / 6;
        }
        fixTetromino();
        clearLines();
        spawnNewTetromino();

    }


    public void rotateClockwise() {
        Tetromino rotated = currentTetromino.rotateClockwise();
        if (canMove(currentX, currentY, rotated) && currentTetromino.tetrominoType() != SpecialTetrominoType.HEAVY_SHAPE) {
            currentTetromino = rotated;
        }
    }

    private void generateTetromino() {
        currentTetromino = Objects.requireNonNullElseGet(nextTetromino, () -> tetrominoFactory.generateTetromino(difficulty));
        if (mode.equals("item") && clearedLines >= 1) {
            nextTetromino = tetrominoFactory.generateSpecialTetromino(difficulty);
            clearedLines -= 1;
        } else {
            nextTetromino = tetrominoFactory.generateTetromino(difficulty);
        }
    }

    protected void fixTetromino() {
        sideFixed = false;
        char[][] shape = currentTetromino.shape();
        Color color = currentTetromino.color();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 'N') {
                    charBoard[currentY + i][currentX + j] = shape[i][j];
                    colorBoard[currentY + i][currentX + j] = color;
                }
            }
        }

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            boolean lineCleared = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (charBoard[y][x] == 'N') {
                    lineCleared = false;
                    break;
                }
            }
            if (lineCleared) {
                targetLineCount++;
            }
        }

        if (currentTetromino.tetrominoType() == SpecialTetrominoType.BOMB_SHAPE) {
            handleBombShape();
        }

        if(currentTetromino.tetrominoType() == SpecialTetrominoType.CROSS_SHAPE) {
            handleCrossShape();
        }

        // LINE_SHAPE 타입일 경우 'L'이 포함된 행 삭제
        if (currentTetromino.tetrominoType() == SpecialTetrominoType.LINE_SHAPE) {
            handleLineShape();
        }

        // VERTICAL_SHAPE 타입일 경우 'V'가 포함된 열 삭제
        if (currentTetromino.tetrominoType() == SpecialTetrominoType.VERTICAL_SHAPE) {
            handleVerticalShape();
        }
    }

    protected void clearLines() {

        clearedLines += targetLineCount;
        totalClearedLines += targetLineCount;
        for (int y = BOARD_HEIGHT - 1; y >= 0; y--) {
            boolean lineFull = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (charBoard[y][x] == 'N') {
                    lineFull = false;
                    break;
                }
            }
            if (lineFull) {
                clearRow(y);
                for (int x = 0; x < BOARD_WIDTH; x++) {
                    charBoard[0][x] = 'N';
                }
                y++;
            }
        }
        score += targetLineCount * targetLineCount * difficulty.getBasePoint();
        targetLineCount = 0;

        setupGameLoop();
    }

    public int getScore() {
        return score;
    }

    private boolean canMove(int newX, int newY, Tetromino tetromino) {
        char[][] shape = tetromino.shape();
        boolean isHeavy = tetromino.tetrominoType() == SpecialTetrominoType.HEAVY_SHAPE;
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 'N') {
                    int x = newX + j;
                    int y = newY + i;

                    if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT) {
                        return false;
                    }
                    if (!isHeavy && y >= 0 && charBoard[y][x] != 'N') {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public char[][] getBoardChar() {
        char[][] copy = new char[BOARD_HEIGHT][BOARD_WIDTH];

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            copy[i] = Arrays.copyOf(charBoard[i], BOARD_WIDTH);
        }

        char[][] shape = currentTetromino.shape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 'N') {
                    int x = currentX + j;
                    int y = currentY + i;
                    if (y >= 0 && y < BOARD_HEIGHT && x >= 0 && x < BOARD_WIDTH) {
                        copy[y][x] = shape[i][j];
                    }
                }
            }
        }
        return copy;
    }

    public Color[][] getBoardColor() {
        Color[][] copy = new Color[BOARD_HEIGHT][BOARD_WIDTH];

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            copy[i] = Arrays.copyOf(colorBoard[i], BOARD_WIDTH);
        }

        char[][] shape = currentTetromino.shape();
        Color color = currentTetromino.color();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 'N') {
                    int x = currentX + j;
                    int y = currentY + i;
                    if (y >= 0 && y < BOARD_HEIGHT && x >= 0 && x < BOARD_WIDTH) {
                        copy[y][x] = color;
                    }
                }
            }
        }
        return copy;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void pauseGame() {
        gameLoop.pause();
    }

    public void resumeGame() {
        gameLoop.play();
    }

    public Tetromino getNextTetromino() {
        return nextTetromino;
    }

    private void handleBombShape() {
        for (int y = Math.max(0, currentY - 1); y <= Math.min(BOARD_HEIGHT - 1, currentY + 1); y++) {
            for (int x = Math.max(0, currentX - 1); x <= Math.min(BOARD_WIDTH - 1, currentX + 1); x++) {
                if (charBoard[y][x] != 'N') {
                    score += difficulty.getBasePoint() / 6;
                }
                charBoard[y][x] = 'N';
            }
        }
    }

    private void handleCrossShape() {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (charBoard[y][x] == 'C'){
                    clearRow(y);
                    clearColumn(x);
                    break;

                }
            }
        }
    }

    private void handleLineShape() {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (charBoard[y][x] == 'L') {
                    clearRow(y);
                    break;
                }
            }
        }
    }

    private void handleVerticalShape() {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                if (charBoard[y][x] == 'V') {
                    clearColumn(x);
                    break;
                }
            }
        }
    }

    private int clearCell(int x, int y, Tetromino tetromino) {
        int cnt = 0;
        char[][] shape = tetromino.shape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 'N') {
                    int targetX = x + j;
                    int targetY = y + i;

                    if (targetX >= 0 && targetX < BOARD_WIDTH && targetY >= 0 && targetY < BOARD_HEIGHT && charBoard[targetY][targetX] != 'N') {
                        charBoard[targetY][targetX] = 'N';
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    protected void clearRow(int rowIndex) {
        for (int yy = rowIndex; yy > 0; yy--) {
            System.arraycopy(charBoard[yy - 1], 0, charBoard[yy], 0, BOARD_WIDTH);
            System.arraycopy(colorBoard[yy - 1], 0, colorBoard[yy], 0, BOARD_WIDTH);
        }
    }
    private void clearColumn(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < BOARD_WIDTH) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                if (charBoard[y][columnIndex] != 'N') {
                    score += difficulty.getBasePoint() / 6;
                }
                charBoard[y][columnIndex] = 'N';
            }
        }
    }
}