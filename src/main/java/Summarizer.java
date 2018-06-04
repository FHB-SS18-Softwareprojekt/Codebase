import java.util.*;

public class Summarizer {

    private final TextParser parser;

    public Summarizer(TextParser parser) {
        this.parser = parser;
    }

    public List<String> summarize(String text, String title, float amount) {
        Map<String, Integer> keywords = this.parser.getKeywords(text);
        Set<String> titleWords = this.parser.splitTitle(title);
        List<String> sentences = this.parser.getSentences(text);

        Map<String, Float> scoredSentences = computeSentenceScores(sentences, keywords, titleWords);

        ArrayList<String> sortedSentences = new ArrayList<>(sentences);
        sortedSentences.sort((s1, s2) -> Float.compare(scoredSentences.get(s1), scoredSentences.get(s2)));

        int sentenceCount = Math.max(1, (int)(amount*sortedSentences.size()));

        return sortedSentences.subList(0, sentenceCount);
    }

    private final static float PRIO_KEYWORD = 2.0f;
    private final static float PRIO_TITLE = 1.5f;
    private final static float PRIO_LENGTH = 0.75f;

    private HashMap<String, Float> computeSentenceScores(List<String> sentences, Map<String, Integer> keywords, Set<String> titleWords) {
        HashMap<String, Float> map = new HashMap<>();

        for (int iSentence = 0; iSentence < sentences.size(); iSentence++) {
            String sentence = sentences.get(iSentence);
            String[] words = this.parser.splitWords(sentence);

            float weight = PRIO_KEYWORD * getKeywordWeight(words, keywords);
            weight += PRIO_TITLE * getTitleWeight(words, titleWords);
            weight += PRIO_LENGTH * words.length;
            map.put(sentence, weight);
        }
        return map;
    }

    private float getKeywordWeight(String[] sentenceWords, Map<String, Integer> keywords) {
        float weight = 0;
        for (String word : sentenceWords)
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

}
