package com.example.SaveFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveSetting {
    public static void saveKeySettingsToFile(String[] keyNames) {
        String filePath = "src/main/java/com/example/SaveFile/setting.txt";
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                lineNumber++;
            }
            reader.close();

            String[] linesArray = lines.toArray(new String[0]);

            List<String> keyNameList = new ArrayList<>(Arrays.asList(keyNames));
            String[] keyNameArray = keyNameList.toArray(new String[0]);


            int minLength = Math.min(keyNameArray.length, linesArray.length);
            for (int i = 0; i < linesArray.length; i++) {
                if (i < minLength) {
                    linesArray[i] = keyNameArray[i];
                }
            }


            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String setting : linesArray) {
                writer.write(setting);
                writer.newLine();
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveColorSettingsToFile(String a) {
        String filePath = "src/main/java/com/example/SaveFile/setting.txt";
        ArrayList<String> array = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null && lineNumber <6) {
                lines.add(line);
                lineNumber++;
            }
            reader.close();

            // 파일의 기존 내용을 그대로 쓰기
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }

            writer.write(a);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String[] loadKeySettingsFromFile() {
        String filePath = "src/main/java/com/example/SaveFile/setting.txt";
        List<String> keyNames = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                keyNames.add(line.trim());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyNames.toArray(new String[0]);
    }

    public static String loadColorSettingFromFile() {
        String file = "src/main/java/com/example/SaveFile/setting.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null && lineNumber < 7) {
                if (lineNumber == 6) {
                    reader.close();
                    return line;
                }
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
