package com.example.demo1.block;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JBlock extends Block {
    public JBlock() {
        this.color = Color.BLUE;
        fillBlocks();

        block1.setTranslateX(0);
        block1.setTranslateY(0);

        block2.setTranslateX(0);
        block2.setTranslateY(BLOCK_SIZE);

        block3.setTranslateX(0);
        block3.setTranslateY(BLOCK_SIZE * 2);

        block4.setTranslateX(BLOCK_SIZE);
        block4.setTranslateY(0);

        getChildren().addAll(block1, block2, block3, block4);
    }
}
