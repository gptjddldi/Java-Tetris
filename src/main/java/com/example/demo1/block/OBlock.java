package com.example.demo1.block;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OBlock extends Block {

    public OBlock() {
        this.color = Color.YELLOW;
        fillBlocks();
        block1.setTranslateX(0);
        block1.setTranslateY(0);

        block2.setTranslateX(BLOCK_SIZE);
        block2.setTranslateY(0);

        block3.setTranslateX(0);
        block3.setTranslateY(BLOCK_SIZE);

        block4.setTranslateX(BLOCK_SIZE);
        block4.setTranslateY(BLOCK_SIZE);

        getChildren().addAll(block1, block2, block3, block4);
    }
}
