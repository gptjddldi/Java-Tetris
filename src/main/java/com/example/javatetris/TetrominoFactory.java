package com.example.javatetris;

import com.example.SaveFile.SaveSetting;
import javafx.scene.paint.Color;

public class TetrominoFactory {
    private static String colorSetting = SaveSetting.loadOneSettingFromFile(7);
    private static Color CYAN = colorSetting.equals("on") ? Color.rgb(0, 204, 204) : Color.rgb(0, 255, 255);
    public static Color BLUE = colorSetting.equals("on") ? Color.rgb(0, 102, 204) : Color.rgb(0, 0, 255);
    public static Color ORANGE = colorSetting.equals("on") ? Color.rgb(255, 204, 0) : Color.rgb(255, 165, 0);
    public static Color YELLOW = colorSetting.equals("on") ? Color.rgb(255, 153, 51) : Color.rgb(255, 255, 0);
    public static Color GREEN = colorSetting.equals("on") ? Color.rgb(0, 204, 102) : Color.rgb(0, 128, 0);
    public static Color PURPLE = colorSetting.equals("on") ? Color.rgb(204, 102, 255) : Color.rgb(128, 0, 128);
    public static Color RED = colorSetting.equals("on") ? Color.rgb(204, 51, 0) : Color.rgb(255, 0, 0);
    public static Color WHITE = Color.WHITE;

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
}
