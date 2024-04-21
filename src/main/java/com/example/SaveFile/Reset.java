package com.example.SaveFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Reset {
    public void ScoreReset() throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/main/java/com/example/SaveFile/resetscore.txt")));
            File file = new File("src/main/java/com/example/SaveFile/score.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            String str;
            int i = 0;
            while (i < 10) {
                str = reader.readLine();
                if (str == null) break;
                writer.write(str);
                writer.newLine();
                i++;
            }
            writer.close();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("파일명을 찾을 수 없습니다.");
        } catch (NullPointerException e) {
            System.out.println("Err : " + e);
        }
    }
    public void SettingReset() throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/main/java/com/example/SaveFile/resetsetting.txt")));
            File file = new File("src/main/java/com/example/SaveFile/setting.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            String str;
            int i = 0;
            while (i < 7) {
                str = reader.readLine();
                if (str == null) break;
                writer.write(str);
                writer.newLine();
                i++;
            }
            writer.close();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("파일명을 찾을 수 없습니다.");
        } catch (NullPointerException e) {
            System.out.println("Err : " + e);
        }
    }

}
