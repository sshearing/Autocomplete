import java.util.ArrayList;

/**
 * A Ternary Tree for time and space efficient text autocomplete.
 * Supports methods for adding data to the tree and for getting
 * autocomplete candidates. This Ternary Tree also maintains counts
 * of how many times a word has been added.
 *
 * @author Steven Shearing
 */
public class TernaryTree {

    // the root node of the tree.
    private Node root;

    /**
     * Construct an empty TernaryTree.
     */
    public TernaryTree() {
        this.root = new Node('m');
    }

    /**
     * Get the candidates for possible completions of a string fragment,
     * sorted by confidence level.
     *
     * @param key the string fragment to complete.
     * @return the list of candidate completions.
     */
    public ArrayList<Candidate> getCompletions(String key) {
        Node top = this.find(key, 0, this.root);
        ArrayList<Candidate> candidates = new ArrayList<Candidate>();
        if (top != null) {
            this.complete(top.child, key, candidates);
            candidates.sort((c1, c2) -> c2.compareTo(c1));
        }
        return candidates;
    }

    /**
     * Add a String to the tree.
     *
     * @param s the string to add to the tree.
     */
    public void add(String s) {
        this.add(s, 0, this.root);
    }

    // Recursive helper function to add a new string to the tree,
    // starting from root.
    private void add(String key, int i, Node curr) {

        // determine which child to visit next or if
        // we are done traversing the tree
        if (key.charAt(i) < curr.key) {
            if (curr.left == null) {
                curr.left = new Node(key.charAt(i));
            }
            this.add(key, i, curr.left);
        } else if (key.charAt(i) > curr.key) {
            if (curr.right == null) {
                curr.right = new Node(key.charAt(i));
            }
            this.add(key, i, curr.right);
        } else if (i + 1 < key.length()) {
            if (curr.child == null) {
                curr.child = new Node(key.charAt(i + 1));
            }
            this.add(key, i + 1, curr.child);
        } else {
            curr.increment();
        }

    }

    // Find the Node in the tree corresponding to the key argument.
    // If no corresponding Node exists, return null.
    private Node find(String key, int i, Node curr) {

        // if the current node is null,
        // then key is not in the tree.
        if (curr == null) {
            return null;
        }

        // otherwise, determine which child to visit next,
        // or determine if we are done traversing the tree.
        if (key.charAt(i) < curr.key) {
            return this.find(key, i, curr.left);
        } else if (key.charAt(i) > curr.key) {
            return this.find(key, i, curr.right);
        } else if (i + 1 < key.length()) {
            return this.find(key, i + 1, curr.child);
        } else {
            return curr;
        }

    }

    // Given a node, find all children of the node that have
    // a value greater than 0.
    private void complete(Node curr, String word,
                          ArrayList<Candidate> candidates) {

        // if curr == null, we're done
        if (curr == null) {
            return;
        }

        // if current represents a possible completion, add it
        // to the list of candidates.
        if (curr.value > 0) {
            candidates.add(new Candidate(word + curr.key, curr.value));
        }

        // traverse to children to find completions
        this.complete(curr.left, word, candidates);
        this.complete(curr.child, word + curr.key, candidates);
        this.complete(curr.right, word, candidates);

    }

    // Container class for a Node in the Tree - contains the node's key,
    // pointers to its children, and a value counting the number of times
    // that node has been added to the tree.
    private final class Node {

        private char key;       // char used to reach node
        private Node left;      // left sibling of the node
        private Node child;     // child of the node
        private Node right;     // right sibling of the node
        private Integer value;  // number of times node has been added

        // Construct a new Node with char key k
        private Node(char k) {
            this.key = k;
            this.value = 0;
            this.left = null;
            this.child = null;
            this.right = null;
        }

        // Increment the value of the Node.
        private void increment() {
            this.value++;
        }
    }
}
