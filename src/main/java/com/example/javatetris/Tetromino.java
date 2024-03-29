package com.example.javatetris;

import java.util.Random;

public class Tetromino {
    private final boolean[][] shape;
//test
    private Tetromino(boolean[][] shape) {
        this.shape = shape;
    }

    public static Tetromino getRandomTetromino() {
        Random random = new Random();
        TetrominoType type = TetrominoType.values()[random.nextInt(TetrominoType.values().length)];
        return new Tetromino(type.getShape());
    }

    public Tetromino rotateClockwise() {
        boolean[][] rotated = new boolean[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                rotated[j][shape.length - 1 - i] = shape[i][j];
            }
        }
        return new Tetromino(rotated);
    }

    public boolean[][] getShape() {
        return shape;
    }

    public int getWidth() {
        return shape[0].length;
    }
}