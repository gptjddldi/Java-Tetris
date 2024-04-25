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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.example.SaveFile.size.size;


public class SettingPage extends Application {
    private ControlsSettingsWindow controlsSettingsWindow;

    @Override
    public void start(Stage primaryStage) {
        VBox settingLayout = new VBox(10*size());

        settingLayout.setAlignment(Pos.CENTER);
        settingLayout.setPadding(new Insets(20*size()));

        Button returnStartPage = new Button("저장 및 메인메뉴");
        returnStartPage.setStyle("-fx-font-size: "+12*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+30*size()+"px;");
        returnStartPage.setOnAction(e -> {
            StartPage startScreen = new StartPage();
            startScreen.start(primaryStage);
        });

        Button resetScoreboard = new Button("스코어보드 초기화");
        resetScoreboard.setStyle("-fx-font-size: "+12*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+30*size()+"px;");
        resetScoreboard.setOnAction(e -> {
            Reset scoreReset = new Reset();
            scoreReset.ScoreReset();
            StartPage startScreen = new StartPage();
            startScreen.start(primaryStage);
        });

        Button setControlsButton = new Button("조작키 설정");
        setControlsButton.setStyle("-fx-font-size: "+12*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+30*size()+"px;");
        controlsSettingsWindow = new ControlsSettingsWindow();
        setControlsButton.setOnAction(e -> controlsSettingsWindow.showAndWait());


        CheckBox colorBlindCheckBox = new CheckBox("색맹모드");
        colorBlindCheckBox.setStyle("-fx-font-size: "+15*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+30*size()+"px;");
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
        ComboBox<String> screenSizeComboBox;
        String[] screenSizes = {"SMALL", "NORMAL", "BIG"};
        screenSizeComboBox = new ComboBox<>();
        screenSizeComboBox.setStyle("-fx-font-size: "+12*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+30*size()+"px;");
        screenSizeComboBox.getItems().addAll(screenSizes);
        String screenSize = SaveSetting.loadOneSettingFromFile(9);
        screenSizeComboBox.setValue(screenSize);
        screenSizeComboBox.setOnAction(e -> {
            String selectedsize = screenSizeComboBox.getValue();
            SaveSetting.saveOneSettingsToFile(selectedsize, 9);
        });

        ComboBox<String> levelComboBox;
        String[] levels = {"EASY", "NORMAL", "HARD"};
        levelComboBox = new ComboBox<>();
        levelComboBox.setStyle("-fx-font-size: "+12*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+30*size()+"px;");
        levelComboBox.getItems().addAll(levels);
        String level = SaveSetting.loadOneSettingFromFile(8);
        levelComboBox.setValue(level);


        levelComboBox.setOnAction(e -> {
            String selectedLevel = levelComboBox.getValue();
            SaveSetting.saveOneSettingsToFile(selectedLevel, 8);
        });


        Button resetSetting = new Button("세팅 초기화");
        resetSetting.setStyle("-fx-font-size: "+12*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+30*size()+"px;");
        resetSetting.setOnAction(e -> {
            Reset settingReset = new Reset();
            settingReset.SettingReset();
            StartPage startScreen = new StartPage();
            startScreen.start(primaryStage);
        });
        Label sizeLabel = new Label("화면크기");
        sizeLabel.setStyle("-fx-font-size: "+15*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+30*size()+"px;");

        Label levelLabel = new Label("난이도");
        levelLabel.setStyle("-fx-font-size: "+15*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+30*size()+"px;");
        settingLayout.getChildren().addAll(sizeLabel,screenSizeComboBox, setControlsButton,
                colorBlindCheckBox, levelLabel, levelComboBox, resetSetting, resetScoreboard,returnStartPage);

        Scene settingPage = new Scene(settingLayout, 292*size(), 492*size());
        primaryStage.setScene(settingPage);
        primaryStage.setTitle("Setting");
        primaryStage.show();
    }
}

