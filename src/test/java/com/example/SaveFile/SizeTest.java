package com.example.SaveFile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SizeTest {



    @Test
    void testSize_Big() {
        // Set up the environment
        SaveSetting.saveOneSettingsToFile("BIG", 14);

        // Call the method under test
        double result = size.size();

        // Verify the result
        assertEquals(1.4, result);
    }

    @Test
    void testSize_Small() {
        // Set up the environment
        SaveSetting.saveOneSettingsToFile("SMALL", 14);

        // Call the method under test
        double result = size.size();

        // Verify the result
        assertEquals(1.0, result);
    }
    @Test
    void testSize_Normal() {
        // Set up the environment
        SaveSetting.saveOneSettingsToFile("NORMAL", 14);

        // Call the method under test
        double result = size.size();

        // Verify the result
        assertEquals(1.2, result);
    }
    @Test
    void testSize_Defalt() {
        SaveSetting.saveOneSettingsToFile("ERROR", 14);
        double result = size.size();

        // Verify the result
        assertEquals(1.2, result);
    }
}

