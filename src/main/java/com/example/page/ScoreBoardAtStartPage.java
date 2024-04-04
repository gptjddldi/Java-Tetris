package com.example.page;

import com.example.SaveFile.ScoreBoardData;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ScoreBoardAtStartPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane scoreBoardLayout = new GridPane();
        scoreBoardLayout.setAlignment(Pos.CENTER);
        scoreBoardLayout.setVgap(10);
        scoreBoardLayout.setHgap(10);

        Button backButton = new Button("MainMenu");

        backButton.setOnAction(e -> {
            StartPage startPage = new StartPage();
            startPage.start(primaryStage);
        });

        scoreBoardLayout.add(backButton, 0, 0);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(300);
        scrollPane.setPrefHeight(550);
        scoreBoardLayout.add(scrollPane, 0, 1, 2, 1);

        try {
            ScoreBoardData.HomeloadRanking(scrollPane);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scene scoreBoardPage = new Scene(scoreBoardLayout, 311, 621);

        primaryStage.setScene(scoreBoardPage);
        primaryStage.setTitle("scoreBoard");
        primaryStage.show();
    }
}
