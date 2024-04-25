package com.example.javatetris;

public enum Difficulty {
    EASY(1.0),
    MEDIUM(0.8),
    HARD(0.6);

    private final double baseInterval;

    Difficulty(double baseInterval) {
        this.baseInterval = baseInterval;
    }

    public double getBaseInterval() {
        return baseInterval;
    }
}
