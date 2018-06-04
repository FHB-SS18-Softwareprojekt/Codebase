import java.io.File;
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

        int sentenceCount = Math.max(1, (int) (amount * sortedSentences.size()));

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


    public static void main(String[] args) {
        ConfigLink config = new ConfigLink(new File("./src/main/resources/config"));
        TextParser textParser = new TextParser(Locale.ENGLISH, config);
        Summarizer summarizer = new Summarizer(textParser);

        final String text = "Computer science is the study of the theory, experimentation, and engineering that form the basis for the design and use of computers. It is the scientific and practical approach to computation and its applications and the systematic study of the feasibility, structure, expression, and mechanization of the methodical procedures (or algorithms) that underlie the acquisition, representation, processing, storage, communication of, and access to, information. An alternate, more succinct definition of computer science is the study of automating algorithmic processes that scale. A computer scientist specializes in the theory of computation and the design of computational systems.[1] See glossary of computer science.\n" +
                "\n" +
                "Its fields can be divided into a variety of theoretical and practical disciplines. Some fields, such as computational complexity theory (which explores the fundamental properties of computational and intractable problems), are highly abstract, while fields such as computer graphics emphasize real-world visual applications. Other fields still focus on challenges in implementing computation. For example, programming language theory considers various approaches to the description of computation, while the study of computer programming itself investigates various aspects of the use of programming language and complex systems. Human–computer interaction considers the challenges in making computers and computations useful, usable, and universally accessible to humans.";

        System.out.println("100% Summary");
        List<String> summarized = summarizer.summarize(text, "Computer Science!", 1f);
        for (String sentence : summarized)
            System.out.println(" - " + sentence);


        System.out.println("50% Summary");
        summarized = summarizer.summarize(text, "Computer Science!", .5f);
        for (String sentence : summarized)
            System.out.println(" - " + sentence);


        System.out.println("20% Summary");
        summarized = summarizer.summarize(text, "Computer Science!", .2f);
        for (String sentence : summarized)
            System.out.println(" - " + sentence);
    }
}
