import functions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for program execution.
 *
 * @author Wilbert Guo
 */
public class Launcher {
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);

        try{
            System.out.print("Enter name of synonym file: ");
            String synonymFileName = sc.nextLine();
            System.out.print("Enter name of input file 1: ");
            String file1Name = sc.nextLine();
            System.out.print("Enter name of input file 2: ");
            String file2Name = sc.nextLine();
            System.out.println("Would you like to define tuple size? \nType Y for yes or N for no" +
                    "\nNOTE: Default tuple size is 3");
            String userResponse = sc.nextLine();
            int tupleSize;
            if(userResponse.equals("Y")){
                System.out.print("Enter tuple size: ");
                tupleSize = sc.nextInt();
            }else{
                tupleSize = 3;
            }

            System.out.println();

            //Print out instructions and then prompt user again if user has not entered
            //a value for the file names
            while (synonymFileName.trim().length() == 0 ||
                    file1Name.trim().length() == 0 ||
                    file2Name.trim().length() == 0 ||
                    userResponse.trim().length() == 0){
                System.out.println("\n=============INSTRUCTIONS=============");
                System.out.println("This is a plagiarism detector that uses n-tuple comparisons.");
                System.out.println("Please enter the file names for each file you are prompted for.");
                System.out.println("Synonym File - File that contains synonyms you want to check for.");
                System.out.println("File1 - Original file to check against");
                System.out.println("File2 - File to check for plagiarism. ");
                System.out.println("Tuple Size - Amount of words in each tuple to check against");
                System.out.println();
                System.out.print("Enter name of synonym file: ");
                synonymFileName = sc.nextLine();
                System.out.print("Enter name of input file 1: ");
                file1Name = sc.nextLine();
                System.out.print("Enter name of input file 2: ");
                file2Name = sc.nextLine();
                System.out.println("Would you like to define tuple size? \nType Y for yes or N for no" +
                        "\nNOTE: Default tuple size is 3");
                userResponse = sc.nextLine();
                if(userResponse.equals("Y")){
                    System.out.print("Enter tuple size: ");
                    tupleSize = sc.nextInt();
                }else{
                    tupleSize = 3;
                }
            }

            PlagiarismChecker checker = new PlagiarismChecker();

            ArrayList<ArrayList<String>> file1Tuple = Tuple.fileToArray(file1Name, tupleSize);
            ArrayList<ArrayList<String>> file2Tuple = Tuple.fileToArray(file2Name, tupleSize);
            checker.recordSynonyms(synonymFileName);

            int result = checker.countSimilarity(file1Tuple, file2Tuple);
            System.out.println("\nThe file " + file1Name + " is " + result + "% plagiarized.");
        }catch(TupleException e){
            System.out.println(e.getMessage());
        }


    }
}