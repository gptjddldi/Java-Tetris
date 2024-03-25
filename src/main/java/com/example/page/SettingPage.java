package com.example.page;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SettingPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane settingLayout = new GridPane();
        settingLayout.setAlignment(Pos.TOP_LEFT);
        settingLayout.setVgap(10);
        settingLayout.setHgap(10);

        Button returnStartPage = new Button("Mainmenu");

        returnStartPage.setOnAction(e -> {
            StartPage startScreen = new StartPage();
            startScreen.start(primaryStage);
        });

        settingLayout.add(returnStartPage, 0, 0);

        Scene settingPage = new Scene(settingLayout, 311, 621);

        primaryStage.setScene(settingPage);
        primaryStage.setTitle("Setting");
        primaryStage.show();
    }
}
