package ru.hh.testtext;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;

/**
 * Запуск программы. Здесь можно указать Writer для вывода результата.
 * По умолчанию программа создает файл test_result.txt в системной tmp папке.
 * @author Andrey Bukhtoyarov (andreymedoed@gmail.com).
 * @version %Id%.
 * @since 0.1.
 */
public class TestsStart {

    public static void main(String[] args) throws IOException {
        Path file = FileSystems.getDefault().getPath(System.getProperty("java.io.tmpdir"), "test_result.txt");
        Files.deleteIfExists(file);
        Files.createFile(file);
        try (PrintWriter pw = new PrintWriter(new FileWriter(file.toFile(), true))) {
            new TestsTracker(pw).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
