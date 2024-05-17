package com.example.SaveFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import com.example.page.StartPage;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;

public class ScoreBoardData {
    String[][] stu_arr = new String[21][3];
    String name;
    int score;
    String level;
    public static int lastEnteredScore;
    public static String lastEnteredName;
    private static String FILE_PATH = SaveSetting.getFileAbsolutePath("src/main/java/com/example/SaveFile/score.txt");

    // 파일 경로 설정 메서드
    public static void setFilePath(String path) {
        FILE_PATH = path;
    }

    public static void SaveToFile(String text, int score) {
        try {
            String gameLevel = SaveSetting.loadOneSettingFromFile(13);
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

            lines.add(String.format("%d,%s,%s", score, text, gameLevel));
            String mode = StartPage.mode;
            if (Objects.equals(mode, "item")) {
                int startIndex = Math.min(10, lines.size());
                Collections.sort(lines.subList(startIndex, lines.size()), (a, b) -> {
                    int scoreA = Integer.parseInt(a.split(",")[0]);
                    int scoreB = Integer.parseInt(b.split(",")[0]);
                    return Integer.compare(scoreB, scoreA);
                });
                if (lines.size() > 20) {
                    lines.subList(20, lines.size()).clear();
                }
            } else if (Objects.equals(mode, "normal")) {
                List<String> tempLines = new ArrayList<>(lines);
                int endIndex = Math.min(10, tempLines.size());
                List<String> newList = new ArrayList<>(tempLines.subList(0, endIndex));
                newList.add(tempLines.get(tempLines.size() - 1));
                Collections.sort(newList, (a, b) -> {
                    int scoreA = Integer.parseInt(a.split(",")[0]);
                    int scoreB = Integer.parseInt(b.split(",")[0]);
                    return Integer.compare(scoreB, scoreA);
                });

                if (newList.size() > 10) {
                    newList.remove(10);
                }
                List<String> combinedList = new ArrayList<>(tempLines.subList(10, Math.min(20, tempLines.size())));
                newList.addAll(combinedList);
                lines.clear();
                lines.addAll(newList);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String sortedLine : lines) {
                    writer.write(sortedLine);
                    writer.newLine();
                }
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveRanking(String name, int score) {
        SaveToFile(name, score);
        lastEnteredScore = score;
        lastEnteredName = name;
    }

    public static void loadRanking(ScrollPane scrollPane) throws Exception {
        ScoreBoardData obj = new ScoreBoardData();
        obj.getScore();

        VBox container = new VBox();
        Label mode_level = new Label("<일반 모드>");
        container.getChildren().add(mode_level);
        for (int i = 0; i < 10; i++) {
            obj.getScr(i);
            Label label = new Label(String.format("점수: %-10d이름: %s%s", obj.score, obj.name, obj.level));
            label.setStyle("-fx-font-family: 'Courier New';-fx-font-weight: bold; -fx-font-size: " + 12 * size.size() + "px;");
            if (obj.score == lastEnteredScore && obj.name.equals(lastEnteredName)) {
                label.setStyle("-fx-font-family: 'Courier New';-fx-font-weight: bold;-fx-text-fill: red;-fx-font-size: " + 12 * size.size() + "px;");
            }
            container.getChildren().add(label);
        }
        Label mode_item = new Label("\n<아이템 모드>");
        container.getChildren().add(mode_item);
        for (int i = 10; i < 20; i++) {
            obj.getScr(i);
            Label label = new Label(String.format("점수: %-10d이름: %s%s", obj.score, obj.name, obj.level));
            label.setStyle("-fx-font-family: 'Courier New';-fx-font-weight: bold; -fx-font-size: " + 12 * size.size() + "px;");
            if (obj.score == lastEnteredScore && obj.name.equals(lastEnteredName)) {
                label.setStyle("-fx-font-family: 'Courier New';-fx-font-weight: bold;-fx-text-fill: red;-fx-font-size: " + 12 * size.size() + "px;");
            }
            container.getChildren().add(label);
        }
        scrollPane.setContent(container);
    }

    public static void HomeloadRanking(ScrollPane scrollPane) throws Exception {
        ScoreBoardData obj = new ScoreBoardData();
        obj.getScore();

        VBox container = new VBox();
        Label mode_level = new Label("<일반 모드>");
        container.getChildren().add(mode_level);
        for (int i = 0; i < 10; i++) {
            obj.getScr(i);
            Label label = new Label(String.format("점수: %-10d이름: %s%s" + "\n", obj.score, obj.name, obj.level));
            label.setStyle("-fx-font-family: 'Courier New'; -fx-font-weight: bold;-fx-font-size: " + 12 * size.size() + "px;");
            container.getChildren().add(label);
        }
        Label mode_item = new Label("\n<아이템 모드>");
        container.getChildren().add(mode_item);
        for (int i = 10; i < 20; i++) {
            obj.getScr(i);
            Label label = new Label(String.format("점수: %-10d이름: %s%s", obj.score, obj.name, obj.level));
            label.setStyle("-fx-font-family: 'Courier New'; -fx-font-weight: bold;-fx-font-size: " + 12 * size.size() + "px;");
            container.getChildren().add(label);
        }
        scrollPane.setContent(container);
    }

    public void getScr(int i) {
        if (stu_arr[i] != null && stu_arr[i].length >= 3) { // Check if array and its length are valid
            this.score = Integer.parseInt(stu_arr[i][0]);
            this.name = stu_arr[i][1];
            this.level = "(" + stu_arr[i][2] + ")";
        } else {
            this.score = 0;
            this.name = "";
            this.level = ""; // Or any default value you prefer
        }
    }

    public void getScore() throws IOException { // 데이터 읽어오는 핵심 코드
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String str;
            int i = 0;
            while ((str = br.readLine()) != null) { // 값이 없을 때까지 파일 읽기
                this.stu_arr[i] = str.split(","); // ,로 점수 나누기
                i++;
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}