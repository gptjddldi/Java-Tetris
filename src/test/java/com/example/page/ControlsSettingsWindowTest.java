package com.example.page;

import static org.junit.jupiter.api.Assertions.*;

import com.example.SaveFile.SaveSetting;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.util.concurrent.CountDownLatch;

public class ControlsSettingsWindowTest {

    private ControlsSettingsWindow controlsSettingsWindow;
    private static boolean javafxInitialized = false;

    @BeforeAll
    public static void initJFX() throws Exception {
        if (!javafxInitialized) {
            final CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            latch.await();
            javafxInitialized = true;
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                controlsSettingsWindow = new ControlsSettingsWindow();
                controlsSettingsWindow.start(new Stage());
                latch.countDown();
            } catch (Exception e) {
                fail("Failed to initialize ControlsSettingsWindow: " + e.getMessage());
            }
        });
        latch.await(); // Wait for JavaFX thread to initialize
    }

    @Test
    public void testInitialization() {
        assertNotNull(controlsSettingsWindow, "ControlsSettingsWindow should be initialized");
    }


    @Test
    public void testDuplicateKeysCheck() {
        Platform.runLater(() -> {
            String[] duplicateKeySettings = { "UP", "UP", "LEFT", "RIGHT", "P","W", "S", "A", "D", "V", "SPACE"};
            SaveSetting.saveKeySettingsToFile(duplicateKeySettings);
            controlsSettingsWindow.showAndWait();

            TextField[] keyFields = getKeyFields(controlsSettingsWindow);
            boolean hasDuplicateKeys = checkForDuplicateKeys(keyFields);
            assertTrue(hasDuplicateKeys, "There should be a duplicate key warning");
        });
    }

    private TextField[] getKeyFields(ControlsSettingsWindow controlsSettingsWindow) {
        try {
            java.lang.reflect.Field field = ControlsSettingsWindow.class.getDeclaredField("keyFields");
            field.setAccessible(true);
            return (TextField[]) field.get(controlsSettingsWindow);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkForDuplicateKeys(TextField[] keyFields) {
        for (int i = 0; i < keyFields.length; i++) {
            String currentKey = keyFields[i].getText().toUpperCase();
            for (int j = 0; j < keyFields.length; j++) {
                if (i != j) {
                    String comparedKey = keyFields[j].getText().toUpperCase();
                    if (currentKey.equals(comparedKey)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}


