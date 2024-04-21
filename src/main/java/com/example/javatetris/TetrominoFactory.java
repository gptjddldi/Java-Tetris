package com.example.javatetris;

import javafx.scene.paint.Color;

public class TetrominoFactory {

    public static Tetromino generateSpecialTetromino() {
        int typeIndex = (int) (Math.random() * SpecialTetrominoType.values().length);
        SpecialTetrominoType type = SpecialTetrominoType.values()[typeIndex];
        return switch (type) {
            case HEAVY_SHAPE ->
                    new Tetromino(SpecialTetrominoType.HEAVY_SHAPE, new char[][]{{'O', 'O'}, {'O', 'O'}}, Color.GRAY);
            case HORIZON_SHAPE ->
                    new Tetromino(SpecialTetrominoType.HORIZON_SHAPE, new char[][]{{'O', 'O', 'O', 'O'}}, Color.LIGHTBLUE);
            case VERTICAL_SHAPE ->
                    new Tetromino(SpecialTetrominoType.VERTICAL_SHAPE, new char[][]{{'O'}, {'O'}, {'O'}, {'O'}}, Color.LIGHTGREEN);
            case BOMB_SHAPE -> new Tetromino(SpecialTetrominoType.BOMB_SHAPE, new char[][]{{'O'}}, Color.BISQUE);
            case CROSS_SHAPE ->
                    new Tetromino(SpecialTetrominoType.CROSS_SHAPE, new char[][]{{'N', 'O', 'N'}, {'O', 'O', 'O'}, {'N', 'O', 'N'}}, Color.LIGHTPINK);
        };
    }

    public static Tetromino generateTetromino() {
        int typeIndex = (int) (Math.random() * BasicTetrominoType.values().length);
        BasicTetrominoType type = BasicTetrominoType.values()[typeIndex];

        return switch (type) {
            case I_SHAPE -> new Tetromino(BasicTetrominoType.I_SHAPE, new char[][]{{'O', 'O', 'O', 'O'}}, Color.CYAN);
            case J_SHAPE ->
                    new Tetromino(BasicTetrominoType.J_SHAPE, new char[][]{{'O', 'N', 'N'}, {'O', 'O', 'O'}}, Color.BLUE);
            case L_SHAPE ->
                    new Tetromino(BasicTetrominoType.L_SHAPE, new char[][]{{'N', 'N', 'O'}, {'O', 'O', 'O'}}, Color.ORANGE);
            case O_SHAPE ->
                    new Tetromino(BasicTetrominoType.O_SHAPE, new char[][]{{'O', 'O'}, {'O', 'O'}}, Color.YELLOW);
            case S_SHAPE ->
                    new Tetromino(BasicTetrominoType.S_SHAPE, new char[][]{{'N', 'O', 'O'}, {'O', 'O', 'N'}}, Color.GREEN);
            case T_SHAPE ->
                    new Tetromino(BasicTetrominoType.T_SHAPE, new char[][]{{'N', 'O', 'N'}, {'O', 'O', 'O'}}, Color.PURPLE);
            case Z_SHAPE ->
                    new Tetromino(BasicTetrominoType.Z_SHAPE, new char[][]{{'O', 'O', 'N'}, {'N', 'O', 'O'}}, Color.RED);
        };
    }
}
