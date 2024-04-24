package com.example.javatetris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TetrisGame {
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final char[][] charBoard;

    private final Color[][] colorBoard;
    private int currentX, currentY;
    private Tetromino currentTetromino;
    private Tetromino nextTetromino;
    private final Timeline gameLoop;
    private boolean gameOver = false;
    private int score = 0;
    private int clearedLines = 0;
    private boolean readyToFix = false;
    int clearNum = 0;

    public TetrisGame() {
        charBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];
        for (char[] chars : charBoard) {
            Arrays.fill(chars, 'N');
        }

        colorBoard = new Color[BOARD_HEIGHT][BOARD_WIDTH];
        spawnNewTetromino();

        gameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> moveDown()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }


    private void spawnNewTetromino() {
        currentTetromino = Objects.requireNonNullElseGet(nextTetromino, () -> TetrominoFactory.generateTetromino(Difficulty.EASY));

        if(clearedLines >= 1) {
            nextTetromino = TetrominoFactory.generateSpecialTetromino(Difficulty.EASY);
            clearedLines -= 1;
        } else {
            nextTetromino = TetrominoFactory.generateTetromino(Difficulty.EASY);
        }

        currentX = BOARD_WIDTH / 2 - currentTetromino.getWidth() / 2;
        currentY = 0;
        if (!canMove(currentX, currentY, currentTetromino)) {
            gameOver = true;
            gameLoop.stop();
        }
    }

    public void moveLeft() {
        if (canMove(currentX - 1, currentY, currentTetromino)) {
            currentX--;
        }
    }

    public void moveRight() {
        if (canMove(currentX + 1, currentY, currentTetromino)) {
            currentX++;
        }
    }

    public void moveDown() {
        if (canMove(currentX, currentY + 1, currentTetromino)) {
            currentY++;
            if(currentTetromino.tetrominoType() == SpecialTetrominoType.HEAVY_SHAPE) {
                clearCell(currentX, currentY, currentTetromino);
            }
        } else {
            if (!readyToFix) {
                readyToFix = true; // fixTetromino 실행 준비 완료
                new Timeline(new KeyFrame(Duration.seconds(0.1), ae1 ->{
                    fixTetrominoAndScore(()->{
                        turnLinesWhite();
                    });
                })).play();

                new Timeline(new KeyFrame(Duration.seconds(0.9), ae2 -> {
                    clearLinesAndSpawnNewTetromino(clearNum);
                    readyToFix = false;
                })).play();
            }
        }
    }

    public void rotateClockwise() {
        Tetromino rotated = currentTetromino.rotateClockwise();
        if (canMove(currentX, currentY, rotated)) {
            currentTetromino = rotated;
        }
    }

    private void fixTetrominoAndScore(Runnable callback) {
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
        score += 10;
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

        callback.run();
    }

    private void turnLinesWhite() {
        clearNum = 0;

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            boolean lineCleared = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (charBoard[y][x] == 'N') {
                    lineCleared = false;
                    break;
                }
            }
            if (lineCleared) {
                clearNum++;
                for (int x = 0; x < BOARD_WIDTH; x++) {
                    charBoard[y][x] = 'O';
                    colorBoard[y][x] = Color.WHITE;
                }
            }
        }
    }

    private void clearLinesAndSpawnNewTetromino(int num) {

        clearLines(num); // 이전에 정의된 clearLines 메소드를 사용하여 줄을 삭제
        spawnNewTetromino(); // 새로운 테트로미노 생성
    }

    private void clearLines(int num) {
        clearedLines += num;
        for (int y = BOARD_HEIGHT - 1; y >= 0; y--) {
            boolean lineFullOrWhite = true;
            //boolean lineFull = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (charBoard[y][x] == 'N' || colorBoard[y][x] != Color.WHITE) {
                    lineFullOrWhite = false;
                    break;
                }
            }
            if (lineFullOrWhite) {
                for (int yy = y; yy > 0; yy--) {
                    System.arraycopy(charBoard[yy - 1], 0, charBoard[yy], 0, BOARD_WIDTH);
                    System.arraycopy(colorBoard[yy - 1], 0, colorBoard[yy], 0, BOARD_WIDTH);
                }
                for (int x = 0; x < BOARD_WIDTH; x++) {
                    charBoard[0][x] = 'N';
                    colorBoard[0][x] = null;
                }
                y++;
            }
        }

        switch (num) {
            case 1 -> score += 100;
            case 2 -> score += 200;
            case 3 -> score += 300;
            case 4 -> score += 400;
            default -> {
            }
        }
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
                charBoard[y][x] = 'N';
            }
        }
    }

    private void handleCrossShape() {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if(y == currentY || x == currentX) {
                    charBoard[y][x] = 'N';
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

    private void clearCell(int x, int y, Tetromino tetromino) {
        char[][] shape = tetromino.shape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 'N') {
                    int targetX = x + j;
                    int targetY = y + i;

                    if (targetX >= 0 && targetX < BOARD_WIDTH && targetY >= 0 && targetY < BOARD_HEIGHT) {
                        charBoard[targetY][targetX] = 'N';
                        colorBoard[targetY][targetX] = Color.WHITE;
                    }
                }
            }
        }
    }

    private void clearRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < BOARD_HEIGHT) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                charBoard[rowIndex][x] = 'N';
                colorBoard[rowIndex][x] = Color.WHITE;
            }
        }
    }
    private void clearColumn(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < BOARD_WIDTH) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                charBoard[y][columnIndex] = 'N';
                colorBoard[y][columnIndex] = Color.WHITE;
            }
        }
    }
}