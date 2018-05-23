package Algorithms;

// An abstract class which implements Algos interface. 
// Used to initialize parsed files

public abstract class PlagiarismAlgos implements Algos {

    protected String[] parsefile1; // string array represents parsed file1
    protected String[] parsefile2; // string array to hold parsed file2
    protected int score; // represents the similarity score between the given two files

    PlagiarismAlgos(String[] parsefile1, String[] parsefile2){
        this.parsefile1=parsefile1;
        this.parsefile2=parsefile2;
    }


}
