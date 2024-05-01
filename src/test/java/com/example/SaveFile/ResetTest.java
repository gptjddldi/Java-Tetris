package com.example.SaveFile;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class ResetTest {
    private static final String SCORE_FILE_PATH = "src/main/java/com/example/SaveFile/score.txt";
    private static final String SCORE_RESET_FILE_PATH = "src/main/java/com/example/SaveFile/resetscore.txt";
    private static final String SETTING_FILE_PATH = "src/main/java/com/example/SaveFile/setting.txt";
    private static final String SETTING_RESET_FILE_PATH = "src/main/java/com/example/SaveFile/resetsetting.txt";
    private static final String TEST_SCORE_FILE_PATH = "src/main/java/com/example/SaveFile/testscore.txt";
    private static final String TEST_SETTING_FILE_PATH = "src/main/java/com/example/SaveFile/testsetting.txt";

    private Reset reset;

    @BeforeEach
    public void setUp() throws IOException {
        // 테스트용 파일 생성
        copyFile(SCORE_RESET_FILE_PATH, TEST_SCORE_FILE_PATH);
        copyFile(SETTING_RESET_FILE_PATH, TEST_SETTING_FILE_PATH);
        reset = new Reset();
    }

    @AfterEach
    public void tearDown() {
        // 테스트용 파일 삭제
        deleteFile(TEST_SCORE_FILE_PATH);
        deleteFile(TEST_SETTING_FILE_PATH);
    }

    @Test
    public void testScoreReset() throws IOException {
        // When
        reset.ScoreReset();

        // Then
        assertFileContentsEqual(SCORE_FILE_PATH, TEST_SCORE_FILE_PATH);
    }

    @Test
    public void testSettingReset() throws IOException {
        // When
        reset.SettingReset();

        // Then
        assertFileContentsEqual(SETTING_FILE_PATH, TEST_SETTING_FILE_PATH);
    }

    // 도우미 메서드
    private void copyFile(String sourcePath, String destinationPath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourcePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destinationPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private void deleteFile(String filePath) {
        File file = new File(filePath);
        file.delete();
    }

    private void assertFileContentsEqual(String expectedFilePath, String actualFilePath) throws IOException {
        try (BufferedReader expectedReader = new BufferedReader(new FileReader(expectedFilePath));
             BufferedReader actualReader = new BufferedReader(new FileReader(actualFilePath))) {
            String expectedLine;
            String actualLine = null;
            while ((expectedLine = expectedReader.readLine()) != null &&
                    (actualLine = actualReader.readLine()) != null) {
                assertEquals(expectedLine, actualLine);
            }
            assertNull(expectedLine);
            assertNull(actualLine);
        }
    }
}