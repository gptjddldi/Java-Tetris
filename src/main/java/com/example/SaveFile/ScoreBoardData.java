package com.example.SaveFile;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.io.*;

public class ScoreBoardData {
    String[][] stu_arr=new String[10][2];     //2차원 배열 정의
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(String.format("%d", score));//여기에 점수 받아오면됨
            writer.write("," + text);
            writer.newLine();

            writer.flush();
            writer.close();
            //txt파일에서 숫자 높은순으로 정렬 만들어야됨
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

        int i = 1;
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
