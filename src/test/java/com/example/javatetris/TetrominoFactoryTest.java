package com.example.javatetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class TetrominoFactoryTest {
    @Test
    void testTetrominoGenerationEasy() {
        testTetrominoGeneration(Difficulty.EASY, 0.20, 10000);
    }

    @Test
    void testTetrominoGenerationMedium() {
        testTetrominoGeneration(Difficulty.MEDIUM, 0, 10000);
    }

    @Test
    void testTetrominoGenerationHard() {
        testTetrominoGeneration(Difficulty.HARD, -0.20, 10000);
    }

    private void testTetrominoGeneration(Difficulty difficulty, double additionalProbability, int trials) {
        double baseRate = 1.0 / 7;
        double expectedRate = baseRate + additionalProbability * baseRate;
        double tolerance = 0.05 * expectedRate;  // 허용 오차 범위 (기대 확률의 5%)

        double expectedCount = trials * expectedRate;
        double lowerBound = expectedCount - tolerance * trials;
        double upperBound = expectedCount + tolerance * trials;

        Map<BasicTetrominoType, Integer> counts = new HashMap<>();
        for (BasicTetrominoType type : BasicTetrominoType.values()) {
            counts.put(type, 0);
        }

        for (int i = 0; i < trials; i++) {
            Tetromino tetromino = TetrominoFactory.generateTetromino(difficulty);
            counts.put((BasicTetrominoType) tetromino.tetrominoType(), counts.get(tetromino.tetrominoType()) + 1);
        }

        System.out.println(difficulty.name() + " Expected I_SHAPE Count: " + expectedCount +
                ", Actual: " + counts.get(BasicTetrominoType.I_SHAPE) +
                ", Lower Bound: " + lowerBound +
                ", Upper Bound: " + upperBound);
        assertTrue(counts.get(BasicTetrominoType.I_SHAPE) >= lowerBound &&
                counts.get(BasicTetrominoType.I_SHAPE) <= upperBound);
    }
}
