package com.example.page;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ControlsSettingsWindowTest {

    @Test
    void testControlsSettingsWindowWithRandomInput() {
        // Given
        ControlsSettingsWindow controlsSettingsWindow = new ControlsSettingsWindow();
        Stage stage = new Stage();

        // When
        Platform.runLater(() -> controlsSettingsWindow.start(stage));

        // Then
        Platform.runLater(() -> {
            Scene scene = stage.getScene();
            assertNotNull(scene);
            Parent root = scene.getRoot();
            assertEquals("조작키 설정", stage.getTitle());

            // 랜덤 키 값 배열
            KeyCode[] randomKeys = {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE, KeyCode.X};

            // 텍스트 필드에 랜덤 값 입력
            TextField[] keyFields = {
                    (TextField) root.lookup("#keyField0"),
                    (TextField) root.lookup("#keyField1"),
                    (TextField) root.lookup("#keyField2"),
                    (TextField) root.lookup("#keyField3"),
                    (TextField) root.lookup("#keyField4"),
                    (TextField) root.lookup("#keyField5")
            };

            Random random = new SecureRandom();
            for (TextField keyField : keyFields) {
                assertNotNull(keyField);
                KeyCode randomKeyCode = randomKeys[random.nextInt(randomKeys.length)];
                keyField.setText(randomKeyCode.toString());
            }

            // 사용자 입력에 따라 UI가 올바르게 반응하는지 확인할 수 있습니다.
        });
    }
}