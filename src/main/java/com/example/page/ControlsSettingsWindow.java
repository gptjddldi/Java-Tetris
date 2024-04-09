package com.example.page;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControlsSettingsWindow {
    private KeyCode[] newKeys;

    public void show() {
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
            newKeys = new KeyCode[4];
            for (int i = 0; i < defaultKeys.length; i++) {
                try {
                    // 사용자가 입력한 문자열을 대문자로 변환하여 KeyCode로 가져옴
                    newKeys[i] = KeyCode.valueOf(keyFields[i].getText().toUpperCase());
                } catch (IllegalArgumentException ex) {
                    // 사용자가 유효하지 않은 키를 입력한 경우 처리
                    System.out.println("Invalid key: " + keyFields[i].getText());
                    // 기본값으로 설정하거나 다른 처리를 할 수 있음
                    newKeys[i] = defaultKeys[i]; // 예시로 기본 조작키로 설정
                }
            }
            window.close();
        });

        layout.add(saveButton, 1, defaultKeys.length); // 저장 버튼을 레이아웃에 추가

        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.showAndWait();
    }

    public KeyCode[] getNewKeys() {
        return newKeys;
    }


}



