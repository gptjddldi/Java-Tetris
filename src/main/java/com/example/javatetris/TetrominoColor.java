package com.example.javatetris;

import javafx.scene.paint.Color;

public class TetrominoColor {
    private static final Color[] defaultColors = {
            Color.CYAN,     // I_SHAPE
            Color.BLUE,     // J_SHAPE
            Color.ORANGE,   // L_SHAPE
            Color.YELLOW,   // O_SHAPE
            Color.GREEN,    // S_SHAPE
            Color.PURPLE,   // T_SHAPE
            Color.RED       // Z_SHAPE
    };

    private static final Color[] colorBlindColors = {
            Color.rgb(0, 255, 255),    // I_SHAPE (Cyan for Protanopia)
            Color.rgb(0, 0, 255),      // J_SHAPE (Blue for Protanopia)
            Color.rgb(255, 140, 0),    // L_SHAPE (DarkOrange for Protanopia)
            Color.rgb(255, 255, 0),    // O_SHAPE (Yellow for Protanopia)
            Color.rgb(107, 142, 35),   // S_SHAPE (OliveDrab for Protanopia)
            Color.rgb(148, 0, 211),    // T_SHAPE (DarkViolet for Protanopia)
            Color.rgb(255, 0, 0)       // Z_SHAPE (Red for Protanopia)
    };

    // 기본 색상으로 테트로미노 색상 설정
    public static void setDefaultColors() {
        TetrominoType[] types = TetrominoType.values();
        for (int i = 0; i < types.length; i++) {
            types[i].setColor(defaultColors[i]);
        }
    }

    // 색맹 모드용 색상으로 테트로미노 색상 설정
    public static void setColorsForColorBlindMode() {
        TetrominoType[] types = TetrominoType.values();
        for (int i = 0; i < types.length; i++) {
            types[i].setColor(colorBlindColors[i]);
        }
    }

    // 테트로미노 색상을 초기 상태로 재설정
    public static void resetColors() {
        setDefaultColors();
    }
}

