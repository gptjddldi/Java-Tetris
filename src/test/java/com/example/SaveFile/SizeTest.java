package com.example.SaveFile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SizeTest {

    @Test
    void testSize_Normal() {
        // Set up the environment
        SaveSetting.saveOneSettingsToFile("NORMAL", 9);

        // Call the method under test
        double result = size.size();

        // Verify the result
        assertEquals(1.2, result);
    }

    @Test
    void testSize_Big() {
        // Set up the environment
        SaveSetting.saveOneSettingsToFile("BIG", 9);

        // Call the method under test
        double result = size.size();

        // Verify the result
        assertEquals(1.4, result);
    }

    @Test
    void testSize_Small() {
        // Set up the environment
        SaveSetting.saveOneSettingsToFile("SMALL", 9);

        // Call the method under test
        double result = size.size();

        // Verify the result
        assertEquals(1.0, result);
    }
}

