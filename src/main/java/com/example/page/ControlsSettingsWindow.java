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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        String[] keyName = SaveSetting.loadKeySettingsFromFile();
        // 각 조작키에 대한 레이블과 텍스트 필드를 생성
        TextField[] keyFields = new TextField[11]; // 텍스트 필드 배열 크기 변경

        // 중복된 키를 추적하기 위한 Set 생성
        ObservableSet<KeyCode> usedKeys = FXCollections.observableSet();

        HBox mainLayout = new HBox(); // 메인 VBox 생성
        mainLayout.setSpacing(10 * size());

        VBox player1Column = new VBox(); // 플레이어 1 컬럼
        VBox player2Column = new VBox(); // 플레이어 2 컬럼

        Button saveButton = new Button("저장");
        saveButton.setStyle("-fx-font-size: "+12*size()+"px;-fx-pref-width: "+141*size()+"px; -fx-pref-height: "+10*size()+"px;");
        saveButton.setOnAction(e -> {
            // 중복된 키가 있는지 확인하고 경고창을 표시
            for (int i = 0; i < 11; i++) {
                String currentKey = keyFields[i].getText().toUpperCase(); // 현재 텍스트 상자의 텍스트
                for (int j = 0; j < 11; j++) {
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
            String[] keyNames = new String[11]; // 텍스트 필드에 입력된 값들을 저장할 배열 크기 변경
            for (int i = 0; i < 11; i++) {
                try {
                    keyNames[i] = keyFields[i].getText().toUpperCase(); // 대문자로 변환한 값을 저장
                } catch (IllegalArgumentException ex) {
                    System.out.println("Invalid key: " + keyFields[i].getText());
                }
            }
            SaveSetting.saveKeySettingsToFile(keyNames);
            window.close();
        });

        for (int i = 0; i < 11; i++) {
            Label keyLabel = new Label();
            keyLabel.setStyle("-fx-font-family: 'Courier New';-fx-font-size: "+12*size()+"px;");
            TextField keyField = new TextField();
            keyFields[i] = keyField; // 텍스트 필드 배열에 추가
            keyField.setStyle("-fx-font-size: "+12*size()+"px;-fx-pref-width: "+70*size()+"px; -fx-pref-height: "+10*size()+"px;");
            keyField.setText(keyName[i]); // 초기값 설정
            keyField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty() && !Character.isUpperCase(newValue.charAt(0))) {
                    keyField.setText(oldValue); // 이전 값으로 되돌림
                }
            });


            keyField.setOnKeyPressed(event -> {
                KeyCode pressedKey = event.getCode();
                keyField.setText(pressedKey.toString().toUpperCase()); // 대문자로 변환하여 표시
                usedKeys.add(pressedKey); // 새로운 키를 추가
            });

            if (i < 5 || i == 10) { // 플레이어 1의 키 설정
                switch (i) {
                    case 0:
                        keyLabel.setText("P1 UP:    ");
                        break;
                    case 1:
                        keyLabel.setText("P1 DOWN:  ");
                        break;
                    case 2:
                        keyLabel.setText("P1 LEFT:  ");
                        break;
                    case 3:
                        keyLabel.setText("P1 RIGHT: ");
                        break;
                    case 4:
                        keyLabel.setText("P1 FALL:  ");
                        break;
                    case 10:
                        keyLabel.setText("PAUSE:    ");
                        break;
                    default:
                        break;
                }
                player1Column.getChildren().addAll(createRow(keyLabel, keyField));
            } else { // 플레이어 2의 키 설정
                switch (i) {
                    case 5:
                        keyLabel.setText("P2 UP:    ");
                        break;
                    case 6:
                        keyLabel.setText("P2 DOWN:  ");
                        break;
                    case 7:
                        keyLabel.setText("P2 LEFT:  ");
                        break;
                    case 8:
                        keyLabel.setText("P2 RIGHT: ");
                        break;
                    case 9:
                        keyLabel.setText("P2 FALL:  ");
                        break;
                    default:
                        break;
                }
                player2Column.getChildren().addAll(createRow(keyLabel, keyField));

            }
            if (i == 10) { // 플레이어 2의 컬럼이 끝나면 저장 버튼 추가
                player2Column.getChildren().add(saveButton);
            }
        }

        mainLayout.getChildren().addAll(player1Column, player2Column); // 컬럼을 메인 VBox에 추가

        Scene scene = new Scene(mainLayout, 300 * size(), 150 * size());
        window.setScene(scene);
        window.showAndWait();
    }

    // HBox를 생성하는 메서드
    private HBox createRow(Label label, TextField textField) {
        HBox row = new HBox();
        row.getChildren().addAll(label, textField);
        return row;
    }


}
