package com.example.SaveFile;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreBoardData {
    String[][] stu_arr=new String[10][2];
    String name;
    int score;

    //파일로 저장하는 것
    public static void SaveToFile(String text, int score) {
        String filePath = "src/main/java/com/example/SaveFile/score.txt";
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            lines.add(String.format("%d,%s", score, text));

            Collections.sort(lines, (a, b) -> {
                int scoreA = Integer.parseInt(a.split(",")[0]);
                int scoreB = Integer.parseInt(b.split(",")[0]);
                return Integer.compare(scoreB, scoreA);
            });

            if (lines.size() > 10) {
                lines.subList(10, lines.size()).clear();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
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



    public static void saveRancking(TextField textField,int score) {
        String text = textField.getText();
        SaveToFile(text,score);
    }

    public String getDataAsString() {
        return String.format("점수: %10d             이름: %s\n\n", this.score, this.name);
    }
    public static void loadRanking(TextArea textArea) throws Exception {
        ScoreBoardData obj = new ScoreBoardData();
        obj.getScore();

        StringBuilder stringBuilder = new StringBuilder();

        int i = 0;
        while (i < 10) {
            obj.getScr(i);
            stringBuilder.append(obj.getDataAsString());
            i++;
        }

        textArea.setText(stringBuilder.toString());
    }
    public void getScr(int i){
        if (stu_arr[i][0] != null) {
            this.score = Integer.parseInt(stu_arr[i][0]);
        } else {
            this.score = 0;
        }
        this.name = stu_arr[i][1];
    }


    public void getScore() throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/example/SaveFile/score.txt"));
            String str;

            int i = 0;
            while ((str = br.readLine()) != null) {
                this.stu_arr[i] = str.split(",");
                i++;
            }

            br.close(); // 파일을 닫습니다.
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage());
        }
    }

}
