package com.example.page;

import com.example.SaveFile.SaveSetting;
import com.example.javatetris.TetrisApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.example.SaveFile.size.size;

public class StartPage extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public static String mode;
    public static String colormode;
    @Override
    public void start(Stage primaryStage) {
        VBox startLayout = new VBox();
        startLayout.setAlignment(Pos.CENTER);
        startLayout.setSpacing(20*size());
        startLayout.setPadding(new Insets(20*size()));
        Label gameTitleLabel = new Label("Java Tetris");
        gameTitleLabel.setStyle("-fx-font-size: "+ 40*size()+"px; -fx-font-weight: bold;");
        Button startButton = createMenuButton("일반모드");
        Button startITEMButton = createMenuButton("아이템모드");
        Button settingButton = createMenuButton("설정");
        Button scoreboardButton = createMenuButton("스코어보드");
        Button exitButton = createMenuButton("게임종료");

        startLayout.getChildren().addAll(gameTitleLabel, startButton, startITEMButton, settingButton, scoreboardButton, exitButton);

        Scene startPage = new Scene(startLayout,292 *size(), 492*size());
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

        startButton.setOnAction(e -> {
            mode = "normal";
            colormode = SaveSetting.loadOneSettingFromFile(7);
            changeScene(new TetrisApplication(), primaryStage);
        });
        startITEMButton.setOnAction(e -> {
            mode = "item";
            colormode = SaveSetting.loadOneSettingFromFile(7);
            changeScene(new TetrisApplication(), primaryStage);
        });
        settingButton.setOnAction(e -> {
            changeScene(new SettingPage(), primaryStage);
        });
        scoreboardButton.setOnAction(e -> changeScene(new ScoreBoardAtStartPage(), primaryStage));
        exitButton.setOnAction(e -> Platform.exit());
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: "+20*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+50*size()+"px;");
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