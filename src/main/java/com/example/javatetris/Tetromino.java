package com.example.javatetris;

import javafx.scene.paint.Color;

public record Tetromino(TetrominoType tetrominoType, char[][] shape, Color color) {

    public Tetromino rotateClockwise() {
        char[][] rotated = new char[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                rotated[j][shape.length - 1 - i] = shape[i][j];
            }
        }
        return new Tetromino(tetrominoType, rotated, color);
    }

    public Color getColor() {
        return color;
    }

    public int getWidth() {
        return shape[0].length;
    }
}