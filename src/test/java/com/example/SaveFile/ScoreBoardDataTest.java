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
    public void testSaveToFileNormalMode() throws IOException {
        StartPage.mode = MODE_NORMAL;
        ScoreBoardData.SaveToFile("testUserNormal1", 100);
        ScoreBoardData.SaveToFile("testUserNormal2", 200);

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEMP_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        assertFalse(lines.isEmpty());
        assertEquals("200,testUserNormal2,default", lines.get(0)); // Assuming "default" is the game level
        assertEquals("100,testUserNormal1,default", lines.get(1));
    }

    @Test
    public void testSaveToFileItemMode() throws IOException {
        StartPage.mode = MODE_ITEM;
        ScoreBoardData.SaveToFile("testUserItem1", 150);
        ScoreBoardData.SaveToFile("testUserItem2", 250);

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEMP_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        assertFalse(lines.isEmpty());
        assertEquals("150,testUserItem1,default", lines.get(0)); // Assuming "default" is the game level
        assertEquals("250,testUserItem2,default", lines.get(1));
    }

    @Test
    public void testLoadRankingNormalMode() throws Exception {
        StartPage.mode = MODE_NORMAL;
        ScoreBoardData.SaveToFile("testUser1", 150);
        ScoreBoardData.SaveToFile("testUser2", 200);

        ScrollPane scrollPane = new ScrollPane();
        ScoreBoardData.loadRanking(scrollPane);

        VBox container = (VBox) scrollPane.getContent();
        List<String> labels = new ArrayList<>();
        for (int i = 1; i < container.getChildren().size(); i++) { // Skip first Label (mode_level)
            labels.add(((Label) container.getChildren().get(i)).getText());
        }

        assertEquals("점수: 200       이름: testUser2(default)", labels.get(0));
        assertEquals("점수: 150       이름: testUser1(default)", labels.get(1));
    }

    @Test
    public void testLoadRankingItemMode() throws Exception {
        StartPage.mode = MODE_ITEM;
        ScoreBoardData.SaveToFile("testUser1", 150);
        ScoreBoardData.SaveToFile("testUser2", 200);

        ScrollPane scrollPane = new ScrollPane();
        ScoreBoardData.loadRanking(scrollPane);

        VBox container = (VBox) scrollPane.getContent();
        List<String> labels = new ArrayList<>();
        for (int i = 1; i < container.getChildren().size(); i++) { // Skip first Label (mode_level)
            labels.add(((Label) container.getChildren().get(i)).getText());
        }

        assertEquals("점수: 200       이름: testUser2(default)", labels.get(0));
        assertEquals("점수: 150       이름: testUser1(default)", labels.get(1));
    }

    @Test
    public void testFileNotFound() {
        String nonExistentFilePath = "nonexistent.txt";
        ScoreBoardData.setFilePath(nonExistentFilePath);
        assertThrows(IOException.class, () -> {
            scoreBoardData.getScore();
        });
    }

    @Test
    public void testSaveAndSortNormalMode() throws IOException {
        StartPage.mode = MODE_NORMAL;

        for (int i = 1; i <= 12; i++) {
            ScoreBoardData.SaveToFile("normalUser" + i, i * 10);
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
        for (int i = 0; i < Math.min(10, lines.size()); i++) {
            int currentScore = Integer.parseInt(lines.get(i).split(",")[0]);
            assertTrue(currentScore <= previousScore);
            previousScore = currentScore;
        }
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


