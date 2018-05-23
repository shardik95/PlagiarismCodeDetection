package edu.northeastern.cs5500.algorithms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import it.zielke.moji.MossException;

/**
 * Applies the Levenshtein's Distance algorithm for comparison
 */
public class LevenshteinDistance implements Algorithm
{
    // instance variables
	private Map<Integer, Integer> selectedLinesMap;
    private int[] similarLineNos;
    private int similarityScore =0;

    /**
     *
     * @return plagiarism score
     */
    public double getSimilarityScore()
    {
        return this.similarityScore;
    }



    /**
     *
     * @return array of similar lines
     */
    public int[] getsimilarLineNos()
    {
        return this.similarLineNos;
    }

    /**
     * Runs Levenshtein's distance algorithm on Two input string arrays to compare two files
     * @param file1 String array which consists of first file code lines
     * @param file2 String array which consists of second file code lines
     * @return integer value which represents Similarity score
     */
    public Algorithm getAlgorithmImplementation(String[] file1, String[] file2) {

    	selectedLinesMap = new HashMap<>();
        double tlCnt = 0;
        int k=0;
        if(file1 == null || file2 == null)
        {
            return null;
        }
        similarLineNos = new int[file1.length];
        for(String i : file1)
        {
            int min = Integer.MAX_VALUE;
            int l=0;
            similarLineNos[k] = -1;
            i = i.trim();
            i = i.replaceAll("\\s+","");
            for (String j : file2)
            {
                j = j.trim();
                j = j.replaceAll("\\s+","");
                int result =  lD(i.toLowerCase(), j.toLowerCase());
                if(!selectedLinesMap.containsKey(l) &&  (result < min || min == Integer.MAX_VALUE) && result <= 0.3 * i.length())
                {
                    similarLineNos[k] = l;
                    min = result;
                }
                selectedLinesMap.put(similarLineNos[k], 1);
                l++;
            }
            if(similarLineNos[k] != -1)
            {
                
                tlCnt++;
            }
            k++;
        }
        
        
        
        double res = (tlCnt/file1.length) * 100;
        
        
        this.similarityScore = (int)Math.floor(res);
        return this;
        
    }
    
    /**
     * Return the minimum of three values
     * @param a integer value
     * @param b integer value
     * @param c integer value
     * @return the minimum value
     */
    private int min(int a, int b, int c) {
        int mi;
        
        mi = a;
        if (b < mi) {
            mi = b;
        }
        if (c < mi) {
            mi = c;
        }
        return mi;
    }
    
    /**
     * Compute Levenshtein's Distance
     *
     * @param str1 a String str1
     * @param str2 a String str2
     * @return the distance as an integer
     */
    public int lD(String str1, String str2) {
        int[][] matrix; // matrix
        int n; // length of str1
        int m; // length of str2
        int i; // iterates through str1
        int j; // iterates through str2
        char str1i; // ith character of str1
        char str2j; // jth character of str2
        int cost; // cost
        
        // Step 1
        n = str1.length();
        m = str2.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        matrix = new int[n + 1][m + 1];
        
        // Step 2
        for (i = 0; i <= n; i++) {
            matrix[i][0] = i;
        }
        
        for (j = 0; j <= m; j++) {
            matrix[0][j] = j;
        }
        
        // Step 3
        for (i = 1; i <= n; i++) {
            str1i = str1.charAt(i - 1);
            
            // Step 4
            for (j = 1; j <= m; j++) {
                str2j = str2.charAt(j - 1);
                
                // Step 5
                if (str1i == str2j) {
                    cost = 0;
                } else {
                    cost = 1;
                }
                
                // Step 6
                matrix[i][j] = min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1, matrix[i - 1][j - 1] + cost);
            }
        }
        
        // Step 7
        return matrix[n][m];
    }

    
    /**
    *
    * @return Returns similarity score for moss, not used here
    */
	@Override
	public double[][] getSimilarityResult() throws IOException, MossException {
		return new double[0][0];
	}


}
