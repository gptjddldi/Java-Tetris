package com.example.page;

import com.example.SaveFile.SaveSetting;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.example.SaveFile.size.size;

public class ControlsSettingsWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showAndWait();
    }

    public void showAndWait() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("조작키 설정");

        GridPane layout = new GridPane();
        layout.setVgap(10*size());
        layout.setHgap(10*size());
        String[] keyName = SaveSetting.loadKeySettingsFromFile();
        // 각 조작키에 대한 레이블과 텍스트 필드를 생성
        KeyCode[] defaultKeys = {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE, KeyCode.X};
        TextField[] keyFields = new TextField[6]; // 텍스트 필드 배열 크기 변경

        // 중복된 키를 추적하기 위한 Set 생성
        ObservableSet<KeyCode> usedKeys = FXCollections.observableSet();

        for (int i = 0; i < defaultKeys.length; i++) {
            Label keyLabel;
            if (i == 4) {
                keyLabel = new Label("PAUSE: ");
                keyLabel.setStyle("-fx-font-size: "+12*size()+"px;");
            } else if (i == 5) {
                keyLabel = new Label("fall at once: ");
                keyLabel.setStyle("-fx-font-size: "+12*size()+"px;");
            } else {
                keyLabel = new Label(defaultKeys[i] + ": ");
                keyLabel.setStyle("-fx-font-size: "+12*size()+"px;");
            }
            keyFields[i] = new TextField(); // 텍스트 필드 생성
            keyFields[i].setStyle("-fx-font-size: "+12*size()+"px;-fx-pref-width: "+70*size()+"px; -fx-pref-height: "+10*size()+"px;");
            keyFields[i].setText(keyName[i]); // 초기값 설정
            int finalI1 = i;
            keyFields[i].textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty() && !Character.isUpperCase(newValue.charAt(0))) {
                    keyFields[finalI1].setText(oldValue); // 이전 값으로 되돌림
                }
            });


            final int finalI = i; // 람다식 내에서 변수 i를 사용하기 위해 필요
            // 키 입력 이벤트 처리를 위해 키 리스너 추가
            keyFields[i].setOnKeyPressed(event -> {
                KeyCode pressedKey = event.getCode();
                keyFields[finalI].setText(pressedKey.toString().toUpperCase()); // 대문자로 변환하여 표시
                usedKeys.remove(defaultKeys[finalI]); // 기존에 사용된 키를 제거
                usedKeys.add(pressedKey); // 새로운 키를 추가
            });

            layout.addRow(i, keyLabel, keyFields[i]); // 레이블과 텍스트 필드를 레이아웃에 추가
        }

        // 저장 버튼 추가
        Button saveButton = new Button("저장");
        saveButton.setStyle("-fx-font-size: "+12*size()+"px;-fx-pref-width: "+70*size()+"px; -fx-pref-height: "+10*size()+"px;");
        saveButton.setOnAction(e -> {
            // 중복된 키가 있는지 확인하고 경고창을 표시
            for (int i = 0; i < defaultKeys.length; i++) {
                String currentKey = keyFields[i].getText().toUpperCase(); // 현재 텍스트 상자의 텍스트
                for (int j = 0; j < defaultKeys.length; j++) {
                    if (i != j) { // 현재 인덱스가 아닌 다른 모든 인덱스와 비교
                        String comparedKey = keyFields[j].getText().toUpperCase(); // 다른 텍스트 상자의 텍스트
                        if (currentKey.equals(comparedKey)) { // 현재 텍스트와 다른 텍스트가 같으면 중복
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("중복된 키");
                            alert.setHeaderText("중복된 키가 있습니다.");
                            alert.setContentText("다른 키를 선택하세요.");
                            alert.showAndWait();
                            return; // 중복이 발견되면 저장을 중단하고 메서드를 종료
                        }
                    }
                }
            }

            // 변경된 조작키를 저장
            String[] keyNames = new String[6]; // 텍스트 필드에 입력된 값들을 저장할 배열 크기 변경
            for (int i = 0; i < defaultKeys.length; i++) {
                try {
                    keyNames[i] = keyFields[i].getText().toUpperCase(); // 대문자로 변환한 값을 저장
                } catch (IllegalArgumentException ex) {
                    System.out.println("Invalid key: " + keyFields[i].getText());
                    keyNames[i] = defaultKeys[i].getName(); // 기본값을 저장
                }
            }
            SaveSetting.saveKeySettingsToFile(keyNames);
            window.close();
        });

        layout.add(saveButton, 1, defaultKeys.length); // 저장 버튼을 레이아웃에 추가

        Scene scene = new Scene(layout, 170*size(), 230*size());
        window.setScene(scene);
        window.showAndWait();
    }


}