package com.example.demo1;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.example.demo1.block.*;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class HelloApplication extends GameApplication {

    private static final int BLOCK_SIZE = 40;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;

    private Point2D direction = new Point2D(0, 1);
    private double speed = 0.5;

//    private double inputDelay = 0.2;
//    private double lastMove = 0;
//    꾹누르기 허용하고 싶으면 onKeyDown => onKey 로 수정

    private Entity currentBlock;

    enum BlockType {
        I, J, L, O, S, T, Z
    }

    enum EntityType {
        BLOCK
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(BLOCK_SIZE * WIDTH);
        settings.setHeight(BLOCK_SIZE * HEIGHT);
        settings.setTitle("Tetris");

        settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    @Override
    protected void initInput() {
        FXGL.onKeyDown(KeyCode.RIGHT, () -> {
            move(1, 0);
        });
        FXGL.onKeyDown(KeyCode.LEFT, () -> {
            move(-1, 0);
        });
        // 잠시만 쓸거임
        FXGL.onKeyDown(KeyCode.DOWN, () -> {
            move(0, 1);
        });

//        getInput().addAction(new UserAction("Rotate") {
//            @Override
//            protected void onActionBegin() {
//                rotate();
//            }
//        }, KeyCode.UP);
    }


    @Override
    protected void initGame() {

        FXGL.getGameTimer().runAtInterval(() -> {
            moveBlockDown();
        }, Duration.seconds(1));

        spawnBlock();
    }

    @Override
    protected void onUpdate(double tpf) {
//        System.out.println("onUpdate");
//        System.out.println(tpf);
//        moveDown();
    }

    private Node generateRandomBlock() {
        int blockType = FXGLMath.random(0, 6);
        BlockType type = BlockType.values()[blockType];
        switch(type) {
            case I:
                return new IBlock();
            case J:
                return new JBlock();
            case L:
                return new LBlock();
            case Z:
                return new ZBlock();
            case S:
                return new SBlock();
            case T:
                return new TBlock();
            case O:
                return new OBlock();
        }
        return new LBlock();
    }

    private void spawnBlock() {
        // todo: 줄 삭제 로직

        Node block = generateRandomBlock();

//        currentBlock = new Entity();
//        currentBlock.setPosition(WIDTH / 2 * BLOCK_SIZE, 0);
        currentBlock = entityBuilder()
                .at(WIDTH / 2 * BLOCK_SIZE, 80)
                .type(EntityType.BLOCK)
                .viewWithBBox(block)
                .buildAndAttach();

//        currentBlock.addComponent(new BlockComponent(block));


        getGameWorld().addEntity(currentBlock);
    }

    private void move(double dx, double dy) {
        if (!isBlockCollision(currentBlock)){
            currentBlock.translate(dx * BLOCK_SIZE, dy * BLOCK_SIZE);
        }
    }

//    private void moveDown() {
//        move(direction.getX(), direction.getY() * speed);
//    }

//    @Override
//    protected void initPhysics() {
//        onCollisionBegin(EntityType.BLOCK, EntityType.BLOCK, (block, otherBlock) -> {
////            move(0, -1);
//            System.out.println("collision");
////            return true;
//        });
//    }

    public boolean isBlockCollision(Entity block) {
        // todo: 블록 간 충돌 로직
        if(block.getBottomY() >= HEIGHT * BLOCK_SIZE) {
            return true;
        }

        return false;
    }

    public void moveBlockDown() {
        if (!isBlockCollision(currentBlock)) {
//            block.getPositionComponent().translateY(TILE_SIZE); //block 이동
            move(0, 1);
        } else {
            spawnBlock();
        }
    }

    private void rotate() {
        // 블록 회전 로직을 구현하세요
    }

    public static void main(String[] args) {
        launch(args);
    }
}