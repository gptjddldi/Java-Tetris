package com.example.page;

import com.example.SaveFile.ScoreBoardData;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static com.example.SaveFile.size.size;

public class ScoreBoardAtStartPage extends Application {


    @Override
    public void start(Stage primaryStage) {
        GridPane scoreBoardLayout = createScoreBoardLayout(primaryStage);
        Scene scoreBoardPage = new Scene(scoreBoardLayout, 290 * size(), 492 * size());

        primaryStage.setScene(scoreBoardPage);
        primaryStage.setTitle("scoreBoard");
        primaryStage.show();
    }

    public GridPane createScoreBoardLayout(Stage primaryStage) {
        GridPane scoreBoardLayout = new GridPane();
        scoreBoardLayout.setAlignment(Pos.CENTER);
        scoreBoardLayout.setVgap(10 * size());
        scoreBoardLayout.setHgap(10 * size());

        Button backButton = createBackButton(primaryStage);
        scoreBoardLayout.add(backButton, 0, 0);

        ScrollPane scrollPane = createScrollPane();
        scoreBoardLayout.add(scrollPane, 0, 1, 2, 1);

        loadScoreBoardData(scrollPane);

        return scoreBoardLayout;
    }

    public Button createBackButton(Stage primaryStage) {
        Button backButton = new Button("메인메뉴");
        backButton.setStyle("-fx-font-size: " + 10 * size() + "px; -fx-pref-width: " + 70 * size() + "px; -fx-pref-height: " + 30 * size() + "px;");
        backButton.setOnAction(e -> {
            StartPage startPage = new StartPage();
            startPage.start(primaryStage);
        });
        return backButton;
    }

    public ScrollPane createScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-font-size: " + 12 * size() + "px;");
        scrollPane.setPrefWidth(280 * size());
        scrollPane.setPrefHeight(470 * size());
        return scrollPane;
    }

    public void loadScoreBoardData(ScrollPane scrollPane) {
        try {
            ScoreBoardData.HomeloadRanking(scrollPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
