import java.util.Collections;
import java.util.ArrayList;

/**
 * An AutocompleteProvider. Can be trained online while providing
 * autocomplete suggestions.
 *
 * @author Steven Shearing
 */
public class AutocompleteProvider {

    // Ternary Tree datastructure to store training data
    // and get completions in a time and space efficient manor.
    private TernaryTree tree;

    /**
     * Construct a new AutocompleteProvider.
     */
    public AutocompleteProvider() {
        this.tree = new TernaryTree();
    }

    /**
     * Get all the possible completions, ordered by confidence,
     * which have been seen in training. Filters out quotations and
     * leading/trailing whitespace in the string fragment.
     *
     * @param fragment the string fragment to complete.
     * @return a list of all possible completions for the fragment.
     */
    public ArrayList<Candidate> getWords(String fragment) {
        fragment = fragment.replaceAll("\"", "").toLowerCase().trim();
        return this.tree.getCompletions(fragment);
    }

    /**
     * Train the AutocompleteProvider algorithm on new data.
     * The new data will be used in conjuction with any previously
     * used training data. Training data is filtered to include
     * only english letters, hyphens, and apostraphes.
     *
     * @param passage the new training data, in String form.
     */
    public void train(String passage) {
        passage = passage.replaceAll("[^a-zA-Z-\'\\s]", "");
	passage = passage.trim().toLowerCase();

	// split passage by space and store into an Arraylist
	ArrayList<String> words = new ArrayList<String>();
	for (String word : passage.split("\\s+")) {
	    words.add(word);
	}

	// shuffle the words
	Collections.shuffle(words);

	// for every word, add it to the TernaryTree, in random order
        for (int i = 0; i < words.size(); i++) {
            this.tree.add(words.get(i));
        }
    }
}
