import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class TextParser_Test {

    private static TextParser textParser;
    private static String keywordsText;
    private static List<String> keywordsList;
    private static String sentenceText;
    private static List<String> sentenceList;

    @BeforeAll
    static void initAll() {
        ConfigLink configLink = new ConfigLink(new File("./src/test/resources/config"));
        textParser = new TextParser(Locale.ENGLISH, configLink);

        keywordsText = "This Text is a text; and it contains something that is text";
        keywordsList = Arrays.asList("text", "is", "a", "that", "contains", "this", "it", "something");

        sentenceText = "Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat? Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur!! Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum!";
        sentenceList = Arrays.asList("Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.",
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat?",
                "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur!!",
                "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum!");
    }

    @Test
    void test_getKeywords() {
        assertIterableEquals(textParser.getKeywords(keywordsText), keywordsList);
    }

    @Test
    void test_getSentences() {
        assertIterableEquals(textParser.getSentences(sentenceText), sentenceList);
    }
}
