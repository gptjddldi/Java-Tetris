package com.example.javatetris;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class TetrisGameBattle extends TetrisGame {
    private TetrisGameBattle opponent;
    private char[][] nextAttack;
    public TetrisGameBattle(String mode) {
        super(mode);
    }

    public void setOpponent(TetrisGameBattle opponent) {
        this.opponent = opponent;
    }

    public void receiveAttack(char[][] clearedLines) {
        nextAttack = clearedLines;
    }

    public char[][] getNextAttack() {
        return nextAttack;
    }

    @Override
    protected void spawnNewTetromino() {
        if(nextAttack != null) {
            generateNextAttack();
        }
        super.spawnNewTetromino();
    }

    protected void fixTetromino() {
        char[][] shape = currentTetromino.shape();
        Color color = currentTetromino.color();

        // 테트로미노를 보드에 고정
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 'N') {
                    charBoard[currentY + i][currentX + j] = shape[i][j];
                    colorBoard[currentY + i][currentX + j] = color;
                }
            }
        }

        // 현재 보드 상태 저장
        char[][] boardWithoutCurrentTetromino = new char[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            System.arraycopy(charBoard[i], 0, boardWithoutCurrentTetromino[i], 0, BOARD_WIDTH);
        }

        // 꽉 찬 라인의 인덱스 저장
        List<Integer> linesToClear = new ArrayList<>();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            boolean lineCleared = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (charBoard[y][x] == 'N') {
                    lineCleared = false;
                    break;
                }
            }
            if (lineCleared) {
                linesToClear.add(y);
            }
        }

        // 현재 테트로미노를 제외한 보드 생성
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 'N') {
                    boardWithoutCurrentTetromino[currentY + i][currentX + j] = 'N';
                }
            }
        }

        int num = linesToClear.size();

        // 꽉 찬 라인의 정보 저장
        char[][] clearedLines = new char[num][BOARD_WIDTH];
        for (int i = 0; i < num; i++) {
            int y = linesToClear.get(i);
            System.arraycopy(boardWithoutCurrentTetromino[y], 0, clearedLines[i], 0, BOARD_WIDTH);
        }

        // 꽉 찬 라인을 제거하고 점수 계산
        if(num > 0) {
            attackOpponent(clearedLines);
        }
        clearLines(num);

        spawnNewTetromino();
    }

    private void attackOpponent(char[][] clearedLines) {
        opponent.receiveAttack(clearedLines);
    }

    private void generateNextAttack() {
        int num = nextAttack.length;

        for(int y = num; y < BOARD_HEIGHT; y++) {
                System.arraycopy(charBoard[y], 0, charBoard[y-num], 0, BOARD_WIDTH);
                System.arraycopy(colorBoard[y], 0, colorBoard[y-num], 0, BOARD_WIDTH);
        }


        for(int y = BOARD_HEIGHT - num; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (nextAttack[y - BOARD_HEIGHT + num][x] != 'N') {
                    charBoard[y][x] = nextAttack[y - BOARD_HEIGHT + num][x];
                    colorBoard[y][x] = Color.rgb(136, 204, 238); //cyan
                } else {
                    charBoard[y][x] = 'N';
                }
            }
        }
        nextAttack = null;
    }
}


