package com.example.page;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.example.SaveFile.size.size;
import static org.junit.jupiter.api.Assertions.*;

public class SelectBattleModeTest {

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        JavaFXInitializer.initToolkit();
    }

    @Test
    public void testCreateMenuButton() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                SelectBattleMode selectBattleMode = new SelectBattleMode();
                Button normalButton = selectBattleMode.createMenuButton("일반모드");

                assertNotNull(normalButton, "Button should not be null");
                assertEquals("일반모드", normalButton.getText(), "Button text should match");

                // 추가적으로 스타일 및 크기 등을 확인할 수 있습니다.
                assertEquals("-fx-font-size: "+20*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+50*size()+"px;", normalButton.getStyle(), "Button style should match");
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void testStartMethod() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                SelectBattleMode selectBattleMode = new SelectBattleMode();
                selectBattleMode.start(stage);

                Scene scene = stage.getScene();
                assertNotNull(scene, "Scene should not be null");
                assertEquals(290 * size(), scene.getWidth(), 0.1, "Scene width should match");
                assertEquals(492 * size(), scene.getHeight(), 0.1, "Scene height should match");
                assertEquals("SelectBattleMode", stage.getTitle(), "Stage title should match");
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
    public void testChangeScene() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                SelectBattleMode selectBattleMode = new SelectBattleMode();

                Application mockApplication = new Application() {
                    @Override
                    public void start(Stage primaryStage) {
                        primaryStage.setTitle("Mock Application");
                    }
                };

                selectBattleMode.changeScene(mockApplication, stage);

                assertEquals("Mock Application", stage.getTitle(), "Stage title should be set by mock application");
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
                SelectBattleMode selectBattleMode = new SelectBattleMode();
                selectBattleMode.start(stage);

                Scene scene = stage.getScene();
                VBox layout = (VBox) scene.getRoot();

                Button normalButton = (Button) layout.getChildren().get(0);
                Button itemButton = (Button) layout.getChildren().get(1);
                Button timeAttackButton = (Button) layout.getChildren().get(2);

                assertNotNull(normalButton, "Normal mode button should not be null");
                assertNotNull(itemButton, "Item mode button should not be null");
                assertNotNull(timeAttackButton, "Time attack mode button should not be null");

                normalButton.fire();
                assertEquals("normal", SelectBattleMode.battlemode, "battlemode should be set to 'normal'");

                itemButton.fire();
                assertEquals("item", SelectBattleMode.battlemode, "battlemode should be set to 'item'");

                timeAttackButton.fire();
                assertEquals("timeattack", SelectBattleMode.battlemode, "battlemode should be set to 'timeattack'");

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
