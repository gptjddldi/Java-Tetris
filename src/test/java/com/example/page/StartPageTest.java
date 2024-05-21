package com.example.page;


import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class StartPageTest {

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        JavaFXInitializer.initToolkit();
    }

    @Test
    public void testStartPageComponents() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                StartPage startPage = new StartPage();
                startPage.start(stage);

                Scene scene = stage.getScene();
                VBox layout = (VBox) scene.getRoot();

                Label gameTitleLabel = (Label) layout.getChildren().get(0);
                assertNotNull(gameTitleLabel, "Game title label should not be null");
                assertEquals("Java Tetris", gameTitleLabel.getText(), "Game title label text should be 'Java Tetris'");

                Button startButton = (Button) layout.getChildren().get(1);
                assertNotNull(startButton, "Start button should not be null");

                Button startITEMButton = (Button) layout.getChildren().get(2);
                assertNotNull(startITEMButton, "Start item button should not be null");

                Button startBATTLEButton = (Button) layout.getChildren().get(3);
                assertNotNull(startBATTLEButton, "Start battle button should not be null");

                Button settingButton = (Button) layout.getChildren().get(4);
                assertNotNull(settingButton, "Setting button should not be null");

                Button scoreboardButton = (Button) layout.getChildren().get(5);
                assertNotNull(scoreboardButton, "Scoreboard button should not be null");

                Button exitButton = (Button) layout.getChildren().get(6);
                assertNotNull(exitButton, "Exit button should not be null");

            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception should not be thrown");
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void testButtonActions() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                StartPage startPage = new StartPage();
                startPage.start(stage);

                Scene scene = stage.getScene();
                VBox layout = (VBox) scene.getRoot();

                Button startButton = (Button) layout.getChildren().get(1);
                assertNotNull(startButton, "Start button should not be null");

                Button startITEMButton = (Button) layout.getChildren().get(2);
                assertNotNull(startITEMButton, "Start item button should not be null");

                Button startBATTLEButton = (Button) layout.getChildren().get(3);
                assertNotNull(startBATTLEButton, "Start battle button should not be null");

                Button settingButton = (Button) layout.getChildren().get(4);
                assertNotNull(settingButton, "Setting button should not be null");

                Button scoreboardButton = (Button) layout.getChildren().get(5);
                assertNotNull(scoreboardButton, "Scoreboard button should not be null");

                Button exitButton = (Button) layout.getChildren().get(6);
                assertNotNull(exitButton, "Exit button should not be null");

                startButton.fire();

                startITEMButton.fire();

                startBATTLEButton.fire();

                settingButton.fire();

                scoreboardButton.fire();

                exitButton.fire();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception should not be thrown");
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }


    @Test
    public void testKeyPressActions() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                StartPage startPage = new StartPage();
                startPage.start(stage);

                Scene scene = stage.getScene();
                VBox layout = (VBox) scene.getRoot();

                Button startButton = (Button) layout.getChildren().get(1);
                Button exitButton = (Button) layout.getChildren().get(6);

                assertNotNull(startButton, "Start button should not be null");
                assertNotNull(exitButton, "Exit button should not be null");

                scene.setOnKeyPressed(event -> {
                    KeyCode keyCode = event.getCode();
                    if (keyCode == KeyCode.DOWN) {
                        startButton.requestFocus();
                    } else if (keyCode == KeyCode.UP) {
                        exitButton.requestFocus();
                    }
                });

                Platform.runLater(() -> scene.getOnKeyPressed().handle(new javafx.scene.input.KeyEvent(null, null, javafx.scene.input.KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false)));
                Thread.sleep(500);
                assertTrue(startButton.isFocused(), "Start button should be focused");

                Platform.runLater(() -> scene.getOnKeyPressed().handle(new javafx.scene.input.KeyEvent(null, null, javafx.scene.input.KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false)));
                Thread.sleep(500);
                assertTrue(exitButton.isFocused(), "Exit button should be focused");

            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception should not be thrown");
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void testShowInfoPopup() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                StartPage startPage = new StartPage();
                startPage.start(stage);

                // Simulate key press other than UP or DOWN
                Scene scene = stage.getScene();
                Platform.runLater(() -> scene.getOnKeyPressed().handle(new javafx.scene.input.KeyEvent(null, null, javafx.scene.input.KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false)));

                // Wait for the popup to appear
                Thread.sleep(500);

                // Check if the popup is shown
                Stage popupStage = (Stage) scene.getWindow().getScene().getWindow().getScene().getWindow().getScene().getWindow();
                assertNotNull(popupStage, "Popup stage should not be null");
                assertEquals("Information", popupStage.getTitle(), "Popup title should be 'Information'");

            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception should not be thrown");
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }
}
