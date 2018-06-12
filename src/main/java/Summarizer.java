import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Summarizer {

    private final TextParser parser;

    public Summarizer(TextParser parser) {
        this.parser = parser;
    }

    public List<Sentence> summarize(String text, String title, float amount) {
        Map<String, Integer> keywords = this.parser.getKeywords(text);
        Set<String> titleWords = this.parser.splitTitle(title);
        List<Sentence> sentences = this.parser.getSentences(text);

        computeSentenceScores(sentences, keywords, titleWords);

        ArrayList<Sentence> sortedSentences = new ArrayList<>(sentences);
        sortedSentences.sort((s1, s2) -> Float.compare(s1.getSentenceScore(), s2.getSentenceScore()));

        int sentenceCount = Math.max(1, (int) (amount * sortedSentences.size()));

        return sortedSentences.subList(0, sentenceCount);
    }

    private final static float PRIO_KEYWORD = 2.0f;
    private final static float PRIO_TITLE = 1.25f;
    private final static float PRIO_LENGTH = 0.5f;
    private final static float PRIO_POS = 1.5f;

    private void computeSentenceScores(List<Sentence> sentences, Map<String, Integer> keywords, Set<String> titleWords) {
        //HashMap<String, Float> map = new HashMap<>();

        int sentenceCount = sentences.size();
        for (int iSentence = 0; iSentence < sentenceCount; iSentence++) {
            Sentence sentence = sentences.get(iSentence);
            String sentenceText = sentence.getText();
            String[] words = this.parser.splitWords(sentenceText);

            float weight = PRIO_KEYWORD * getKeywordWeight(words, keywords);
            weight += PRIO_TITLE * getTitleWeight(words, titleWords);
            weight += PRIO_LENGTH * words.length;
            weight += PRIO_POS * getSentencePriority(iSentence, sentenceCount);
            sentence.setSentenceScore(weight);
        }
        //return map;
    }

    private float getKeywordWeight(String[] sentenceWords, Map<String, Integer> keywords) {
        float weight = 0;
        for (String word : sentenceWords)
            if (keywords.containsKey(word))
                weight += keywords.get(word);
        return weight / sentenceWords.length;
    }

    private float getTitleWeight(String[] sentenceWords, Set<String> titleWords) {
        float count = 0;
        for (String word : sentenceWords)
            if (titleWords.contains(word))
                count++;
        return count / titleWords.size();
    }

    /**
     * Based on a research paper by
     * <p>
     * See page 4 in this document:
     * https://www.researchgate.net/publication/237252504_Sentence_Extraction_Based_Single_Document_Summarization
     *
     * @return a percentual float number representing the importance of the sentence based on its position
     */
    private float getSentencePriority(int position, int sentenceCount) {
        float relativePosition = position / (float) sentenceCount;
        if (relativePosition < .1f)
            return 0.17f;
        else if (relativePosition < .2f)
            return 0.23f;
        else if (relativePosition < .3f)
            return 0.14f;
        else if (relativePosition < .4f)
            return 0.08f;
        else if (relativePosition < .5f)
            return 0.05f;
        else if (relativePosition < .6f)
            return 0.04f;
        else if (relativePosition < .7f)
            return 0.06f;
        else if (relativePosition < .8f)
            return 0.04f;
        else if (relativePosition < .9f)
            return 0.04f;
        else
            return 0.15f;
    }


    public static void main(String[] args) {
        ConfigLink config = new ConfigLink(new File("./src/main/resources/config"));
        TextParser textParser = new TextParser(Locale.ENGLISH, config);
        Summarizer summarizer = new Summarizer(textParser);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Pfad zu Datei eingeben:");
        String path = scanner.nextLine();
        if (path.isEmpty() || !path.endsWith(".txt"))
            System.out.println("Pfad unzulässig");

        File file = new File(path);

        if (file.exists() && file.isFile()) {
            System.out.println("Zusammenfassungs-Größe angeben (Wert zwischen 30 und 80)");
            int quota = scanner.nextInt();
            float f_quota = quota / 100f;

            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                String title = reader.readLine();
                String line;
                StringBuilder text = new StringBuilder();
                while ((line = reader.readLine()) != null)
                    text.append(line).append("\n");

                System.out.println(quota + "% Zusammenfassung:");
                List<Sentence> summarized = summarizer.summarize(text.toString(), title, f_quota);
                summarized.sort(Comparator.comparingInt(Sentence::getPosition));

                for (Sentence sentence : summarized)
                    System.out.println(" - " + sentence.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
