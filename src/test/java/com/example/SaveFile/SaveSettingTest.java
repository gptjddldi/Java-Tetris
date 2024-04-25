package com.example.SaveFile;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class SaveSettingTest {

    @Test
    void testSaveKeySettingsToFile() {
        String[] keyNames = {"UP", "DOWN", "LEFT", "RIGHT", "SPACE", "X", "off", "MEDIUM", "NORMAL"};
        SaveSetting.saveKeySettingsToFile(keyNames);

        String[] loadedSettings = SaveSetting.loadKeySettingsFromFile();
        assertArrayEquals(keyNames, loadedSettings);
    }

    @Test
    public void testSaveOneSettingsToFile() throws IOException {
        // 임시 파일 경로 생성
        String filePath = "src/main/java/com/example/SaveFile/setting.txt";

        // 테스트를 위한 새로운 파일 생성
        File testFile = new File(filePath);

        // 메서드 호출
        SaveSetting.saveOneSettingsToFile("UP", 1);

        // 파일이 생성되었는지 확인
        assertTrue(testFile.exists());

        // 파일에서 설정 값 읽어오기
        BufferedReader reader = new BufferedReader(new FileReader(testFile));
        String line = reader.readLine();
        reader.close();

        // 설정 값이 올바르게 변경되었는지 확인
        assertEquals("UP", line);
    }

    @Test
    public void testLoadOneSettingFromFile() throws IOException {
        assertEquals("UP", SaveSetting.loadOneSettingFromFile(1));
        assertEquals("DOWN", SaveSetting.loadOneSettingFromFile(2));
        assertEquals("LEFT", SaveSetting.loadOneSettingFromFile(3));
    }
}