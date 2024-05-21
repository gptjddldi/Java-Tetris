package com.example;

import com.example.page.StartPage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    private static final CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void testMainLaunch() throws Exception {
        // Initialize the JavaFX environment
        Platform.startup(() -> {
            Stage stage = new Stage();
            try {
                Application app = new StartPage();
                app.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });

        // Wait for the JavaFX application to start
        assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX application did not start within 5 seconds");
    }
}
