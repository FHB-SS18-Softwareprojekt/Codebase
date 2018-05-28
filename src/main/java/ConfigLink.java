import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ConfigLink {
    private final File configFolder;
    private final Gson gson;

    private final Map<Locale, Set<String>> stopwords;

    public ConfigLink(File _configFolder) {
        this.configFolder = _configFolder;
        this.gson = new Gson();
        this.stopwords = new HashMap<>();

        for (File file : configFolder.listFiles())
            readConfig(file);
    }

    private boolean readConfig(File file) {
        String lang = file.getName();
        lang = lang.substring(0, lang.lastIndexOf("."));
        Locale locale = Locale.forLanguageTag(lang);
        if (locale != null) {
            try {
                JsonReader reader = new JsonReader(new FileReader(file));
                this.stopwords.put(locale, this.gson.fromJson(reader, HashSet.class));
                reader.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Set<String> getStopwords(Locale locale) {
        if (locale == null)
            throw new IllegalArgumentException("Locale may not be null");
        if (!this.stopwords.containsKey(locale))
            stopwords.put(locale, new HashSet<>());
        return stopwords.get(locale);
    }

    public boolean addStopword(Locale locale, String stopword) {
        if (locale == null || stopword == null)
            throw new IllegalArgumentException("Locale or Stopword may not be null");
        return getStopwords(locale).add(stopword);
    }

    public void saveConfig() {
        for (Locale locale : this.stopwords.keySet()) {
            try {
                File file = new File(this.configFolder, locale.toLanguageTag() + ".json");
                JsonWriter writer = new JsonWriter(new FileWriter(file));
                this.gson.toJson(this.gson.toJsonTree(this.stopwords.get(locale)), writer);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
