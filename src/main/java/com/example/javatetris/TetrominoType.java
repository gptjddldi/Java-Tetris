package com.example.javatetris;

import com.example.SaveFile.SaveSetting;
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
        String colorSetting = SaveSetting.loadColorSettingFromFile();
        switch (this) {
            case I_SHAPE:
                return (colorSetting.equals("on")) ? Color.rgb(0, 204, 204) : Color.rgb(0, 255, 255); // Yellow or Cyan
            case J_SHAPE:
                return (colorSetting.equals("on")) ? Color.rgb(0, 102, 204) : Color.rgb(0, 0, 255); // Yellow or Blue
            case L_SHAPE:
                return (colorSetting.equals("on")) ? Color.rgb(255, 204, 0) : Color.rgb(255, 165, 0); // Yellow or Orange
            case O_SHAPE:
                return (colorSetting.equals("on")) ? Color.rgb(255, 153, 51) : Color.rgb(255, 255, 0); // Yellow or Yellow
            case S_SHAPE:
                return (colorSetting.equals("on")) ? Color.rgb(0, 204, 102) : Color.rgb(0, 128, 0); // Yellow or Green
            case T_SHAPE:
                return (colorSetting.equals("on")) ? Color.rgb(204, 102, 255) : Color.rgb(128, 0, 128); // Yellow or Purple
            case Z_SHAPE:
                return (colorSetting.equals("on")) ? Color.rgb(204, 51, 0) : Color.rgb(255, 0, 0); // Yellow or Red
            default:
                return Color.WHITE;
        }
    }
}