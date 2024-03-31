package com.example.page;

import com.example.SaveFile.Reset;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

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

        Button returnStartPage = new Button("Main menu");

        returnStartPage.setOnAction(e -> {
            StartPage startScreen = new StartPage();
            startScreen.start(primaryStage);
        });

        settingLayout.add(returnStartPage, 0, 0);

        Button resetScoreboard = new Button("스코어보드 초기화");

        resetScoreboard.setOnAction(e -> {
            Reset scoreReset = new Reset();
            try {
                scoreReset.ScoreReset();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Button setControlsButton = new Button("조작키 설정");

        setControlsButton.setOnAction(e -> {
            // 조작키 설정 창을 생성
            ControlsSettingsWindow controlsSettingsWindow = new ControlsSettingsWindow();
            controlsSettingsWindow.show();
        });

        settingLayout.add(setControlsButton, 0, 2);

        Button adjustScreenSizeButton = new Button("게임 화면 크기 조절");

        adjustScreenSizeButton.setOnAction(e -> {
            // 화면 크기 조절 창을 생성
            ScreenSizeSettingsWindow screenSizeSettingsWindow = new ScreenSizeSettingsWindow();
            screenSizeSettingsWindow.show();
        });

        settingLayout.add(adjustScreenSizeButton, 0, 3); // 이 부분 수정

        settingLayout.add(resetScoreboard, 0, 1);

        Scene settingPage = new Scene(settingLayout, 311, 621);

        primaryStage.setScene(settingPage);
        primaryStage.setTitle("Setting");
        primaryStage.show();
    }
}
