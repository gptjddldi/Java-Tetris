package com.example.page;

import com.example.javatetris.TetrisApplication;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane startLayout = new GridPane();
        startLayout.setAlignment(Pos.CENTER);
        startLayout.setVgap(20);
        startLayout.setHgap(20);
        startLayout.setPadding(new Insets(20));

        Button startButton = new Button("Start Game");
        Button settingButton = new Button("Setting");
        Button scoreboardButton = new Button("Scoreboard");
        Button exitButton = new Button("Exit");

        startButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 50px;");
        settingButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 50px;");
        scoreboardButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 50px;");
        exitButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 50px;");

        startButton.setOnAction(e -> {
            TetrisApplication tetrisApp = new TetrisApplication();
            tetrisApp.start(primaryStage);
        });

        settingButton.setOnAction(e -> {
            SettingPage settingPage = new SettingPage();
            settingPage.start(primaryStage);
        });

        scoreboardButton.setOnAction(e -> {
            ScoreBoardPage scoreBoardpage = new ScoreBoardPage();
            scoreBoardpage.start(primaryStage);
        });

        exitButton.setOnAction(e -> primaryStage.close());

        startLayout.add(startButton, 0, 0);
        startLayout.add(settingButton, 0, 1);
        startLayout.add(scoreboardButton, 0, 2);
        startLayout.add(exitButton, 0, 3);

        primaryStage.setResizable(false);

        Scene startPage = new Scene(startLayout, 311, 621);
        primaryStage.setScene(startPage);
        primaryStage.setTitle("Start");
        primaryStage.show();
    }
}

