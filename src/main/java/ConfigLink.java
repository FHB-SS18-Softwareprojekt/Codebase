import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class ConfigLink {
    private final File configFile;
    private final Gson gson;

    private Set<String> stopwords;

    public ConfigLink(File _configFile) {
        this.configFile = _configFile;
        this.gson = new Gson();

        try {
            JsonReader reader = new JsonReader(new FileReader(this.configFile));
            this.stopwords = this.gson.fromJson(reader, HashSet.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getStopwords() {
        return stopwords;
    }

}
