package com.example.javatetris;

public enum Difficulty {
    EASY(10, 1.0),
    MEDIUM(12, 0.8),
    HARD(14, 0.6);

    private final double baseInterval;
    private final int basePoint;

    Difficulty(int basePoint,double baseInterval) {
        this.baseInterval = baseInterval;
        this.basePoint = basePoint;
    }

    public double getBaseInterval() {
        return baseInterval;
    }
    public double getBasePoint() {
        return basePoint;
    }
}
