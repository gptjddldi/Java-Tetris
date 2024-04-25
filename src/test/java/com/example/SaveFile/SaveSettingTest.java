package com.example.SaveFile;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SaveSettingTest {

    @Test
    void testSaveKeySettingsToFile() {
        String[] keyNames = {"Key1", "Key2", "Key3", "Key4", "Key5", "Key6","Key6","Key6","Key6"};
        SaveSetting.saveKeySettingsToFile(keyNames);

        String[] loadedSettings = SaveSetting.loadKeySettingsFromFile();
        assertArrayEquals(keyNames, loadedSettings);
    }
}