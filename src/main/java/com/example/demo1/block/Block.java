package com.example.demo1.block;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Block extends Group {
    public static final int BLOCK_SIZE = 40;
    public static Color color;
    public Rectangle block1 = createBlock();
    public Rectangle block2 = createBlock();
    public Rectangle block3 = createBlock();
    public Rectangle block4 = createBlock();

    public Block() {
        this.color = Color.RED;
    }

    public Rectangle createBlock() {
        Rectangle block = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        block.setFill(color);
        return block;
    }

    protected void fillBlocks() {
        block1.setFill(color);
        block2.setFill(color);
        block3.setFill(color);
        block4.setFill(color);
    }

    public Rectangle[] getBlocks() {
        return new Rectangle[] {block1, block2, block3, block4};
    }

    public int getBottomY() {
        int bottomY = 0;
        for(Rectangle block : getBlocks()) {
            bottomY = Math.max((int) block.getTranslateY(), bottomY);
        }
        return bottomY;
    }

    public int getTopY() {
        int topY = 0;
        for(Rectangle block : getBlocks()) {
            topY = Math.min((int) block.getTranslateY(), topY);
        }
        return topY;
    }

    public int getRightX() {
        int rightX = 0;
        for(Rectangle block : getBlocks()) {
            rightX = Math.max((int) block.getTranslateX(), rightX);
        }
        return rightX;
    }

    public int getLeftX() {
        int leftX = 0;
        for(Rectangle block : getBlocks()) {
            leftX = Math.min((int) block.getTranslateX(), leftX);
        }
        return leftX;
    }

}





//    public void rotate() {
//        int[][] newShape = new int[shape.length][shape[0].length];
//        for(int i = 0; i < shape.length; i++) {
//            for(int j = 0; j < shape[i].length; j++) {
//                newShape[i][j] = shape[shape[i].length - j - 1][i];
//            }
//        }
//        shape = newShape;
//    }