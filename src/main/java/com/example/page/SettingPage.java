package com.example.page;

import com.example.SaveFile.Reset;
import com.example.SaveFile.SaveSetting;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingPage extends Application {
    private ControlsSettingsWindow controlsSettingsWindow;

    @Override
    public void start(Stage primaryStage) {
        VBox settingLayout = new VBox(10);
        settingLayout.setAlignment(Pos.TOP_LEFT);
        settingLayout.setPadding(new Insets(20));

        Button returnStartPage = new Button("메인메뉴");
        returnStartPage.setOnAction(e -> {
            StartPage startScreen = new StartPage();
            startScreen.start(primaryStage);
        });

        Button resetScoreboard = new Button("스코어보드 초기화");
        resetScoreboard.setOnAction(e -> {
            Reset scoreReset = new Reset();
            scoreReset.ScoreReset();
            StartPage startScreen = new StartPage();
            startScreen.start(primaryStage);
        });

        Button setControlsButton = new Button("조작키 설정");
        controlsSettingsWindow = new ControlsSettingsWindow();
        setControlsButton.setOnAction(e -> controlsSettingsWindow.showAndWait());

        Button adjustScreenSizeButton = new Button("게임 화면 크기 조절");
        adjustScreenSizeButton.setOnAction(e -> {
            ScreenSizeSettingsWindow screenSizeSettingsWindow = new ScreenSizeSettingsWindow();
            screenSizeSettingsWindow.show();
        });

        CheckBox colorBlindCheckBox = new CheckBox("색맹모드");
        String colorSetting = SaveSetting.loadOneSettingFromFile(7);
        if (colorSetting.equals("on")) {
            colorBlindCheckBox.setSelected(true);
        }
        colorBlindCheckBox.setOnAction(e -> {
            if (colorBlindCheckBox.isSelected()) {
                SaveSetting.saveOneSettingsToFile("on", 7);
            } else {
                SaveSetting.saveOneSettingsToFile("off", 7);
            }
        });

        ComboBox<String> levelComboBox;
        String[] levels = {"EASY", "NORMAL", "HARD", "ITEM"};
        levelComboBox = new ComboBox<>();
        levelComboBox.getItems().addAll(levels);
        String level = SaveSetting.loadOneSettingFromFile(8);
        levelComboBox.setValue(level); // 현재 설정 값으로 썸네일처럼 표시


        levelComboBox.setOnAction(e -> {
            String selectedLevel = levelComboBox.getValue();
            SaveSetting.saveOneSettingsToFile(selectedLevel, 8);
        });


        Button resetSetting = new Button("세팅 초기화");
        resetSetting.setOnAction(e -> {
            Reset settingReset = new Reset();
            settingReset.SettingReset();
            StartPage startScreen = new StartPage();
            startScreen.start(primaryStage);
        });

        settingLayout.getChildren().addAll(returnStartPage, adjustScreenSizeButton, setControlsButton,
                colorBlindCheckBox, new Label("난이도"), levelComboBox, resetSetting, resetScoreboard);

        Scene settingPage = new Scene(settingLayout, 311, 621);
        primaryStage.setScene(settingPage);
        primaryStage.setTitle("Setting");
        primaryStage.show();
    }
}

