package com.example.javatetris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Arrays;

public class TetrisGame {
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final boolean[][] board; // Represents the game board
    private int currentX, currentY; // Current position of the active Tetromino
    private Tetromino currentTetromino; // Current Tetromino shape
    private final Timeline gameLoop;

    public TetrisGame() {
        // Initialize the game board
        board = new boolean[BOARD_HEIGHT][BOARD_WIDTH];
        // Initialize other game variables
        // Set up the first Tetromino
        spawnNewTetromino();

        // Game loop
        gameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> moveDown()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void spawnNewTetromino() {
        currentTetromino = Tetromino.getRandomTetromino();
        currentX = BOARD_WIDTH / 2 - currentTetromino.getWidth() / 2;
        currentY = 0;
        if (!canMove(currentX, currentY, currentTetromino)) {
            // Game over condition: if new Tetromino cannot be placed, game ends
            // You can handle game over logic here
            System.out.println("Game Over");
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
        boolean[][] shape = currentTetromino.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    board[currentY + i][currentX + j] = true;
                }
            }
        }
        clearLines();
        spawnNewTetromino();
    }

    private void clearLines() {
        for (int y = BOARD_HEIGHT - 1; y >= 0; y--) {
            boolean lineFull = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (!board[y][x]) {
                    lineFull = false;
                    break;
                }
            }
            if (lineFull) {
                // Move all lines above down by one
                for (int yy = y; yy > 0; yy--) {
                    System.arraycopy(board[yy - 1], 0, board[yy], 0, BOARD_WIDTH);
                }
                // Clear top line
                for (int x = 0; x < BOARD_WIDTH; x++) {
                    board[0][x] = false;
                }
                y++; // Check the same line again
            }
        }
    }

    // Method to check if the Tetromino can move to a new position
    private boolean canMove(int newX, int newY, Tetromino tetromino) {
        boolean[][] shape = tetromino.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    int x = newX + j;
                    int y = newY + i;
                    // Check if the Tetromino is within bounds
                    if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT) {
                        return false;
                    }
                    // Check for collisions with existing blocks on the board
                    if (y >= 0 && board[y][x]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Method to get the current state of the game board
    public boolean[][] getBoardState() {
        boolean[][] copy = new boolean[BOARD_HEIGHT][BOARD_WIDTH];

        // Copy current board state
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            copy[i] = Arrays.copyOf(board[i], BOARD_WIDTH);
        }

        // Overlay current Tetromino onto the board state
        boolean[][] shape = currentTetromino.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    int x = currentX + j;
                    int y = currentY + i;
                    if (y >= 0 && y < BOARD_HEIGHT && x >= 0 && x < BOARD_WIDTH) {
                        copy[y][x] = true;
                    }
                }
            }
        }
        return copy;
    }
}