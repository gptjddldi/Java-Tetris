package com.example.page;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardAtGameEndTest {

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        JavaFXInitializer.initToolkit();
    }
    @Test
    public void testStartMethod() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                ScoreBoardAtGameEnd ScoreBoardAtGameEnd = new ScoreBoardAtGameEnd();
                Stage stage = new Stage();
                ScoreBoardAtGameEnd.start(stage);

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
                ScoreBoardAtGameEnd scoreBoardAtGameEnd = new ScoreBoardAtGameEnd(500);
                Stage stage = new Stage();
                GridPane scoreBoardLayout = scoreBoardAtGameEnd.createScoreBoardLayout(stage);
                assertNotNull(scoreBoardLayout, "ScoreBoardLayout should not be null");
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }


    @Test
    public void testShowAlertAndReturnToMainMenu() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                ScoreBoardAtGameEnd scoreBoardAtGameEnd = new ScoreBoardAtGameEnd(5);
                Stage stage = new Stage();

                scoreBoardAtGameEnd.showAlertAndReturnToMainMenu(stage, "Test Alert Message");

                assertTrue(Alert.AlertType.INFORMATION.equals(Alert.AlertType.INFORMATION));

            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }
}
