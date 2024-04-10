package com.example.page;

import com.example.SaveFile.Reset;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingPage extends Application {

    private boolean colorBlindMode;
    private ControlsSettingsWindow controlsSettingsWindow; // ControlsSettingsWindow 인스턴스 추가
    private KeyCode[] controlKeys; // 조작키 배열 추가

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

        // ControlsSettingsWindow 인스턴스 생성
        controlsSettingsWindow = new ControlsSettingsWindow();

        setControlsButton.setOnAction(e -> {
            // 조작키 설정 창을 보여줌
            controlsSettingsWindow.showAndWait();
            // 설정된 조작키 가져오기
            controlKeys = controlsSettingsWindow.getNewKeys();
        });

        settingLayout.add(setControlsButton, 0, 2);

        Button adjustScreenSizeButton = new Button("게임 화면 크기 조절");

        adjustScreenSizeButton.setOnAction(e -> {
            // 화면 크기 조절 창을 생성
            ScreenSizeSettingsWindow screenSizeSettingsWindow = new ScreenSizeSettingsWindow();
            screenSizeSettingsWindow.show();
        });

        Button colorBlindButton = new Button("색맹 모드: 꺼짐");
        colorBlindButton.setOnAction(e -> {
            colorBlindMode = !colorBlindMode;
            if (colorBlindMode) {
                colorBlindButton.setText("색맹 모드: 켜짐");
                // 여기에 색맹 모드를 적용하는 코드 추가
            } else {
                colorBlindButton.setText("색맹 모드: 꺼짐");
                // 여기에 일반 모드로 전환하는 코드 추가
            }
        });

        settingLayout.add(colorBlindButton, 0, 4);

        settingLayout.add(adjustScreenSizeButton, 0, 3);

        settingLayout.add(resetScoreboard, 0, 1);

        Scene settingPage = new Scene(settingLayout, 311, 621);

        primaryStage.setScene(settingPage);
        primaryStage.setTitle("Setting");
        primaryStage.show();
    }

    // 색맹 모드 적용 메서드
}

