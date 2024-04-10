package com.example.SaveFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;

public class ScoreBoardData {
    String[][] stu_arr=new String[10][2];
    String name;
    int score;
    public static int lastEnteredScore;
    public static String lastEnteredName;

    // 파일로 저장하는 것
    public static void SaveToFile(String text, int score) {
        String filePath = "src/main/java/com/example/SaveFile/score.txt";
        try {
            File file = new File(filePath);
            if (!file.exists()) {    // 파일 없으면 생성
                file.createNewFile();
            }

            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(file)); // 파일 읽기
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close(); // 파일 읽기 종료

            lines.add(String.format("%d,%s", score, text));

            Collections.sort(lines, (a, b) -> { // 점수 sorting
                int scoreA = Integer.parseInt(a.split(",")[0]);
                int scoreB = Integer.parseInt(b.split(",")[0]);
                return Integer.compare(scoreB, scoreA);
            });

            if (lines.size() > 10) { // 10등까지 출력
                lines.subList(10, lines.size()).clear();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file)); // 파일에 작성
            for (String sortedLine : lines) {
                writer.write(sortedLine);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveRanking(String name, int score) {
        String text = name;
        SaveToFile(text, score);

        lastEnteredScore = score;
        lastEnteredName = name;
    }

    public static void loadRanking(ScrollPane scrollPane) throws Exception {
        ScoreBoardData obj = new ScoreBoardData();
        obj.getScore();

        VBox container = new VBox();

        for (int i = 0; i < 10; i++) {
            obj.getScr(i);

            Label label = new Label("점수: " + obj.score + "             이름: " + obj.name);
            if (obj.score == lastEnteredScore && obj.name.equals(lastEnteredName)) {
                label.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
            }

            container.getChildren().add(label);
        }

        scrollPane.setContent(container);
    }
    public static void HomeloadRanking(ScrollPane scrollPane) throws Exception {
        ScoreBoardData obj = new ScoreBoardData();
        obj.getScore();

        VBox container = new VBox();

        for (int i = 0; i < 10; i++) {
            obj.getScr(i);

            Label label = new Label("점수: " + obj.score + "             이름: " + obj.name);
            container.getChildren().add(label);
        }

        scrollPane.setContent(container);
    }

    public void getScr(int i){
        if (stu_arr[i][0] != null) {
            this.score = Integer.parseInt(stu_arr[i][0]);
        } else {
            this.score = 0;
        }
        this.name = stu_arr[i][1];
    }

    public void getScore() throws IOException { // 데이터 읽어오는 핵심 코드
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/example/SaveFile/score.txt"));
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