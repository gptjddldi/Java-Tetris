package com.example.page;

import com.example.SaveFile.SaveSetting;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SettingPageTest {

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        JavaFXInitializer.initToolkit();
    }

    @Test
    public void testSettingPageComponents() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                SettingPage settingPage = new SettingPage();
                settingPage.start(stage);

                Scene scene = stage.getScene();
                VBox layout = (VBox) scene.getRoot();

                Label sizeLabel = (Label) layout.getChildren().get(0);
                assertNotNull(sizeLabel, "Size label should not be null");
                assertEquals("화면크기", sizeLabel.getText(), "Size label text should be '화면크기'");

                ComboBox<String> screenSizeComboBox = (ComboBox<String>) layout.getChildren().get(1);
                assertNotNull(screenSizeComboBox, "Screen size combo box should not be null");
                assertEquals("SMALL", screenSizeComboBox.getItems().get(0), "First item should be 'SMALL'");

                Button setControlsButton = (Button) layout.getChildren().get(2);
                assertNotNull(setControlsButton, "Set controls button should not be null");

                CheckBox colorBlindCheckBox = (CheckBox) layout.getChildren().get(3);
                assertNotNull(colorBlindCheckBox, "Color blind check box should not be null");

                Label levelLabel = (Label) layout.getChildren().get(4);
                assertNotNull(levelLabel, "Level label should not be null");
                assertEquals("난이도", levelLabel.getText(), "Level label text should be '난이도'");

                ComboBox<String> levelComboBox = (ComboBox<String>) layout.getChildren().get(5);
                assertNotNull(levelComboBox, "Level combo box should not be null");
                assertEquals("EASY", levelComboBox.getItems().get(0), "First item should be 'EASY'");

                Button resetSettingButton = (Button) layout.getChildren().get(6);
                assertNotNull(resetSettingButton, "Reset setting button should not be null");

                Button resetScoreboardButton = (Button) layout.getChildren().get(7);
                assertNotNull(resetScoreboardButton, "Reset scoreboard button should not be null");

                Button returnStartPageButton = (Button) layout.getChildren().get(8);
                assertNotNull(returnStartPageButton, "Return start page button should not be null");

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
                SettingPage settingPage = new SettingPage();
                settingPage.start(stage);

                Scene scene = stage.getScene();
                VBox layout = (VBox) scene.getRoot();

                Button setControlsButton = (Button) layout.getChildren().get(2);
                assertNotNull(setControlsButton, "Set controls button should not be null");

                Button resetSettingButton = (Button) layout.getChildren().get(6);
                assertNotNull(resetSettingButton, "Reset setting button should not be null");

                Button resetScoreboardButton = (Button) layout.getChildren().get(7);
                assertNotNull(resetScoreboardButton, "Reset scoreboard button should not be null");

                Button returnStartPageButton = (Button) layout.getChildren().get(8);
                assertNotNull(returnStartPageButton, "Return start page button should not be null");

                setControlsButton.fire();

                resetSettingButton.fire();

                resetScoreboardButton.fire();

                returnStartPageButton.fire();

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
    public void testColorBlindMode() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                SettingPage settingPage = new SettingPage();
                settingPage.start(stage);

                Scene scene = stage.getScene();
                VBox layout = (VBox) scene.getRoot();

                CheckBox colorBlindCheckBox = (CheckBox) layout.getChildren().get(3);
                assertNotNull(colorBlindCheckBox, "Color blind check box should not be null");

                colorBlindCheckBox.setSelected(true);
                colorBlindCheckBox.fire();
                assertEquals("on", SaveSetting.loadOneSettingFromFile(12), "Color blind mode should be 'on'");

                colorBlindCheckBox.setSelected(false);
                colorBlindCheckBox.fire();
                assertEquals("off", SaveSetting.loadOneSettingFromFile(12), "Color blind mode should be 'off'");

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
