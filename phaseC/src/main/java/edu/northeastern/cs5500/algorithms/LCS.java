package edu.northeastern.cs5500.algorithms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import it.zielke.moji.MossException;

/**
 * Applies Longest common sequence algorithm for comparison
 */
public class LCS implements Algorithm
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
     * Runs LCS algorithm on Two input string arrays to compare two files
     * @param file1 String array which consists of first file code lines
     * @param file2 String array which consists of second file code lines
     * @return integer value which represents Similarity score
     */
    @Override
    public Algorithm getAlgorithmImplementation(String[] file1, String[] file2) {
        
    	selectedLinesMap = new HashMap<>();
        double tlCnt = 0;
        int k=0;
        if(file1 == null || file2 == null)
        {
            return null;
        }
        similarLineNos = new int[file1.length];
        for(String i :file1)
        {
            int max = 0;
            int l=0;
            similarLineNos[k] = -1;
            i = i.trim();
            i = i.replaceAll("\\s+","");
            for (String j : file2)
            {
                j = j.trim();
                j = j.replaceAll("\\s+","");
                String result = lcs(i.toLowerCase(), j.toLowerCase());
                if(!selectedLinesMap.containsKey(l) && result.length() > max && result.length() >= i.length() * 0.95 && result.length() >= j.length() *0.95)
                {
                    similarLineNos[k] = l;
                    max = result.length();
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
     * LCS algorithm
     * @param str1 String from first file that needs to be compared
     * @param str2 String from second file that needs to be compared
     * @return
     */
    public String lcs(String str1, String str2)
    {
        int l1 = str1.length();
        int l2 = str2.length();
        
        int[][] arr = new int[l1 + 1][l2 + 1];
        
        for (int i = l1 - 1; i >= 0; i--)
        {
            for (int j = l2 - 1; j >= 0; j--)
            {
                if (str1.charAt(i) == str2.charAt(j))
                    arr[i][j] = arr[i + 1][j + 1] + 1;
                else
                    arr[i][j] = Math.max(arr[i + 1][j], arr[i][j + 1]);
            }
        }
        
        int i = 0;
        int j = 0;
        StringBuilder sb = new StringBuilder();
        while (i < l1 && j < l2)
        {
            if (str1.charAt(i) == str2.charAt(j))
            {
                sb.append(str1.charAt(i));
                i++;
                j++;
            }
            else if (arr[i + 1][j] >= arr[i][j + 1]) 
                i++;
            else
                j++;
        }
        return sb.toString();
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
