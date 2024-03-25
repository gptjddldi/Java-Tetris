package com.example.page;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ScoreBoardPage extends Application {

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

        // 텍스트 입력란
        TextArea textArea = new TextArea();
        textArea.setPrefWidth(300);
        textArea.setPrefHeight(500);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        scoreBoardLayout.add(textArea, 0, 1, 2, 1); // 텍스트 영역을 1행 2열에 추가하고, 열의 수는 2로 지정하여 한 줄을 차지하도록 함

        // 텍스트 입력란 아래에 추가할 텍스트 필드와 버튼
        TextField inputField = new TextField();
        inputField.setPrefWidth(230);

        Button submitButton = new Button("Submit");

        scoreBoardLayout.add(inputField, 0, 2);
        scoreBoardLayout.add(submitButton, 1, 2);

        Scene scoreBoardPage = new Scene(scoreBoardLayout, 311, 621);

        primaryStage.setScene(scoreBoardPage);
        primaryStage.setTitle("scoreBoard");
        primaryStage.show();
    }
}
