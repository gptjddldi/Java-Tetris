package com.example.page;

import com.example.javatetris.TetrisApplication;
import com.example.javatetris.TetrisBattleApplication;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.example.SaveFile.size.size;

public class SelectBattleMode extends Application {
    public static String battlemode;



    @Override
    public void start(Stage primaryStage) {
        VBox SelectBattleModeLayout = new VBox(50 * size());

        SelectBattleModeLayout.setAlignment(Pos.CENTER);
        SelectBattleModeLayout.setPadding(new Insets(20 * size()));
        Button NORMALButton = createMenuButton("일반모드");
        Button ITEMButton = createMenuButton("아이템모드");
        Button timeATTACKButton = createMenuButton("타임어택모드");

        SelectBattleModeLayout.getChildren().addAll(NORMALButton,ITEMButton,timeATTACKButton);
        NORMALButton.setOnAction(e -> {
            battlemode = "normal";
            changeScene(new TetrisBattleApplication(battlemode), primaryStage);
        });
        ITEMButton.setOnAction(e -> {
            battlemode = "item";
            changeScene(new TetrisBattleApplication(battlemode), primaryStage);
        });
        timeATTACKButton.setOnAction(e -> {
            battlemode = "time";
            changeScene(new TetrisBattleApplication(battlemode), primaryStage);
        });



        Scene SelectBattleMode = new Scene(SelectBattleModeLayout, 290 * size(), 492 * size());
        primaryStage.setScene(SelectBattleMode);
        primaryStage.setTitle("SelectBattleMode");
        primaryStage.show();

    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: "+20*size()+"px; -fx-pref-width: "+150*size()+"px; -fx-pref-height: "+50*size()+"px;");
        return button;
    }

    private void changeScene(Application application, Stage primaryStage) {
        try {
            application.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
