import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {

    private final static String REGEX_PUNCTUATION = "([^ A-Za-z0-9])";
    private final static Pattern REGEX_SENTENCE_END = Pattern.compile("(.*?[.?!][ \n\r\0])");
    private static final Logger log = Logger.getLogger(TextParser.class.getName());
    private final ConfigLink config;

    public TextParser(Locale _locale, ConfigLink _config) {
        this.config = _config;
    }

    public Locale identifyLanguage(String text) {
        Map<String, Integer> words = splitAndCountWords(text);
        int identifiedCount = 0;
        Locale identifiedLanguage = null;
        for (Locale locale : this.config.getSupportedLanguages()) {
            int count = 0;
            for (String word : this.config.getStopwords(locale)) {
                if (words.containsKey(word))
                    count += words.get(word);
            }
            if (count > identifiedCount) {
                identifiedCount = count;
                identifiedLanguage = locale;
            }
        }
        return identifiedLanguage;
    }

    public Map<String, Integer> getKeywords(String text, Locale locale) {
        Map<String, Integer> words = this.splitAndCountWords(text);
        this.removeStopWords(words, locale);
        return words;
    }

    public List<Sentence> getSentences(String text) {
        List<Sentence> list = new ArrayList<>();
        //Adding a linebreak to the end to allow detecting last sentence
        Matcher matcher = REGEX_SENTENCE_END.matcher(text + "\n");
        int position = 0;
        while (matcher.find()) {
            list.add(new Sentence(matcher.group().trim(), position));
            position++;
        }
        return list;
    }

    public String[] splitWords(String text) {
        text = this.removePunctations(text);
        return text.toLowerCase().split(" ");
    }

    public Set<String> splitTitle(String title, Locale locale) {
        title = this.removePunctations(title);
        Set<String> set = new HashSet<>();
        Collections.addAll(set, title.toLowerCase().split(" "));
        removeStopWords(set, locale);
        return set;
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

    private void removeStopWords(Map<String, Integer> words, Locale locale) {
        for (String s : this.config.getStopwords(locale))
            words.remove(s.toLowerCase());
    }

    public void removeStopWords(Collection<String> words, Locale locale) {
        for (String s : this.config.getStopwords(locale))
            words.remove(s.toLowerCase());
    }

    public String[] getTextFromPath(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader;
        try {
            if (file.getPath().endsWith(".txt")) {
                log.info("Found .txt");
                reader = new BufferedReader(new FileReader(file));
            } else if (file.getPath().endsWith(".pdf")) {
                log.info("Found .pdf");
                PDFDocReader pdfDocReader = new PDFDocReader();
                String text = pdfDocReader.readDocument(file.getPath());
                reader = new BufferedReader((new StringReader(text)));
            } else if (file.getPath().endsWith(".doc") || file.getPath().endsWith(".docx")) {
                log.info("Found .doc or docx");
                WordDocReader wordDocReader = new WordDocReader();
                String text = wordDocReader.readDocument(file.getPath());
                reader = new BufferedReader((new StringReader(text)));
            } else {
                throw new IllegalArgumentException("Wrong/Unknown file extension. Currently supported: " +
                        ".txt , .pdf , .doc & docx.");
            }
            String title = reader.readLine();
            log.info(title);
            String line;
            StringBuilder text = new StringBuilder();
            while ((line = reader.readLine()) != null)
                text.append(line).append("\n");
            String[] read = {title, text.toString()};
            return read;
        } catch (Exception e) {
            throw e;
        }
    }
}
