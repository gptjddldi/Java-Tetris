package com.example.javatetris;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class TetrominoFactory {

    private  Color CYAN = Color.rgb(0, 255, 255);
    private  Color BLUE = Color.rgb(0, 0, 255);
    private  Color ORANGE = Color.rgb(255, 165, 0);
    private  Color YELLOW = Color.rgb(255, 255, 0);
    private  Color GREEN = Color.rgb(0, 128, 0);
    private  Color PURPLE = Color.rgb(128, 0, 128);
    private  Color RED = Color.rgb(255, 0, 0);


    public TetrominoFactory(String colorSetting) {
        if(colorSetting.equals("on")){
            CYAN = Color.rgb(0, 204, 204);
            BLUE = Color.rgb(0, 0, 255);
            ORANGE = Color.rgb(255, 204, 0);
            YELLOW = Color.rgb(255, 153, 51);
            GREEN = Color.rgb(0, 204, 102);
            PURPLE = Color.rgb(204, 102, 255);
            RED = Color.rgb(204, 51, 0);
        }
    }

    public  Tetromino generateSpecialTetromino(Difficulty difficulty) {
        int typeIndex = (int) (Math.random() * SpecialTetrominoType.values().length);
        SpecialTetrominoType type = SpecialTetrominoType.values()[typeIndex];
        return switch (type) {
            case HEAVY_SHAPE ->
                    new Tetromino(SpecialTetrominoType.HEAVY_SHAPE, new char[][]{{'O', 'O'}, {'O', 'O', 'O', 'O'}}, Color.GRAY);
            case BOMB_SHAPE -> new Tetromino(SpecialTetrominoType.BOMB_SHAPE, new char[][]{{'B'}}, Color.BISQUE);
            case CROSS_SHAPE ->
                    new Tetromino(SpecialTetrominoType.CROSS_SHAPE, new char[][]{{'N', 'O', 'N'}, {'O', 'O', 'O'}, {'N', 'O', 'N'}}, Color.LIGHTPINK);
            case LINE_SHAPE -> generateShapedTetromino(generateTetromino(difficulty), SpecialTetrominoType.LINE_SHAPE);
            case VERTICAL_SHAPE -> generateShapedTetromino(generateTetromino(difficulty), SpecialTetrominoType.VERTICAL_SHAPE);
        };
    }

    public Tetromino generateTetromino(Difficulty difficulty) {
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
            case I_SHAPE -> new Tetromino(BasicTetrominoType.I_SHAPE, new char[][]{{'O', 'O', 'O', 'O'}}, CYAN);

            case J_SHAPE ->
                    new Tetromino(BasicTetrominoType.J_SHAPE, new char[][]{{'O', 'N', 'N'}, {'O', 'O', 'O'}}, BLUE);
            case L_SHAPE ->
                    new Tetromino(BasicTetrominoType.L_SHAPE, new char[][]{{'N', 'N', 'O'}, {'O', 'O', 'O'}}, ORANGE);
            case O_SHAPE ->
                    new Tetromino(BasicTetrominoType.O_SHAPE, new char[][]{{'O', 'O'}, {'O', 'O'}}, YELLOW);
            case S_SHAPE ->
                    new Tetromino(BasicTetrominoType.S_SHAPE, new char[][]{{'N', 'O', 'O'}, {'O', 'O', 'N'}}, GREEN);
            case T_SHAPE ->
                    new Tetromino(BasicTetrominoType.T_SHAPE, new char[][]{{'N', 'O', 'N'}, {'O', 'O', 'O'}}, PURPLE);
            case Z_SHAPE ->
                    new Tetromino(BasicTetrominoType.Z_SHAPE, new char[][]{{'O', 'O', 'N'}, {'N', 'O', 'O'}}, RED);
        };
    }

    private Tetromino generateShapedTetromino(Tetromino original, TetrominoType type) {
        char ch = 'N';
        if (type == SpecialTetrominoType.LINE_SHAPE) {
            ch = 'L';
        } else if (type == SpecialTetrominoType.VERTICAL_SHAPE) {
            ch = 'V';
        }
        char[][] modifiedShape = original.shape().clone();
        int rows = modifiedShape.length;
        int cols = modifiedShape[0].length;

        List<int[]> oBlocks = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (modifiedShape[i][j] == 'O') {
                    oBlocks.add(new int[]{i, j});
                }
            }
        }

        if (!oBlocks.isEmpty()) {
            int[] selectedBlock = oBlocks.get((int) (Math.random() * oBlocks.size()));
            modifiedShape[selectedBlock[0]][selectedBlock[1]] = ch;
        }

        return new Tetromino(type, modifiedShape, original.color());
    }
}
