package com.example.page;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ScreenSizeSettingsWindow {

    private final Stage window;

    public ScreenSizeSettingsWindow(Stage primaryStage) {
        this.window = new Stage();
        this.window.initModality(Modality.APPLICATION_MODAL);
        this.window.setTitle("게임 화면 크기 조절");

        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);

        // 화면 크기 선택 ChoiceBox 생성
        ChoiceBox<String> screenSizeChoiceBox = new ChoiceBox<>();
        screenSizeChoiceBox.getItems().addAll("800x600", "1024x768", "1280x720");
        screenSizeChoiceBox.setValue("800x600"); // 기본값 설정

        // 저장 버튼 추가
        Button saveButton = new Button("저장");
        saveButton.setOnAction(e -> {
            // 선택된 화면 크기를 가져와서 처리
            String selectedSize = screenSizeChoiceBox.getValue();
            // 화면 크기를 변경
            applyScreenSize(selectedSize, primaryStage);
            window.close(); // 창 닫기
        });

        layout.add(screenSizeChoiceBox, 0, 0);
        layout.add(saveButton, 0, 1);

        Scene scene = new Scene(layout);
        window.setScene(scene);
    }

    public void show() {
        window.showAndWait();
    }

    private void applyScreenSize(String selectedSize, Stage primaryStage) {
        // 선택된 화면 크기를 파싱하여 너비와 높이를 얻음
        String[] sizeParts = selectedSize.split("x");
        int width = Integer.parseInt(sizeParts[0]);
        int height = Integer.parseInt(sizeParts[1]);

        // 주어진 너비와 높이로 primaryStage의 크기를 변경
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }
}


