package com.example.page;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ScreenSizeSettingsWindow {

    public void show() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("게임 화면 크기 조절");

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
            // 이후 처리 작업을 추가
            System.out.println("선택된 화면 크기: " + selectedSize);
            window.close(); // 창 닫기
        });

        layout.add(screenSizeChoiceBox, 0, 0);
        layout.add(saveButton, 0, 1);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
