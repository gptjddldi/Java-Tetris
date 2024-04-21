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

    public static Tetromino generateTetromino(Difficulty difficulty) {
        BasicTetrominoType[] types = BasicTetrominoType.values();
        double[] weights = new double[types.length];
        double totalWeight = 0.0;

        for (int i = 0; i < types.length; i++) {
            weights[i] = 1.0;
        }

        switch (difficulty) {
            case EASY:
                weights[BasicTetrominoType.I_SHAPE.ordinal()] = 1.20;
                break;
            case MEDIUM:
                break;
            case HARD:
                weights[BasicTetrominoType.I_SHAPE.ordinal()] = 0.80;
                break;
        }

        for (double weight : weights) {
            totalWeight += weight;
        }

        double value = Math.random() * totalWeight;
        double cumulativeWeight = 0.0;
        int selectedTypeIndex = 0;

        for (int i = 0; i < weights.length; i++) {
            cumulativeWeight += weights[i];
            if (cumulativeWeight > value) {
                selectedTypeIndex = i;
                break;
            }
        }

        BasicTetrominoType selectedType = types[selectedTypeIndex];


        return switch (selectedType) {
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
