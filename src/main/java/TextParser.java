import java.util.*;

public class TextParser {

    private final Locale locale;
    private final ConfigLink config;

    public TextParser(Locale _locale, ConfigLink _config) {
        this.locale = _locale;
        this.config = _config;
    }

    public List<String> getKeywords(String text) {
        text = this.removePunctations(text);
        Map<String, Integer> words = this.splitAndCountWords(text);
        this.removeStopWords(words);

        List<String> keywords = new ArrayList<>(words.keySet());
        keywords.sort((s1, s2) -> -Integer.compare(words.get(s1), words.get(s2)));
        return keywords;
    }

    private Map<String, Integer> splitAndCountWords(String text) {
        Map<String, Integer> map = new HashMap<>();
        String[] words = text.toLowerCase().split(" ");
        for (String word : words) {
            if (map.containsKey(word))
                map.put(word, map.get(word) + 1);
            else
                map.put(word, 1);
        }
        return map;
    }

    private String removePunctations(String text) {
        return text.replaceAll("([^ A-Za-z0-9])", "");
    }

    private void removeStopWords(Map<String, Integer> words) {
        for (String s : this.config.getStopwords(this.locale))
            words.remove(s.toLowerCase());
    }
}
