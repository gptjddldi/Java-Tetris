package com.example.page;

import com.example.SaveFile.ScoreBoardData;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static com.example.SaveFile.size.size;
import static com.example.page.StartPage.mode;

public class ScoreBoardAtGameEnd extends Application {
    private boolean submitButtonClicked = false;
    private int score;

    public ScoreBoardAtGameEnd() {
        super();
    }

    public ScoreBoardAtGameEnd(int score) {
        this();
        setScore(score);
    }

    public int getPlace10(int a) {
        int value = 0;
        String filePath = "src/main/java/com/example/SaveFile/score.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < a - 1; i++) {
                reader.readLine();
            }
            String line = reader.readLine();
            String[] values = line.split(",");
            reader.close();
            if (values.length > 0) {
                value = Integer.parseInt(values[0]);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane scoreBoardLayout = createScoreBoardLayout(primaryStage);
        Scene scoreBoardPage = new Scene(scoreBoardLayout, 290 * size(), 492 * size());

        primaryStage.setScene(scoreBoardPage);
        primaryStage.setTitle("scoreBoard");
        primaryStage.show();
    }

    public GridPane createScoreBoardLayout(Stage primaryStage) {
        GridPane scoreBoardLayout = new GridPane();
        scoreBoardLayout.setAlignment(Pos.CENTER);
        scoreBoardLayout.setVgap(10 * size());
        scoreBoardLayout.setHgap(10 * size());

        int normal_mode = getPlace10(10);
        int item_mode = getPlace10(20);

        if (Objects.equals(mode, "normal")) {
            if (normal_mode > score) {
                showAlertAndReturnToMainMenu(primaryStage, "10등 안에 들지 못했습니다.\n메인 메뉴로 돌아갑니다.");
                return scoreBoardLayout;
            }
        } else if (Objects.equals(mode, "item")) {
            if (item_mode > score) {
                showAlertAndReturnToMainMenu(primaryStage, "10등 안에 들지 못했습니다.\n메인 메뉴로 돌아갑니다.");
                return scoreBoardLayout;
            }
        }

        Button backButton = createBackButton(primaryStage);
        scoreBoardLayout.add(backButton, 0, 0);

        ScrollPane scrollPane = createScrollPane();
        scoreBoardLayout.add(scrollPane, 0, 1, 2, 1);

        Label label = new Label("랭킹 업데이트를 위해 이름을 입력하세요");
        label.setStyle("-fx-font-size: " + (12 * size()) + "px;");
        scoreBoardLayout.add(label, 0, 2);

        TextField inputField = createInputField();
        scoreBoardLayout.add(inputField, 0, 3);

        Button submitButton = createSubmitButton(scrollPane, inputField);
        scoreBoardLayout.add(submitButton, 1, 3);

        loadRanking(scrollPane);

        return scoreBoardLayout;
    }

    private Button createBackButton(Stage primaryStage) {
        Button backButton = new Button("메인메뉴");
        backButton.setStyle("-fx-font-size: " + 10 * size() + "px; -fx-pref-width: " + 70 * size() + "px; -fx-pref-height: " + 30 * size() + "px;");
        backButton.setOnAction(e -> {
            StartPage startPage = new StartPage();
            startPage.start(primaryStage);
        });
        return backButton;
    }

    private ScrollPane createScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-font-size: " + 12 * size() + "px;");
        scrollPane.setPrefSize(270 * size(), 385 * size());
        return scrollPane;
    }

    private TextField createInputField() {
        TextField inputField = new TextField("");
        inputField.setPrefWidth(230 * size());

        inputField.addEventFilter(KeyEvent.KEY_TYPED, event -> { // 쉼표 입력을 막음
            if (event.getCharacter().equals(",")) {
                event.consume();
            }
        });

        return inputField;
    }

    private Button createSubmitButton(ScrollPane scrollPane, TextField inputField) {
        Button submitButton = new Button("저장");
        submitButton.setStyle("-fx-font-size: " + 10 * size() + "px;");
        submitButton.setOnAction(event -> {
            if (!submitButtonClicked) {
                ScoreBoardData.saveRanking(inputField.getText(), score);
                try {
                    ScoreBoardData.loadRanking(scrollPane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                submitButtonClicked = true;
                submitButton.setDisable(true);
            }
        });
        return submitButton;
    }

    void loadRanking(ScrollPane scrollPane) {
        try {
            ScoreBoardData.HomeloadRanking(scrollPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void showAlertAndReturnToMainMenu(Stage primaryStage, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        StartPage startPage = new StartPage();
        startPage.start(primaryStage);
    }

    public void setScore(int score) {
        this.score = score;
    }
}

