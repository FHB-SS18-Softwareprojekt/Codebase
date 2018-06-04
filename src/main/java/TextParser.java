import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {

    private final static String REGEX_PUNCTUATION = "([^ A-Za-z0-9])";
    private final static Pattern REGEX_SENTENCE_END = Pattern.compile("(.*?[.?!][ \n\r\0])");

    private final Locale locale;
    private final ConfigLink config;

    public TextParser(Locale _locale, ConfigLink _config) {
        this.locale = _locale;
        this.config = _config;
    }

    public Map<String, Integer> getKeywords(String text) {
        Map<String, Integer> words = this.splitAndCountWords(text);
        this.removeStopWords(words);
        return words;
    }

    public List<String> getSentences(String text) {
        List<String> list = new ArrayList<>();
        //Adding a linebreak to the end to allow detecting last sentence
        Matcher matcher = REGEX_SENTENCE_END.matcher(text+"\n");
        while(matcher.find())
            list.add(matcher.group().trim());
        return list;
    }

    public String[] splitWords(String text)
    {
        text = this.removePunctations(text);
        return text.toLowerCase().split(" ");
    }

    private Map<String, Integer> splitAndCountWords(String text) {
        Map<String, Integer> map = new HashMap<>();
        String[] words = this.splitWords(text);
        for (String word : words) {
            if (map.containsKey(word))
                map.put(word, map.get(word) + 1);
            else
                map.put(word, 1);
        }
        return map;
    }

    private String removePunctations(String text) {
        return text.replaceAll(REGEX_PUNCTUATION, "");
    }

    private void removeStopWords(Map<String, Integer> words) {
        for (String s : this.config.getStopwords(this.locale))
            words.remove(s.toLowerCase());
    }

    public void removeStopWords(Collection<String> words) {
        for (String s : this.config.getStopwords(this.locale))
            words.remove(s.toLowerCase());
    }
}
