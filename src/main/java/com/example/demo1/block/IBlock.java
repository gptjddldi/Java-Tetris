package com.example.demo1.block;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class IBlock extends Block {
    public IBlock() {
        this.color = Color.CYAN;
        fillBlocks();

        block1.setTranslateX(0);
        block1.setTranslateY(0);

        block2.setTranslateX(0);
        block2.setTranslateY(BLOCK_SIZE);

        block3.setTranslateX(0);
        block3.setTranslateY(BLOCK_SIZE * 2);

        block4.setTranslateX(0);
        block4.setTranslateY(BLOCK_SIZE * 3);

        getChildren().addAll(block1, block2, block3, block4);
    }
}
