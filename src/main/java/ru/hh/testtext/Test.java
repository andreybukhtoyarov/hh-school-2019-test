package ru.hh.testtext;

/**
 * Интерфейс для тестов.
 * @author Andrey Bukhtoyarov (andreymedoed@gmail.com).
 * @version %Id%.
 * @since 0.1.
 */
public interface Test {
    /**
     * Запускает тест.
     * @throws Exception - тест не обрабатывает ошибки, он должен их пробрасывает дальше.
     */
    void test() throws Exception;
}
