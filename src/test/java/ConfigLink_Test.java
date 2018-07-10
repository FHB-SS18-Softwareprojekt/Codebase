import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLink_Test {

    private ConfigLink configLink;
    private Set<String> en_stopwords;

    @BeforeEach
    void initEach() {
        configLink = new ConfigLink(new File("./src/test/resources/config"));
        en_stopwords = new HashSet<>();
        en_stopwords.add("or");
        en_stopwords.add("because");
        en_stopwords.add("and");
        en_stopwords.add("then");
    }

    @Test()
    void test_getStopwords_null() {
        assertThrows(IllegalArgumentException.class, () -> configLink.getStopwords(null));
    }

    @Test()
    void test_getStopwords_en() {
        assertIterableEquals(configLink.getStopwords(Locale.ENGLISH), en_stopwords);
    }

    @Test()
    void test_addStopwords_null() {
        assertThrows(IllegalArgumentException.class, () -> configLink.addStopword(null, "test"));
        assertThrows(IllegalArgumentException.class, () -> configLink.addStopword(Locale.ENGLISH, null));
    }

    @Test()
    void test_addStopwords_en() {
        en_stopwords.add("test");
        assertTrue(configLink.addStopword(Locale.ENGLISH, "test"));
        assertIterableEquals(configLink.getStopwords(Locale.ENGLISH), en_stopwords);
    }

    @Test()
    void test_saveConfig() {
        File lang_fr = new File("./src/test/resources/config/fr.json");
        if (lang_fr.exists())
            lang_fr.delete();
        configLink.addStopword(Locale.FRENCH, "et");
        configLink.addStopword(Locale.FRENCH, "alors");
        configLink.saveConfig();
        assertTrue(lang_fr.exists());
        lang_fr.delete();
    }
}
