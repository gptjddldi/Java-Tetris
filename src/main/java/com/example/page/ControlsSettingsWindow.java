package com.example.page;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControlsSettingsWindow {

    public void show() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("조작키 설정");

        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);

        // 각 조작키에 대한 레이블과 텍스트 필드를 생성
        String[] controlKeys = {"UP", "DOWN", "LEFT", "RIGHT", "BOTTOM", "ROTATE"}; // 조작키 목록
        TextField[] keyFields = new TextField[6];
        for (int i = 0; i < controlKeys.length; i++) {
            Label keyLabel = new Label(controlKeys[i] + ": ");
            keyFields[i] = new TextField(); // 텍스트 필드 생성
            keyFields[i].setText(controlKeys[i]); // 초기값 설정
            layout.addRow(i, keyLabel, keyFields[i]); // 레이블과 텍스트 필드를 레이아웃에 추가
        }


        // 취소 버튼 추가
        Button cancelButton = new Button("취소");
        cancelButton.setOnAction(e -> {
            // 각 텍스트 필드의 값을 초기값으로 되돌림
            for (int i = 0; i < controlKeys.length; i++) {
                keyFields[i].setText(controlKeys[i]);
            }
            // 창 닫기
            window.close();
        });

        layout.add(cancelButton, 2, controlKeys.length); // 취소 버튼을 레이아웃에 추가

        // 저장 버튼 추가
        Button saveButton = new Button("저장");
        saveButton.setOnAction(e -> {
            // 각 텍스트 필드에 입력된 조작키를 가져와서 저장하거나 처리
            for (TextField keyField : keyFields) {
                String key = keyField.getText();
                // 이후 처리 작업을 추가
                System.out.println("설정된 조작키: " + key);
            }
            // 창 닫기
            window.close();
        });

        layout.add(saveButton, 1, controlKeys.length); // 저장 버튼을 레이아웃에 추가

        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.showAndWait();
    }
}

