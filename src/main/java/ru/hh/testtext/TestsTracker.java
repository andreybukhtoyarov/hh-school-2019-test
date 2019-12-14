package ru.hh.testtext;

import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Инициализация тестов и их запуск.
 * @author Andrey Bukhtoyarov (andreymedoed@gmail.com).
 * @version %Id%.
 * @since 0.1.
 */
public class TestsTracker {
    /**
     * Writer
     */
    private final Writer writer;
    /**
     * Множество тестов.
     */
    private final Set<Test> setTests = new LinkedHashSet<>();

    TestsTracker(Writer writer) {
        this.writer = writer;
    }

    /**
     * Инициализирует и добавляет все тесты в коллекцию.
     */
    private void addTests() {
        Tests tests = new Tests(this.writer);
        setTests.add(tests.new WhenTextIsOneWord());
        setTests.add(tests.new WhenTextIsSeveralWord());
        setTests.add(tests.new WhenEmptyTextThen());
        setTests.add(tests.new WhenTextStartWithExclamationMark());
        setTests.add(tests.new WhenTextWithOROperator());
        setTests.add(tests.new WhenTextWithNOTOperator());
        setTests.add(tests.new WhenTextWithSeveralConditions());
        setTests.add(tests.new WhenTextWithSearchByFields());
        setTests.add(tests.new WhenTextSize256Chars());
        setTests.add(tests.new WhenTextSize512Chars());
        setTests.add(tests.new WhenTextSize1024Chars());
        setTests.add(tests.new WhenTextSize32768Chars());
        setTests.add(tests.new WhenTextIsNull());
        setTests.add(tests.new WhenTextIsNULL());
        setTests.add(tests.new WhenTextIsISNULL());
        setTests.add(tests.new WhenTextNonASCII());
        setTests.add(tests.new WhenTextNonASCIIAndASCII());
        setTests.add(tests.new WhenTextIsNumbers());
        setTests.add(tests.new WhenTextIsNumbersAndLetter());
        setTests.add(tests.new WhenTextIsNumbersSpaceLetter());
        setTests.add(tests.new WhenTextIsLettersSet());
        setTests.add(tests.new WhenTextIsSpace());
        setTests.add(tests.new WhenTextBeginFromSpace());
        setTests.add(tests.new WhenTextBeginFromDot());
        setTests.add(tests.new WhenTextIsDot());
        setTests.add(tests.new WhenTextIsSlash());
        setTests.add(tests.new WhenTextIsURI());
    }

    /**
     * Запускает все тесты.
     * @throws Exception - пробрасывает иключение дальше.
     */
    public void start() throws Exception {
        addTests();
        for (Test test : this.setTests) {
            test.test();
        }
    }
}
