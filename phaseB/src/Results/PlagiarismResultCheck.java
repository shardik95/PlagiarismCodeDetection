package Results;
import Algorithms.*;

public class PlagiarismResultCheck extends PlagiarismResult {

    private Algos lcs; // an object of type LCS
    private Algos ld; // an object of type LD
    private Algos ast; // an object of type ast

    public PlagiarismResultCheck(String name1,String name2,String file1, String file2){
		super(name1,name2,file1,file2);
	}
	// Used to compare the files using algorithms
    @Override
    public void compare(AlgorithmFactory af) {
        lcs = af.makeLCS(getparsefile1(),getparsefile2());
        ld =  af.makeLD(getparsefile1(),getparsefile2());
        ast = af.makeAST(getparsefile1(),getparsefile2());
    }
    
    
    // Returns : an integer array which holds
    //           matched line numbers  of file1
    @Override
    public int[] getindexfile1() {
        return this.indexfile1;
    }

    // Returns : an integer array which holds
    //           matched line numbers of file2
    @Override
    public int[] getindexfile2() {
        return this.indexfile2;
    }

    // Returns : a string array which consists of data obtained
    //           after parsing file-1
    @Override
    public String[] getparsefile1() {
        return this.parsefile1;
    }

    // Returns : a string array which consists of data obtained
    //           after parsing file-2
    @Override
    public String[] getparsefile2() {
        return this.parsefile2;
    }

    // Returns : a integer value which represents the count of matching variables
    @Override
    public int getNumVar() {
        return this.numVar;
    }
    
    // Returns : a integer value which represents the count of the matching statements
    @Override
    public int getNumStatmts() {
        return this.numStatmts;
    }

    // Returns : a integer value which represents the count of matching functions
    @Override
    public int getNumFunc() {
        return this.numFunc;
    }

    // Returns : an integer value which represents the average similarity score
    @Override
    public int getAvgScore() {
        return this.avgScore;
    }
    
    // Returns : an object of type LCS
    public Algos getLcs() {
		return lcs;
	}

    // Returns : an object of type LD
	public Algos getLd() {
		return ld;
	}

	// Returns : an object of type AST
	public Algos getAst() {
		return ast;
	}
    
    
}
