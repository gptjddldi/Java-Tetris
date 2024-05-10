package com.example.SaveFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.*;

public class Reset {
    private static final String SCORE_FILE_PATH = SaveSetting.getFileAbsolutePath("src/main/java/com/example/SaveFile/score.txt");
    private static final String SCORE_RESET_FILE_PATH = SaveSetting.getFileAbsolutePath("src/main/java/com/example/SaveFile/resetscore.txt");
    private static final String SETTING_FILE_PATH = SaveSetting.getFileAbsolutePath("src/main/java/com/example/SaveFile/setting.txt");
    private static final String SETTING_RESET_FILE_PATH = SaveSetting.getFileAbsolutePath("src/main/java/com/example/SaveFile/resetsetting.txt");

    public void ScoreReset() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_RESET_FILE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE_PATH))) {
            reader.lines().limit(20).forEach(line -> {
                try {
                    writer.write(line);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SettingReset() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SETTING_RESET_FILE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(SETTING_FILE_PATH))) {
            reader.lines().limit(14).forEach(line -> {
                try {
                    writer.write(line);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
