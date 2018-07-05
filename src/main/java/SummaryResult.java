import java.util.List;

public class SummaryResult {

    private final boolean success;
    private final List<Sentence> sentenceList;
    private final String errorMessage;

    private SummaryResult(boolean success, List<Sentence> sentenceList, String errorMessage) {
        this.success = success;
        this.sentenceList = sentenceList;
        this.errorMessage = errorMessage;
    }

    public static SummaryResult success(List<Sentence> sentenceList) {
        return new SummaryResult(true, sentenceList, null);
    }

    public static SummaryResult fail(String errorMessage) {
        return new SummaryResult(false, null, errorMessage);
    }

    public boolean wasSuccessful() {
        return success;
    }

    public List<Sentence> getSentenceList() {
        return sentenceList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
