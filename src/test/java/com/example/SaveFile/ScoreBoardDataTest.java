package com.example.SaveFile;

import com.example.page.JavaFXInitializer;
import com.example.page.StartPage;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardDataTest {

    private static Path tempScoreFile;

    @BeforeAll
    public static void initJavaFX() throws InterruptedException {
        JavaFXInitializer.initToolkit();
    }

    @BeforeEach
    public void setUp() throws IOException {
        tempScoreFile = Files.createTempFile("score", ".txt");
        prepareFile(tempScoreFile, List.of(
                "100,PlayerA,MEDIUM", "90,PlayerB,MEDIUM", "80,PlayerC,MEDIUM", "70,PlayerD,MEDIUM", "60,PlayerE,MEDIUM",
                "50,PlayerF,MEDIUM", "40,PlayerG,MEDIUM", "30,PlayerH,MEDIUM", "20,PlayerI,MEDIUM", "10,PlayerJ,MEDIUM",
                "200,PlayerK,MEDIUM", "190,PlayerL,MEDIUM", "180,PlayerM,MEDIUM", "170,PlayerN,MEDIUM", "160,PlayerO,MEDIUM",
                "150,PlayerP,MEDIUM", "140,PlayerQ,MEDIUM", "130,PlayerR,MEDIUM", "120,PlayerS,MEDIUM", "110,PlayerT,MEDIUM"
        ));
        ScoreBoardData.setFilePath(tempScoreFile.toString());
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempScoreFile);
    }

    @Test
    public void testSaveRankingNormalMode() throws Exception {
        StartPage.mode = "normal";
        ScoreBoardData.saveRanking("NewPlayerNormal", 85);
        List<String> lines = Files.readAllLines(tempScoreFile);

        // Verify that the score was added in the correct position
        assertEquals(20, lines.size(), "Should have 20 scores after adding a new one in normal mode");
        assertEquals("20,PlayerI,MEDIUM", lines.get(9), "New score should be at the correct position in normal mode");
    }

    @Test
    public void testSaveRankingItemMode() throws Exception {
        StartPage.mode = "item";
        ScoreBoardData.saveRanking("NewPlayerItem", 185);
        List<String> lines = Files.readAllLines(tempScoreFile);

        // Verify that the score was added in the correct position
        assertEquals(20, lines.size(), "Should have 20 scores after adding a new one in item mode");
        assertEquals("190,PlayerL,MEDIUM", lines.get(11), "New score should be at the correct position in item mode");
    }

    @Test
    public void testLoadRanking() throws Exception {
        ScrollPane scrollPane = new ScrollPane();
        ScoreBoardData.loadRanking(scrollPane);

        VBox container = (VBox) scrollPane.getContent();
        assertNotNull(container, "Container should not be null.");
        assertEquals(22, container.getChildren().size(), "Container should have 22 children (20 scores + 2 labels).");
    }

    @Test
    public void testHomeloadRanking() throws Exception {
        ScrollPane scrollPane = new ScrollPane();
        ScoreBoardData.HomeloadRanking(scrollPane);

        VBox container = (VBox) scrollPane.getContent();
        assertNotNull(container, "Container should not be null.");
        assertEquals(22, container.getChildren().size(), "Container should have 22 children (20 scores + 2 labels).");
    }

    private void prepareFile(Path filePath, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}



