public class Sentence {

    private final String text;
    private final int position;
    private float sentenceScore;

    public Sentence(String text, int position){
        this.text=text;
        this.position=position;
    }

    public float getSentenceScore() {
        return sentenceScore;
    }

    public void setSentenceScore(float sentenceScore) {
        this.sentenceScore = sentenceScore;
    }

    public String getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }
}
