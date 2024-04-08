package com.example.page;

import com.example.javatetris.TetrisApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

        Label gameTitleLabel = new Label("Java Tetris");
        gameTitleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        startLayout.add(gameTitleLabel, 0, 0);

        Button startButton = createMenuButton("Start Game");
        Button settingButton = createMenuButton("Setting");
        Button scoreboardButton = createMenuButton("Scoreboard");
        Button exitButton = createMenuButton("Exit");

        startLayout.add(startButton, 0, 1);
        startLayout.add(settingButton, 0, 2);
        startLayout.add(scoreboardButton, 0, 3);
        startLayout.add(exitButton, 0, 4);

        Scene startPage = new Scene(startLayout, 311, 621);
        primaryStage.setScene(startPage);
        primaryStage.setTitle("Start");
        primaryStage.setResizable(false);
        primaryStage.show();

        startPage.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.DOWN) {
                startButton.requestFocus();
            } else if (keyCode == KeyCode.UP) {
                exitButton.requestFocus();
            } else {
                showInfoPopup(primaryStage);
            }
        });

        startButton.setOnAction(e -> changeScene(new TetrisApplication(), primaryStage));
        settingButton.setOnAction(e -> changeScene(new SettingPage(), primaryStage));
        scoreboardButton.setOnAction(e -> changeScene(new ScoreBoardAtStartPage(), primaryStage));
        exitButton.setOnAction(e -> Platform.exit());
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 50px;");
        return button;
    }

    private void showInfoPopup(Stage primaryStage) {
        Label messageLabel = new Label("메뉴창에서는 위, 아래, 엔터 또는 마우스로 이용 가능합니다");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            Stage popupStage = (Stage) closeButton.getScene().getWindow();
            popupStage.close();
        });

        VBox infoLayout = new VBox();
        infoLayout.setAlignment(Pos.CENTER);
        infoLayout.setPadding(new Insets(20));
        infoLayout.getChildren().addAll(messageLabel, closeButton);

        Scene infoScene = new Scene(infoLayout, 400, 70);
        Stage popupStage = new Stage();
        popupStage.initOwner(primaryStage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(infoScene);
        popupStage.setTitle("Information");
        popupStage.showAndWait();
    }

    private void changeScene(Application application, Stage primaryStage) {
        try {
            application.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}