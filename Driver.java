import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Driver class for the AutocompleteProvider.
 *
 * @author Steven Shearing
 */
public final class Driver {

    /**
     * Main driver class is final and not instantiable.
     */
    private Driver() {
    }

    /**
     * Driver method for the Autocomplete Provider. Reads in user input
     * to train the algorithm and complete string fragments.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AutocompleteProvider acp = new AutocompleteProvider();

        // Print out welcome menu
        System.out.println("Welcome.");
        System.out.println("Type \"t\" to train from stdin.");
        System.out.println("Type \"f\" to train from file.");
        System.out.println("Type \"i\" to complete a string fragment.");
        System.out.println("Type \"q\" to quit.");
        System.out.print("Please enter choice: ");

        // variable to store user input
        String input;

        // while the user has not signaled end of input or quit
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            try {
                switch (input.charAt(0)) {
                    case 't':
                        System.out.print("Enter Training Text: ");
                        input = scanner.nextLine();
                        acp.train(input);
                        break;
                    case 'f':
                        System.out.print("Enter training file: ");
                        input = scanner.nextLine();
                        acp.train(fileToString(input));
                        break;
                    case 'i':
                        System.out.print("Enter Input Fragment: ");
                        input = scanner.nextLine();
                        printACResults(acp.getWords(input));
                        break;
                    case 'q':
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (IOException e) {
                System.out.println("Invalid filename!");
	    } catch (StringIndexOutOfBoundsException e) {
		System.out.println("Empty input!");
            } catch (NoSuchElementException e) {
                break;
	    }
            System.out.print("Please enter choice: ");
        }
        System.out.println("\nEnd up input reached. Quitting.");
    }

    // Given a list of Candidates, print out their words and confidences.
    private static void printACResults(ArrayList<Candidate> candidates) {
        if (candidates.size() == 0) {
            System.out.println("Algorithm could not find any completions.");
        } else {
            for (Candidate c: candidates) {
                System.out.print(c.toString() + ", ");
            }
            System.out.println();
        }
    }

    // Grab the contents of a file and store it in a string.
    // Not a great strategy for very large files.
    private static String fileToString(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }
}
