package functions;

import java.util.ArrayList;

/**
 * Interface to determine similarity between two ArrayList of tuples.
 * Chose to use interface because similarity can be defined differently
 * depending on which classes implement this interface.
 */
public interface SimilarityChecker {
    public int countSimilarity(ArrayList<ArrayList<String>> file1, ArrayList<ArrayList<String>> file2);
}