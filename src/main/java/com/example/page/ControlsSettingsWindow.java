package com.example.page;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.example.SaveFile.SaveSetting.saveKeySettingsToFile;

public class ControlsSettingsWindow {

    public void showAndWait() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("조작키 설정");

        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);

        // 각 조작키에 대한 레이블과 텍스트 필드를 생성
        KeyCode[] defaultKeys = {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT}; // 기본 조작키 목록
        TextField[] keyFields = new TextField[4];
        for (int i = 0; i < defaultKeys.length; i++) {
            Label keyLabel = new Label(defaultKeys[i] + ": ");
            keyFields[i] = new TextField(); // 텍스트 필드 생성
            keyFields[i].setText(defaultKeys[i].getName()); // 초기값 설정
            layout.addRow(i, keyLabel, keyFields[i]); // 레이블과 텍스트 필드를 레이아웃에 추가
        }

        // 저장 버튼 추가
        Button saveButton = new Button("저장");
        saveButton.setOnAction(e -> {
            // 변경된 조작키를 저장
            String[] keyNames = new String[4]; // 텍스트 필드에 입력된 값들을 저장할 배열 생성
            for (int i = 0; i < defaultKeys.length; i++) {
                try {
                    keyNames[i] = keyFields[i].getText().toUpperCase(); // 대문자로 변환한 값을 저장
                } catch (IllegalArgumentException ex) {
                    System.out.println("Invalid key: " + keyFields[i].getText());
                    keyNames[i] = defaultKeys[i].getName(); // 기본값을 저장
                }
            }
            saveKeySettingsToFile(keyNames);
            window.close();
        });

        layout.add(saveButton, 1, defaultKeys.length); // 저장 버튼을 레이아웃에 추가

        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.showAndWait();
    }

}
