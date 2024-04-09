package com.example.javatetris;

import javafx.scene.paint.Color;

public enum TetrominoType {
    I_SHAPE(new char[][]{{'O', 'O', 'O', 'O'}}, Color.CYAN),
    J_SHAPE(new char[][]{{'O', 'N', 'N'}, {'O', 'O', 'O'}}, Color.BLUE),
    L_SHAPE(new char[][]{{'N', 'N', 'O'}, {'O', 'O', 'O'}}, Color.ORANGE),
    O_SHAPE(new char[][]{{'O', 'O'}, {'O', 'O'}}, Color.YELLOW),
    S_SHAPE(new char[][]{{'N', 'O', 'O'}, {'O', 'O', 'N'}}, Color.GREEN),
    T_SHAPE(new char[][]{{'N', 'O', 'N'}, {'O', 'O', 'O'}}, Color.PURPLE),
    Z_SHAPE(new char[][]{{'O', 'O', 'N'}, {'N', 'O', 'O'}}, Color.RED);


    private final char[][] shape;
    private final Color color;

    TetrominoType(char[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public char[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }
}