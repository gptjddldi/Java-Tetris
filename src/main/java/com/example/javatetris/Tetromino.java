package com.example.javatetris;

import javafx.scene.paint.Color;

import java.util.Random;

public class Tetromino {
    private final char[][] shape;
    private final Color color;

    private Tetromino(char[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public static Tetromino getRandomTetromino() {
        Random random = new Random();
        TetrominoType type = TetrominoType.values()[random.nextInt(TetrominoType.values().length)];
        return new Tetromino(type.getShape(), type.getColor());
    }

    public Tetromino rotateClockwise() {
        char[][] rotated = new char[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                rotated[j][shape.length - 1 - i] = shape[i][j];
            }
        }
        return new Tetromino(rotated, color);
    }

    public char[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int getWidth() {
        return shape[0].length;
    }
}