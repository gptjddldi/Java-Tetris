/*
package com.example.javatetris;

import com.example.SaveFile.SaveSetting;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Objects;

public class TetrisBattleGame {
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final char[][] charBoardPlayer1;
    private final char[][] charBoardPlayer2;
    private final Color[][] colorBoardPlayer1;
    private final Color[][] colorBoardPlayer2;
    private int currentXPlayer1, currentYPlayer1;
    private int currentXPlayer2, currentYPlayer2;
    private Tetromino currentTetrominoPlayer1;
    private Tetromino currentTetrominoPlayer2;
    private Tetromino nextTetrominoPlayer1;
    private Tetromino nextTetrominoPlayer2;
    private Timeline gameLoopPlayer1;
    private Timeline gameLoopPlayer2;
    private boolean gameOverPlayer1 = false;
    private boolean gameOverPlayer2 = false;
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private int clearedLinesPlayer1 = 0;
    private int clearedLinesPlayer2 = 0;
    private int totalClearedLinesPlayer1 = 0;
    private int totalClearedLinesPlayer2 = 0;
    private TetrominoFactory tetrominoFactoryPlayer1;
    private TetrominoFactory tetrominoFactoryPlayer2;
    private String modePlayer1;
    private String modePlayer2;
    private Difficulty difficultyPlayer1;
    private Difficulty difficultyPlayer2;

    public TetrisBattleGame(String modePlayer1, String modePlayer2) {
        this.modePlayer1 = modePlayer1;
        this.modePlayer2 = modePlayer2;

        String colorSetting = SaveSetting.loadOneSettingFromFile(7);
        tetrominoFactoryPlayer1 = new TetrominoFactory(colorSetting);
        tetrominoFactoryPlayer2 = new TetrominoFactory(colorSetting);

        difficultyPlayer1 = Difficulty.valueOf(SaveSetting.loadOneSettingFromFile(8));
        difficultyPlayer2 = Difficulty.valueOf(SaveSetting.loadOneSettingFromFile(8));

        charBoardPlayer1 = new char[BOARD_HEIGHT][BOARD_WIDTH];
        charBoardPlayer2 = new char[BOARD_HEIGHT][BOARD_WIDTH];

        for (char[] chars : charBoardPlayer1) {
            Arrays.fill(chars, 'N');
        }
        for (char[] chars : charBoardPlayer2) {
            Arrays.fill(chars, 'N');
        }

        colorBoardPlayer1 = new Color[BOARD_HEIGHT][BOARD_WIDTH];
        colorBoardPlayer2 = new Color[BOARD_HEIGHT][BOARD_WIDTH];

        spawnNewTetrominoPlayer1();
        spawnNewTetrominoPlayer2();
        setupGameLoopPlayer1();
        setupGameLoopPlayer2();
    }

    private void setupGameLoopPlayer1() {
        if (gameLoopPlayer1 != null) {
            gameLoopPlayer1.stop();
        }

        double interval = calculateInterval(difficultyPlayer1, totalClearedLinesPlayer1);

        gameLoopPlayer1 = new Timeline(new KeyFrame(Duration.seconds(interval), event -> moveDownPlayer1()));
        gameLoopPlayer1.setCycleCount(Timeline.INDEFINITE);
        gameLoopPlayer1.play();
    }

    private void setupGameLoopPlayer2() {
        if (gameLoopPlayer2 != null) {
            gameLoopPlayer2.stop();
        }

        double interval = calculateInterval(difficultyPlayer2, totalClearedLinesPlayer2);

        gameLoopPlayer2 = new Timeline(new KeyFrame(Duration.seconds(interval), event -> moveDownPlayer2()));
        gameLoopPlayer2.setCycleCount(Timeline.INDEFINITE);
        gameLoopPlayer2.play();
    }

    private double calculateInterval(Difficulty difficulty, int totalClearedLines) {
        double speedFactor = Math.pow(1.02, totalClearedLines);
        return difficulty.getBaseInterval() / speedFactor;
    }

    private void spawnNewTetrominoPlayer1() {
        generateTetrominoPlayer1();
        currentXPlayer1 = BOARD_WIDTH / 2 - currentTetrominoPlayer1.getWidth() / 2;
        currentYPlayer1 = 0;
        if (!canMove(currentXPlayer1, currentYPlayer1, currentTetrominoPlayer1, charBoardPlayer1)) {
            gameOverPlayer1 = true;
            gameLoopPlayer1.stop();
        }
    }

    private void spawnNewTetrominoPlayer2() {
        generateTetrominoPlayer2();
        currentXPlayer2 = BOARD_WIDTH / 2 - currentTetrominoPlayer2.getWidth() / 2;
        currentYPlayer2 = 0;
        if (!canMove(currentXPlayer2, currentYPlayer2, currentTetrominoPlayer2, charBoardPlayer2)) {
            gameOverPlayer2 = true;
            gameLoopPlayer2.stop();
        }
    }

    public void moveLeftPlayer1() {
        if (canMove(currentXPlayer1 - 1, currentYPlayer1, currentTetrominoPlayer1, charBoardPlayer1)) {
            currentXPlayer1--;
        }
    }

    public void moveRightPlayer1() {
        if (canMove(currentXPlayer1 + 1, currentYPlayer1, currentTetrominoPlayer1, charBoardPlayer1)) {
            currentXPlayer1++;
        }
    }

    public void moveDownPlayer1() {
        if (canMove(currentXPlayer1, currentYPlayer1 + 1, currentTetrominoPlayer1, charBoardPlayer1)) {
            currentYPlayer1++;
            scorePlayer1 += difficultyPlayer1.getBasePoint() / 6;
        } else {
            fixTetrominoPlayer1();
        }
    }

    public void moveLeftPlayer2() {
        if (canMove(currentXPlayer2 - 1, currentYPlayer2, currentTetrominoPlayer2, charBoardPlayer2)) {
            currentXPlayer2--;
        }
    }

    public void moveRightPlayer2() {
        if (canMove(currentXPlayer2 + 1, currentYPlayer2, currentTetrominoPlayer2, charBoardPlayer2)) {
            currentXPlayer2++;
        }
    }

    public void moveDownPlayer2() {
        if (canMove(currentXPlayer2, currentYPlayer2 + 1, currentTetrominoPlayer2, charBoardPlayer2)) {
            currentYPlayer2++;
            scorePlayer2 += difficultyPlayer2.getBasePoint() / 6;
        } else {
            fixTetrominoPlayer2();
        }
    }

    public void rotateClockwisePlayer1() {
        Tetromino rotated = currentTetrominoPlayer1.rotateClockwise();
        if (canMove(currentXPlayer1, currentYPlayer1, rotated, charBoardPlayer1) && currentTetrominoPlayer1.t
*/
