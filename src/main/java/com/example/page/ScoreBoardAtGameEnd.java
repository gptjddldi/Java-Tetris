package com.example.page;

import com.example.SaveFile.ScoreBoardData;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ScoreBoardAtGameEnd extends Application {
    private boolean submitButtonClicked = false;
    private int score;

    public static void main(String[] args) {
        launch(args);
    }

    public ScoreBoardAtGameEnd() {
        super();
    }
    public ScoreBoardAtGameEnd(int score) {
        this();
        setScore(score);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane scoreBoardLayout = new GridPane();
        scoreBoardLayout.setAlignment(Pos.CENTER);
        scoreBoardLayout.setVgap(10);
        scoreBoardLayout.setHgap(10);

        Button backButton = new Button("MainMenu");

        backButton.setOnAction(e -> {
            StartPage startPage = new StartPage();
            startPage.start(primaryStage);
        });

        scoreBoardLayout.add(backButton, 0, 0);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(300, 500);
        scoreBoardLayout.add(scrollPane, 0, 3, 2, 1);


        Label label = new Label("랭킹 업데이트를 위해 이름을 입력하세요");
        scoreBoardLayout.add(label, 0, 4);

        TextField inputField = new TextField("");
        inputField.setPrefWidth(250);

        inputField.addEventFilter(KeyEvent.KEY_TYPED, event -> { // 쉼표 입력을 막음
            if (event.getCharacter().equals(",")) {
                event.consume();
            }
        });

        Button submitButton = new Button("저장");
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

        scoreBoardLayout.add(inputField, 0, 5);
        scoreBoardLayout.add(submitButton, 1, 5);

        Scene scoreBoardPage = new Scene(scoreBoardLayout, 311, 621);

        primaryStage.setScene(scoreBoardPage);
        primaryStage.setTitle("scoreBoard");
        primaryStage.show();
    }

    public void setScore(int score) {
        this.score = score;
    }
}
