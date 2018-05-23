package Algorithms;

public class AlgorithmFactory {

	

	// Given: Two string files which needs to be compared
	// Returns: an object of type LCS
	public LCS makeLCS(String[] file1, String[] file2)
	{
		return new LCS(file1, file2);
	}

	// Given: Two string files which needs to be compared
	// Returns: an object of type LD
	public LD makeLD(String[] file1, String[] file2)
	{
		return new LD(file1, file2);
	}

	// Given: Two string files which needs to be compared
	// Returns: an object of type AST
	public AST makeAST(String[] file1, String[] file2)
	{
		return new AST(file1, file2);
	}

}
