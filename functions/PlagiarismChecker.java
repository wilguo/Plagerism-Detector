package functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Plagiarism Detection tool for text files. Uses N-tuple comparision algorithm and
 * also checks for synonyms.
 *
 * @author Wilbert Guo
 */
public class PlagiarismChecker implements SimilarityChecker {

    //Create new ArrayList to keep track of group of synonyms
    private ArrayList<ArrayList<String>> synonyms = new ArrayList();

    /**
     * Calculates percentage similarity between file1 and file2.
     * File1 is original file and File2 is being checked against file1 for plagiarism.
     * @param file1
     * @param file2
     * @return Percentage plagiarised as int
     */
    @Override
    public int countSimilarity(ArrayList<ArrayList<String>> file1, ArrayList<ArrayList<String>> file2) {
        int sameTuples = 0;

        //loop through file1 and file2 tuples
        for(int i = 0; i < file1.size(); i++){
            for(int j = 0; j < file2.size(); j++){
                //increment number of sameTuples when the same tuple is found
                if(this.isTupleEqual(file1.get(i),file2.get(j))){
                    sameTuples++;
                }
            }
        }

        //Assumes file1 is the original file and file2 is being checked against file1
        float percentage = 0;

        //Make sure no division by zero
        if(file1.size() > 0){
            percentage = ((float) sameTuples / file1.size()) * 100;
        }

        return (int)percentage;
    }

    /**
     * Takes a file containing synonyms and converts groups of synonyms into ArrayList.
     * @param fileName
     * @throws IOException
     */
    public void recordSynonyms(String fileName) throws IOException{
        //Assume each line is a unique group of synonyms

        BufferedReader kbd = null;

        try{
            kbd = new BufferedReader(new InputStreamReader(System.in));

            Path path = Paths.get(fileName);
            try (BufferedReader fileInput = Files.newBufferedReader(path)) {
                String line = fileInput.readLine();
                // Reading from a file will produce null at the end.
                while (line != null) {
                    //add each group of synonyms(each line) to synonyms array
                    String[] words = line.toLowerCase().split(" ");
                    this.synonyms.add(new ArrayList<>(Arrays.asList(words)));
                    line = fileInput.readLine();
                }
            }
        }catch(IOException e){
            System.out.println("WARNING: Filename " + fileName + " could not be found.");
        }finally {
            if(kbd != null){
                kbd.close();
            }
        }
    }

    //Method to check if 2 tuples are equal based on values contained and synonyms
    private boolean isTupleEqual(ArrayList<String> tuple1, ArrayList<String> tuple2){
        int count = 0;

        //tuple1 and tuple2 are not equal if they have different sizes
        if(tuple1.size() != tuple2.size()){
            throw new IllegalArgumentException("Tuple sizes are not equal.");
        }

        //Loops through tuple
        for(int i = 0; i < tuple1.size(); i++){
            //check if values at index i are the same
            //also checks if values at index i are synonyms of each other
            if(tuple1.get(i).toLowerCase().equals(tuple2.get(i).toLowerCase())
                    || checkSynonym(tuple1.get(i), tuple2.get(i))){
                //increase count once a match has been found
                count++;
            }
        }

        return count == tuple1.size();
    }

    //Check if s1 and s2 are synonyms
    private boolean checkSynonym(String s1, String s2){
        boolean isFound = false;

        //loop through synonym dictionary
        for(ArrayList<String> arr: this.synonyms){
            //if both strings are found in synonym dictionary, then they are synonyms of each other
            if(arr.contains(s1.toLowerCase()) && arr.contains(s2.toLowerCase())){
                isFound = true;
            }
        }
        return isFound;
    }
}	