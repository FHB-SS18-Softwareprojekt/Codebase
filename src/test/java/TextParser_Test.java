import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class TextParser_Test {

    static TextParser textParser;
    static String example;
    static List<String> exampleList;

    @BeforeAll
    static void initAll() {
        ConfigLink configLink = new ConfigLink(new File("./src/test/resources/config"));
        textParser = new TextParser(Locale.ENGLISH, configLink);
        example = "This Text is a text; and it contains something that is text";
        exampleList = Arrays.asList("text", "is", "a", "that", "contains", "this", "it", "something");
    }

    @Test
    void test_getKeywords() {
        assertIterableEquals(textParser.getKeywords(example), exampleList);
    }
}
