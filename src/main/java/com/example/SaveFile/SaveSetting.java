package com.example.SaveFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveSetting {
    public static void saveKeySettingsToFile(String[] keyNames) {
        String filePath = "src/main/java/com/example/SaveFile/setting.txt";
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String keyName : keyNames) {
                writer.write(keyName);
                writer.newLine();
            }
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
}
