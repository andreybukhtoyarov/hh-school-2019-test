package ru.hh.testtext;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.Writer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Содержит вложенные классы, реализующие интерфейс Test и основную логику тестов.
 * @author Andrey Bukhtoyarov (andreymedoed@gmail.com).
 * @version %Id%.
 * @since 0.1.
 */
public class Tests {
    /**
     * HTTP клиент.
     */
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    /**
     * Writer.
     */
    private final Writer writer;

    public Tests(Writer writer) {
        this.writer = writer;
    }

    /**
     * Отправляет заданный запрос.
     * @param uri - зарос
     * @return - ответ
     * @throws Exception - пробрасывает иключение дальше.
     */
    private HttpResponse<String> sendGet(String uri) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .setHeader("User-Agent", "HoneyBadger")
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Отправляет заданный запрос при помощи метода sendGet(String uri) и получает ответ.
     * Выводит во Writer Назвение теста, Отправленный запрос и Статус код ответа сервера.
     * Парсит тело ответа и находит количество найдненных по запросу вакансий.
     * В зависимости от результата парсинга выводит соответствующее сообщение во Writer.
     * @param request - запрос
     * @param testName - название теста
     * @throws Exception - пробрасывает иключение дальше.
     */
    private void execute(String request, String testName) throws Exception {
        HttpResponse<String> response = sendGet(String.format("https://api.hh.ru/vacancies?text=%s", request));
        JSONObject parse;
        Object found = null;
        if (response.statusCode() == 200) {
            parse = (JSONObject) new JSONParser().parse(response.body());
            found = parse.get("found");
        }
        writer.write(String.format("Тест: %s\n", testName));
        writer.write(String.format("URI = \"%s\"\n", response.uri()));
        writer.write(String.format("Status code = %s\n", response.statusCode()));
        if (found != null) {
            writer.write(String.format("Найдено вакансий: %s\n", found.toString()));
        } else {
            writer.write("По этому запросу вакансий не нашлось.\n");
        }
        writer.write("\n");
    }

    class WhenTextIsOneWord implements Test {

        @Override
        public void test() throws Exception {
            execute("java", "WhenTextIsOneWord");
        }
    }

    class WhenTextIsSeveralWord implements Test {

        @Override
        public void test() throws Exception {
            execute("java%20developer%20team%20Moscow", "WhenTextIsSeveralWord");
        }
    }

    class WhenTextStartWithExclamationMark implements Test {

        @Override
        public void test() throws Exception {
            execute("!java", "WhenTextStartWithExclamationMark");
        }
    }

    class WhenTextWithOROperator implements Test {

        @Override
        public void test() throws Exception {
            execute("java%20OR%20c++", "WhenTextWithOROperator");
        }
    }

    class WhenTextWithNOTOperator implements Test {

        @Override
        public void test() throws Exception {
            execute("java%20NOT%20c++", "WhenTextWithNOTOperator");
        }
    }

    class WhenTextWithSeveralConditions implements Test {

        @Override
        public void test() throws Exception {
            execute("%28java%20OR%20c++%29%20AND%20%28python%20OR%20c++%29",
                    "WhenTextWithSeveralConditions");
        }
    }

    class WhenTextWithSearchByFields implements Test {

        @Override
        public void test() throws Exception {
            execute("NAME%3A%28python+OR+java%29+and+COMPANY_NAME%3AHeadhunter",
                    "WhenTextWithSearchByFields");
        }
    }

    class WhenEmptyTextThen implements Test {

        @Override
        public void test() throws Exception {
            execute("", "WhenEmptyTextThen");
        }
    }

    class WhenTextSize256Chars implements Test {

        @Override
        public void test() throws Exception {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 256; ++i) {
                sb.append("а");
            }
            execute(sb.toString(), "WhenTextSize256Chars");
        }
    }

    class WhenTextSize512Chars implements Test {

        @Override
        public void test() throws Exception {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 512; ++i) {
                sb.append("б");
            }
            execute(sb.toString(), "WhenTextSize512Chars");
        }
    }

    class WhenTextSize1024Chars implements Test {

        @Override
        public void test() throws Exception {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1024; ++i) {
                sb.append("ц");
            }
            execute(sb.toString(), "WhenTextSize1024Chars");
        }
    }

    class WhenTextSize32768Chars implements Test {

        @Override
        public void test() throws Exception {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1024 * 32; ++i) {
                sb.append("ц");
            }
            execute(sb.toString(), "WhenTextSize32768Chars");
        }
    }

    class WhenTextIsNull implements Test {

        @Override
        public void test() throws Exception {
            execute("null", "WhenTextIsNull");
        }
    }

    class WhenTextIsNULL implements Test {

        @Override
        public void test() throws Exception {
            execute("NULL", "WhenTextIsNULL");
        }
    }

    class WhenTextIsISNULL implements Test {

        @Override
        public void test() throws Exception {
            execute("IS%20NULL", "WhenTextIsISNULL");
        }
    }

    class WhenTextNonASCII implements Test {

        @Override
        public void test() throws Exception {
            execute("爪哇", "WhenTextNonASCII");
        }
    }

    class WhenTextNonASCIIAndASCII implements Test {

        @Override
        public void test() throws Exception {
            execute("java%20爪哇", "WhenTextNonASCIIAndASCII");
        }
    }

    class WhenTextIsNumbers implements Test {

        @Override
        public void test() throws Exception {
            execute("37552", "WhenTextIsNumbers");
        }
    }

    class WhenTextIsNumbersAndLetter implements Test {

        @Override
        public void test() throws Exception {
            execute("java37", "WhenTextIsNumbersAndLetter");
        }
    }

    class WhenTextIsNumbersSpaceLetter implements Test {

        @Override
        public void test() throws Exception {
            execute("java%2037", "WhenTextIsNumbersSpaceLetter");
        }
    }

    class WhenTextIsLettersSet implements Test {

        @Override
        public void test() throws Exception {
            execute("j哇a爪v%20а", "WhenTextIsLettersSet");
        }
    }

    class WhenTextIsSpace implements Test {

        @Override
        public void test() throws Exception {
            execute("%20", "WhenTextIsSpace");
        }
    }

    class WhenTextBeginFromSpace implements Test {

        @Override
        public void test() throws Exception {
            execute("%20Java", "WhenTextBeginFromSpace");
        }
    }

    class WhenTextBeginFromDot implements Test {

        @Override
        public void test() throws Exception {
            execute(".getAll", "WhenTextBeginFromDot");
        }
    }

    class WhenTextIsDot implements Test {

        @Override
        public void test() throws Exception {
            execute(".", "WhenTextIsDot");
        }
    }

    class WhenTextIsSlash implements Test {

        @Override
        public void test() throws Exception {
            execute("/", "WhenTextIsSlash");
        }
    }

    class WhenTextIsURI implements Test {

        @Override
        public void test() throws Exception {
            execute("https://hh.ru/", "WhenTextIsURI");
        }
    }
}
