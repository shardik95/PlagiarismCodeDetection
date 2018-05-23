package Algorithms;

//LCS represents the Longest common sequence algorithm object
//which is used for plagiarism check

//It takes two file as input which needs to be compared

public class LCS extends PlagiarismAlgos {

    public LCS(String[] parsefile1, String[] parsefile2) {
        super(parsefile1, parsefile2);
    }

    // Given : Two string files which needs to be compared 
    // Returns : an integer value which represents the similarity score obtained 
    //           using Longest common sequence algorithm
    @Override
    public int run(String[] file1, String[] file2) {
        this.score=0;
        return 0;
    }
}
