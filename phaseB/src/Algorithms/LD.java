package Algorithms;


// LD represents the Levenshtein distance algorithm object
//which is used for plagiarism check

//It takes two file as input which needs to be compared
public class LD extends PlagiarismAlgos {
    public LD(String[] parsefile1, String[] parsefile2) {
        super(parsefile1, parsefile2);
    }

    // Given : Two string files which needs to be compared 
    // Returns : an integer value which represents the similarity score obtained 
    //           using Levenshtein distance algorithm
    @Override
    public int run(String file1[], String file2[]) {
        this.score=0;
        return 0;
    }
}
