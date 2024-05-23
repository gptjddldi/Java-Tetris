package com.example.page;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class JavaFXInitializer extends Application {

    private static final CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void start(Stage primaryStage) {
        latch.countDown();
    }

    public static void initToolkit() throws InterruptedException {
        new Thread(() -> Application.launch(JavaFXInitializer.class)).start();
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new IllegalStateException("JavaFX toolkit initialization timeout.");
        }
        Platform.setImplicitExit(false);
    }
}
