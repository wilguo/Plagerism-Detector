package functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Class to convert text from file into tuples.
 *
 * @author Wilbert Guo
 */
public class Tuple {

    /**
     * Converts text in a file to tuples of size tupleSize.
     * @param fileName
     * @param tupleSize
     * @return ArrayList of tuples
     * @throws IOException
     */
    public static ArrayList<ArrayList<String>>  fileToArray(String fileName, int tupleSize)
            throws IOException, TupleException {
        ArrayList<ArrayList<String>> tupleArray = new ArrayList();
        BufferedReader kbd = null;

        try{
            kbd = new BufferedReader(new InputStreamReader(System.in));

            Path path = Paths.get(fileName);
            try (BufferedReader fileInput = Files.newBufferedReader(path)) {
                String line = fileInput.readLine();
                String document = "";
                // Reading from a file will produce null at the end.
                while (line != null) {
                    //ignore odd indenting
                    line = line.trim();
                    //add each line to giant string
                    document += line + " ";
                    line = fileInput.readLine();
                }
                //parse giant string into Array of tuples and add to tupleArray
                tupleArray.addAll(lineToArray(document, tupleSize));
            }
        }catch(IOException e){
            System.out.println("WARNING: Filename " + fileName + " could not be found.");
        }finally {
            if(kbd != null){
                kbd.close();
            }
        }

        return tupleArray;
    }

    /**
     * Converts String into ArrayList of tuples of size tupleSize.
     * @param line
     * @param tupleSize
     * @return ArrayList of tuples.
     */
    private static ArrayList<ArrayList<String>> lineToArray(String line, int tupleSize)
            throws TupleException {

        String lineToParse = line;

        //ArrayList to store each tuple of line
        ArrayList<ArrayList<String>> tupleArray = new ArrayList<>();

        //Get rid of punctuation in the string since it's insignificant in testing plagiarism
        lineToParse = lineToParse.replaceAll("[^a-zA-Z ]", "");

        //convert all letters to lowercase since capitalization of same word
        //is still counted as plagiarism
        //then store each word into string array
        String[] words = lineToParse.toLowerCase().split(" ");

        //check to see if tuplesize is greater than total amount of words
        if(words.length < tupleSize){
            throw new TupleException("WARNING: Tuple size too large for file parsing.");
        }

        //loop through number of words forming tuples of tupleSize
        for(int i = 0; i < words.length - (tupleSize - 1); i++){
            ArrayList<String> tuple = new ArrayList<>();
            for(int j = i; j < i + tupleSize; j++) {
                tuple.add(words[j]);
            }
            tupleArray.add(tuple);
        }

        return tupleArray;
    }
}