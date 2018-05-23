package Algorithms;

// Algorithms is any class that implements Algos interface.
// Algos represents the algorithms used to check for plagiarism
public interface Algos {
  
	// Given: Two files on which we need to run the algorithm for comparison
	// Returns : an integer value which represents the score of similarity
    public int run(String[] file1,String[] file2);
}
