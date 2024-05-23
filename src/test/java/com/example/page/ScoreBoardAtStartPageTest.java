package com.example.page;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardAtStartPageTest {

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        JavaFXInitializer.initToolkit();
    }
    @Test
    public void testStartMethod() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                ScoreBoardAtStartPage scoreBoardAtStartPage = new ScoreBoardAtStartPage();
                Stage stage = new Stage();
                scoreBoardAtStartPage.start(stage);

                Scene scene = stage.getScene();
                assertNotNull(scene, "Scene should not be null");
                assertEquals("scoreBoard", stage.getTitle(), "Stage title should be 'scoreBoard'");
                assertFalse(stage.getScene().getRoot().getChildrenUnmodifiable().isEmpty(), "Scene root should have children");
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }
    @Test
    public void testCreateScoreBoardLayout_NotNull() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                ScoreBoardAtStartPage scoreBoardAtStartPage = new ScoreBoardAtStartPage();
                Stage stage = new Stage();
                GridPane scoreBoardLayout = scoreBoardAtStartPage.createScoreBoardLayout(stage);
                assertNotNull(scoreBoardLayout, "ScoreBoardLayout should not be null");
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void testBackButtonExists() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                ScoreBoardAtStartPage scoreBoardAtStartPage = new ScoreBoardAtStartPage();
                Stage stage = new Stage();
                GridPane scoreBoardLayout = scoreBoardAtStartPage.createScoreBoardLayout(stage);
                Button backButton = (Button) scoreBoardLayout.getChildren().get(0);
                assertNotNull(backButton, "Back button should not be null");
                assertEquals("메인메뉴", backButton.getText(), "Back button text should be '메인메뉴'");
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void testScrollPaneExists() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                ScoreBoardAtStartPage scoreBoardAtStartPage = new ScoreBoardAtStartPage();
                Stage stage = new Stage();
                GridPane scoreBoardLayout = scoreBoardAtStartPage.createScoreBoardLayout(stage);
                ScrollPane scrollPane = (ScrollPane) scoreBoardLayout.getChildren().get(1);
                assertNotNull(scrollPane, "ScrollPane should not be null");
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }


}


