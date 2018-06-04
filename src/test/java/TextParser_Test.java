import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class TextParser_Test {

    private static TextParser textParser;
    private static String keywordsText;
    private static HashMap<String, Integer> keywordsMap;
    private static String sentenceText;
    private static List<String> sentenceList;
    private static String splitText;
    private static String[] splitArray;

    @BeforeAll
    static void initAll() {
        ConfigLink configLink = new ConfigLink(new File("./src/test/resources/config"));
        textParser = new TextParser(Locale.ENGLISH, configLink);

        keywordsText = "This Text is a text; and it contains something that is text";
        keywordsMap = new HashMap<>();
        keywordsMap.put("text",3);
        keywordsMap.put("is",2);
        keywordsMap.put("something",1);
        keywordsMap.put("contains",1);
        keywordsMap.put("this",1);
        keywordsMap.put("that",1);
        keywordsMap.put("it",1);
        keywordsMap.put("a",1);

        sentenceText = "Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat? Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur!! Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum!";
        sentenceList = Arrays.asList("Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.",
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat?",
                "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur!!",
                "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum!");

        splitText = "Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.";
        splitArray = new String[]{"lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipisici", "elit", "sed", "eiusmod", "tempor", "incidunt", "ut", "labore", "et", "dolore", "magna", "aliqua"};
    }

    @Test
    void test_getKeywords() {
        Map<String, Integer> keywords = textParser.getKeywords(keywordsText);
        for(String k : keywords.keySet())
            assertEquals((int)keywords.get(k), (int)keywordsMap.get(k));
    }

    @Test
    void test_getSentences() {
        assertIterableEquals(textParser.getSentences(sentenceText), sentenceList);
    }

    @Test
    void test_splitWords() {
        assertArrayEquals(textParser.splitWords(splitText), splitArray);
    }
}
