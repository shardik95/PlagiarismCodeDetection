package Results;

import Algorithms.AlgorithmFactory;

// An object of type Result holds all data required to compare the two files and 
// generate the result after plagiarism check
public interface Result {

	// Used to  generate the tokens
    public void tokenise();

    // Compares the given  two files to generate similarity score
    public void compare(AlgorithmFactory af);

    // Returns name of the file1 student 
    public String getStudentName1();

    // Returns name of the file2 student
    public String getStudentName2();

    // returns an array which stores indices of file1
    public int[] getindexfile1();

    // returns an array which stores indices of file2
    public int[] getindexfile2();

    // returns an string array which is obtained after parsing the file1
    public String[] getparsefile1();

    // Returns an string array which is obtained after parsing the file2
    public String[] getparsefile2();

    // Returns an integer values which represents the Number of variables
    public int getNumVar();

    // Returns an integer value which represents the Number of statements
    public int getNumStatmts();

    // Returns an integer value which represents the Number of functions
    public int getNumFunc();

    // Returns the average similarity score   
    public int getAvgScore();
}
