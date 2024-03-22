package org.example.myjavafxtetris;

enum TetrominoType {
    I_SHAPE(new boolean[][]{{true, true, true, true}}),
    J_SHAPE(new boolean[][]{{true, false, false}, {true, true, true}}),
    L_SHAPE(new boolean[][]{{false, false, true}, {true, true, true}}),
    O_SHAPE(new boolean[][]{{true, true}, {true, true}}),
    S_SHAPE(new boolean[][]{{false, true, true}, {true, true, false}}),
    T_SHAPE(new boolean[][]{{false, true, false}, {true, true, true}}),
    Z_SHAPE(new boolean[][]{{true, true, false}, {false, true, true}});

    private final boolean[][] shape;

    TetrominoType(boolean[][] shape) {
        this.shape = shape;
    }

    public boolean[][] getShape() {
        return shape;
    }
}
