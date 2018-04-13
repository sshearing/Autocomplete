/**
 * Class to store Autocomplete completions. Stores a string and
 * confidence level. Implements Comparable for Candidate sorting.
 *
 * @author Steven Shearing
 */
public class Candidate implements Comparable<Candidate> {

    // the Candidate's word
    private String word;

    // the Candidate's confidence of the word
    private Integer confidence;

    /**
     * Construct an Autocomplete Candidate.
     *
     * @param w the Candidate's word.
     * @param c the Candidate's confidence.
     */
    public Candidate(String w, Integer c) {
        this.word = w;
        this.confidence = c;
    }

    /**
     * Get the Candidate's word.
     *
     * @return the Candidate's word.
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Get the Candidate's confidence.
     *
     * @return the Candidate's confidence.
     */
    public Integer getConfidence() {
        return this.confidence;
    }

    /**
     * Get the String representation of the Candidate.
     *
     * @return the string representation of the Candidate.
     */
    public String toString() {
        return this.word + " (" + this.confidence.toString() + ")";
    }

    @Override
    public int compareTo(Candidate target) {
        if (target == null) {
            return 1;
        }
        int confDiff = this.confidence.compareTo(target.getConfidence());
        if (confDiff == 0) {
            return 0 - this.word.compareTo(target.getWord());
        } else {
            return confDiff;
        }
    }
}
