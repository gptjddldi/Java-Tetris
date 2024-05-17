package com.example.SaveFile;

import com.example.page.StartPage;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardDataTest {

    private static final String TEMP_FILE_PATH = "temp_score.txt";
    private static final String MODE_ITEM = "item";
    private static final String MODE_NORMAL = "normal";
    private ScoreBoardData scoreBoardData;

    @BeforeEach
    public void setUp() throws IOException {
        Files.createFile(Paths.get(TEMP_FILE_PATH));
        ScoreBoardData.setFilePath(TEMP_FILE_PATH);
        scoreBoardData = new ScoreBoardData();
        StartPage.mode = MODE_NORMAL; // 기본 모드를 설정
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEMP_FILE_PATH));
    }

    @Test
    public void testSaveAndSortItemMode() throws IOException {
        StartPage.mode = MODE_ITEM;

        for (int i = 1; i <= 12; i++) {
            ScoreBoardData.SaveToFile("itemUser" + i, i * 10);
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEMP_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        assertFalse(lines.isEmpty());
        assertTrue(lines.size() <= 20);

        int previousScore = Integer.MAX_VALUE;
        for (int i = 10; i < lines.size(); i++) {
            int currentScore = Integer.parseInt(lines.get(i).split(",")[0]);
            assertTrue(currentScore <= previousScore);
            previousScore = currentScore;
        }
    }
}


