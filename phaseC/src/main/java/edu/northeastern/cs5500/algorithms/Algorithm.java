package edu.northeastern.cs5500.algorithms;

import java.io.IOException;

import it.zielke.moji.MossException;

public interface Algorithm
{
    /**
     *
     * @param s1 String array
     * @param s2 Strinf array
     * @return Algorithm object
     */
    Algorithm getAlgorithmImplementation(String[] s1,String[] s2);

    /**
     *
     * @return array of similar lines
     */
    int[] getsimilarLineNos();

    /**
     *
     * @return plagiarism score
     */
    double getSimilarityScore();

    /**
    *
    * @return plagiarism score
    */
	double[][] getSimilarityResult() throws IOException, MossException;
}