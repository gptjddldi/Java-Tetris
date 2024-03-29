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
            if (!file.exists()) {    //파일 업으면 파일 생성
                file.createNewFile();
            }

            List<String> lines = new ArrayList<>();// 파일 list로 받기
            BufferedReader reader = new BufferedReader(new FileReader(file)); //파일 읽기
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();//파일 읽기 종료

            lines.add(String.format("%d,%s", score, text));

            Collections.sort(lines, (a, b) -> { //점수 sorting
                int scoreA = Integer.parseInt(a.split(",")[0]);
                int scoreB = Integer.parseInt(b.split(",")[0]);
                return Integer.compare(scoreB, scoreA);
            });

            if (lines.size() > 10) { //10등까지 출력
                lines.subList(10, lines.size()).clear();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));//파일에 작성
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



    public static void saveRancking(TextField textField,int score) { //textfield의 데이터 가져오기
        String text = textField.getText();
        SaveToFile(text,score);
    }

    public String getDataAsString() { // 읽은 데이터 출력 방법
        return String.format("점수: %10d             이름: %s\n\n", this.score, this.name);
    }
    public static void loadRanking(TextArea textArea) throws Exception {
        ScoreBoardData obj = new ScoreBoardData();  // ScoreBoardData 객체 생성
        obj.getScore();

        StringBuilder stringBuilder = new StringBuilder(); // 문자열을 저장할 StringBuilder 객체 생성

        int i = 0;
        while (i < 10) {// i번째 점수를 가져와서 StringBuilder에 추가
            obj.getScr(i);
            stringBuilder.append(obj.getDataAsString());
            i++;
        }

        textArea.setText(stringBuilder.toString());//textarea에 출력
    }

    public void getScr(int i){
        if (stu_arr[i][0] != null) {// 데이터가 null일때 처리 방법(0으로 출력)
            this.score = Integer.parseInt(stu_arr[i][0]);
        } else {
            this.score = 0;
        }
        this.name = stu_arr[i][1];
    }


    public void getScore() throws IOException {// 데이터 읽어오는 핵심 코드
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/example/SaveFile/score.txt"));
            String str;

            int i = 0;
            while ((str = br.readLine()) != null) {//값이 없을때까지 파일 읽기
                this.stu_arr[i] = str.split(",");//,로 점수 나누기
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
