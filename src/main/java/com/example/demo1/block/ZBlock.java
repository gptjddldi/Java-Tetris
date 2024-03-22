package com.example.demo1.block;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ZBlock extends Block {
    public ZBlock() {
        this.color = Color.RED;
        fillBlocks();

        block1.setTranslateX(0);
        block1.setTranslateY(0);

        block2.setTranslateX(BLOCK_SIZE);
        block2.setTranslateY(0);

        block3.setTranslateX(BLOCK_SIZE);
        block3.setTranslateY(BLOCK_SIZE);

        block4.setTranslateX(BLOCK_SIZE * 2);
        block4.setTranslateY(BLOCK_SIZE);

        getChildren().addAll(block1, block2, block3, block4);
    }
}
