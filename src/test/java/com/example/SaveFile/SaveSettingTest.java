package com.example.SaveFile;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SaveSettingTest {

    @Test
    void testSaveKeySettingsToFile() {
        String[] keyNames = {"Key1", "Key2", "Key3", "Key4", "Key5", "Key6"};
        SaveSetting.saveKeySettingsToFile(keyNames);

        String[] loadedSettings = SaveSetting.loadKeySettingsFromFile();
        assertArrayEquals(keyNames, loadedSettings);
    }

    @Test
    void testSaveOneSettingsToFile() {
        SaveSetting.saveOneSettingsToFile("NewValue", 3);

        String loadedSetting = SaveSetting.loadOneSettingFromFile(3);
        assertEquals("NewValue", loadedSetting);
    }

    @Test
    void testLoadKeySettingsFromFileWhenFileDoesNotExist() {
        Throwable exception = assertThrows(Exception.class, () -> SaveSetting.loadKeySettingsFromFile());
        assertEquals("src/main/java/com/example/SaveFile/setting.txt (지정된 파일을 찾을 수 없습니다)", exception.getMessage());
    }

    @Test
    void testLoadOneSettingFromFileWhenFileDoesNotExist() {
        // 파일이 존재하지 않는 상황에서의 예외 처리 테스트
        assertThrows(FileNotFoundException.class, () -> SaveSetting.loadOneSettingFromFile(1));
    }
}