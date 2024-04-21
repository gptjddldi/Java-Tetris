package com.example.javatetris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
            nextTetromino = TetrominoFactory.generateSpecialTetromino();
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
        } else {
            fixTetromino();
        }
    }

    public void rotateClockwise() {
        Tetromino rotated = currentTetromino.rotateClockwise();
        if (canMove(currentX, currentY, rotated)) {
            currentTetromino = rotated;
        }
    }

    private void fixTetromino() {
        char[][] shape = currentTetromino.shape();
        Color color = currentTetromino.color();
        int num = 0;

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 'N') {
                    charBoard[currentY + i][currentX + j] = shape[i][j];
                    colorBoard[currentY + i][currentX + j] = color;
                }
            }
        }
        score += 10;

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            boolean lineCleared = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (charBoard[y][x] == 'N') {
                    lineCleared = false;
                    break;
                }
            }
            if (lineCleared) {
                num++;
            }
        }

        clearLines(num);
        spawnNewTetromino();
    }

    private void clearLines(int num) {
        clearedLines += num;
        for (int y = BOARD_HEIGHT - 1; y >= 0; y--) {
            boolean lineFull = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (charBoard[y][x] == 'N') {
                    lineFull = false;
                    break;
                }
            }
            if (lineFull) {
                for (int yy = y; yy > 0; yy--) {
                    System.arraycopy(charBoard[yy - 1], 0, charBoard[yy], 0, BOARD_WIDTH);
                }
                for (int x = 0; x < BOARD_WIDTH; x++) {
                    charBoard[0][x] = 'N';
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
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 'N') {
                    int x = newX + j;
                    int y = newY + i;
                    if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT) {
                        return false;
                    }
                    if (y >= 0 && charBoard[y][x] != 'N') {
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
}