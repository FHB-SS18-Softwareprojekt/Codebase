import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TextParser_Test {

    private static TextParser textParser;
    private static String keywordsText;
    private static HashMap<String, Integer> keywordsMap;
    private static String sentenceText;
    private static List<Sentence> sentenceList;
    private static String splitText;
    private static String[] splitArray;
    private static String titleText;
    private static Set<String> titleSet;
    private static List<String> stopwords;
    private static List<String> stopwordsFiltered;

    @BeforeAll
    static void initAll() {
        ConfigLink configLink = new ConfigLink(new File("./src/test/resources/config"));
        textParser = new TextParser(Locale.ENGLISH, configLink);

        keywordsText = "This Text is a text; and it contains something that is text";
        keywordsMap = new HashMap<>();
        keywordsMap.put("text", 3);
        keywordsMap.put("is", 2);
        keywordsMap.put("something", 1);
        keywordsMap.put("contains", 1);
        keywordsMap.put("this", 1);
        keywordsMap.put("that", 1);
        keywordsMap.put("it", 1);
        keywordsMap.put("a", 1);

        sentenceText = "Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat? Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur!! Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum!";
        sentenceList = new ArrayList<>();
        Collections.addAll(sentenceList, new Sentence("Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.", 0),
                new Sentence("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat?", 1),
                new Sentence("Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur!!", 2),
                new Sentence("Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum!", 3));

        splitText = "Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.";
        splitArray = new String[]{"lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipisici", "elit", "sed", "eiusmod", "tempor", "incidunt", "ut", "labore", "et", "dolore", "magna", "aliqua"};

        titleText = "Test-Title; or how to properly Unit-test";
        titleSet = new HashSet<>();
        Collections.addAll(titleSet, "testtitle", "how", "to", "properly", "unittest");

        stopwords = new ArrayList<>();
        Collections.addAll(stopwords, "Remove", "or", "and", "words");
        stopwordsFiltered = new ArrayList<>();
        Collections.addAll(stopwordsFiltered, "Remove", "words");
    }

    @Test
    void test_getKeywords() {
        Map<String, Integer> keywords = textParser.getKeywords(keywordsText, Locale.ENGLISH);
        for (String k : keywords.keySet())
            assertEquals((int) keywords.get(k), (int) keywordsMap.get(k));
    }

    @Test
    void test_getSentences() {
        List<Sentence> sentenceTestList = textParser.getSentences(sentenceText);
        assertEquals(sentenceList.size(), sentenceTestList.size());
        for (int i = 0; i < sentenceTestList.size(); i++) {
            assertEquals(sentenceTestList.get(i).getText(), sentenceList.get(i).getText());
            assertEquals(sentenceTestList.get(i).getPosition(), sentenceList.get(i).getPosition());
            assertEquals(sentenceTestList.get(i).getSentenceScore(), sentenceList.get(i).getSentenceScore());
        }
    }

    @Test
    void test_splitWords() {
        assertArrayEquals(textParser.splitWords(splitText), splitArray);
    }

    @Test
    void test_splitTitle() {
        assertIterableEquals(textParser.splitTitle(titleText, Locale.ENGLISH), titleSet);
    }

    @Test
    void test_removeStopWords() {
        textParser.removeStopWords(stopwords, Locale.ENGLISH);
        assertIterableEquals(stopwords, stopwordsFiltered);
    }

    @Test
    void test_getTextFromPath() {
        try {
            String[] text = textParser.getTextFromPath("./src/test/resources/pdf/HelloWorld.pdf");
            assertEquals(text.length, 2);
            assertEquals(text[0], "hello world");
            assertEquals(text[1], "");
        } catch (IOException exception) {
            fail(exception);
        }
    }
}
