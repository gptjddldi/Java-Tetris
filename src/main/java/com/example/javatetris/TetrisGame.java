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
    private final char[][] charBoard; // Represents the game board

    private final Color[][] colorBoard; // Represents the game board
    private int currentX, currentY; // Current position of the active Tetromino
    private Tetromino currentTetromino; // Current Tetromino shape
    private Tetromino nextTetromino; // Current Tetromino shape
    private final Timeline gameLoop;
    private boolean gameOver = false;
    private int score;

    public TetrisGame() {
        score = 0;
        // Initialize the game board
        charBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];
        for(int i = 0; i < charBoard.length; i++) {
            Arrays.fill(charBoard[i], 'N');
        }

        colorBoard = new Color[BOARD_HEIGHT][BOARD_WIDTH];
        // Initialize other game variables
        // Set up the first Tetromino
        spawnNewTetromino();

        // Game loop
        gameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> moveDown()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }


    private void spawnNewTetromino() {
        currentTetromino = Objects.requireNonNullElseGet(nextTetromino, Tetromino::getRandomTetromino);
        nextTetromino = Tetromino.getRandomTetromino();

        currentX = BOARD_WIDTH / 2 - currentTetromino.getWidth() / 2;
        currentY = 0;
        if (!canMove(currentX, currentY, currentTetromino)) {
            // Game over condition: if new Tetromino cannot be placed, game ends
            // You can handle game over logic here
            gameOver = true;
            gameLoop.stop();
        }
    }

    // Method to move the current Tetromino left
    public void moveLeft() {
        if (canMove(currentX - 1, currentY, currentTetromino)) {
            currentX--;
        }
    }

    // Method to move the current Tetromino right
    public void moveRight() {
        if (canMove(currentX + 1, currentY, currentTetromino)) {
            currentX++;
        }
    }

    // Method to move the current Tetromino down (simulate gravity)
    public void moveDown() {
        if (canMove(currentX, currentY + 1, currentTetromino)) {
            currentY++;
        } else {
            // If the Tetromino cannot move down further, fix it onto the board
            fixTetromino();
        }
    }

    // Method to rotate the current Tetromino clockwise
    public void rotateClockwise() {
        Tetromino rotated = currentTetromino.rotateClockwise();
        if (canMove(currentX, currentY, rotated)) {
            currentTetromino = rotated;
        }
    }

    // Method to fix the current Tetromino onto the board
    private void fixTetromino() {
        char[][] shape = currentTetromino.getShape();
        Color color = currentTetromino.getColor();
        int linesCleared = 0;

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
            boolean lineCleared = true; // Assume line is cleared initially
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (charBoard[y][x] == 'N') { // If any cell in the row is not occupied
                    lineCleared = false; // Line is not cleared
                    break; // No need to check further in this row
                }
            }
            if (lineCleared) {
                linesCleared++;
                // You may choose to break here if you only want to count one line cleared per fix
            }
        }

        clearLines(linesCleared);
        spawnNewTetromino();
    }

    private void clearLines(int linesCleared) {
        for (int y = BOARD_HEIGHT - 1; y >= 0; y--) {
            boolean lineFull = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (charBoard[y][x] == 'N') {
                    lineFull = false;
                    break;
                }
            }
            if (lineFull) {
                // Move all lines above down by one
                for (int yy = y; yy > 0; yy--) {
                    System.arraycopy(charBoard[yy - 1], 0, charBoard[yy], 0, BOARD_WIDTH);
                }
                // Clear top line
                for (int x = 0; x < BOARD_WIDTH; x++) {
                    charBoard[0][x] = 'N';
                }
                y++; // Check the same line again
            }
        }

        switch (linesCleared) {
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

    // Method to check if the Tetromino can move to a new position
    private boolean canMove(int newX, int newY, Tetromino tetromino) {
        char[][] shape = tetromino.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 'N') {
                    int x = newX + j;
                    int y = newY + i;
                    // Check if the Tetromino is within bounds
                    if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT) {
                        return false;
                    }
                    // Check for collisions with existing blocks on the board
                    if (y >= 0 && charBoard[y][x] != 'N') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Method to get the current state of the game board
    // 여기서 Color 도 같이 전달하면 좋을듯?
    public char[][] getBoardChar() {
        char[][] copy = new char[BOARD_HEIGHT][BOARD_WIDTH];

        // Copy current board state
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            copy[i] = Arrays.copyOf(charBoard[i], BOARD_WIDTH);
        }

        // Overlay current Tetromino onto the board state
        char[][] shape = currentTetromino.getShape();
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

        // Copy current board state
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            copy[i] = Arrays.copyOf(colorBoard[i], BOARD_WIDTH);
        }

        // Overlay current Tetromino onto the board state
        char[][] shape = currentTetromino.getShape();
        Color color = currentTetromino.getColor();
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