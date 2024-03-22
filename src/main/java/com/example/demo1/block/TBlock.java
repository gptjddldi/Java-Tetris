package com.example.demo1.block;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TBlock extends Block {
    public TBlock() {
        this.color = Color.PURPLE;
        fillBlocks();

        block1.setTranslateX(BLOCK_SIZE);
        block1.setTranslateY(0);

        block2.setTranslateX(0);
        block2.setTranslateY(BLOCK_SIZE);

        block3.setTranslateX(BLOCK_SIZE);
        block3.setTranslateY(BLOCK_SIZE);

        block4.setTranslateX(BLOCK_SIZE * 2);
        block4.setTranslateY(BLOCK_SIZE);

        getChildren().addAll(block1, block2, block3, block4);
    }
}
