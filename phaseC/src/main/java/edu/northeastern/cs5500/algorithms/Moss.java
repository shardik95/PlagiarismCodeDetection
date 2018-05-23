package edu.northeastern.cs5500.algorithms;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.northeastern.cs5500.moss.MossApi;
import it.zielke.moji.MossException;

public class Moss implements Algorithm{




	@Override
	public Algorithm getAlgorithmImplementation(String[] s1, String[] s2) {

		return this;
	}

	/**
	 *
	 * @return array of similar lines
	 */
	@Override
	public int[] getsimilarLineNos() {
		return new int[0];
	}


	/**
	 *
	 * @return Not used in for Moss implementation
	 */
	@Override
	public double getSimilarityScore() {
		return 0;
	}

	@Override
	public double[][] getSimilarityResult() throws IOException, MossException {
		MossApi mapi=new MossApi();
		Document doc = Jsoup.connect(mapi.getMossURL()).get();
		Elements newsHeadlines = doc.select("body > table > tbody > tr > td > a ");
		String assignmentPath = "Assignment";

		File assFiles = new File(assignmentPath);
		long noOfFolders = assFiles.length();
		int totalNoOfComparisons = (int)noOfFolders * (int)noOfFolders;
		double[][] result = new double[totalNoOfComparisons][3];
		for (int i =0; i< newsHeadlines.size()-1; i=i+2) {

			String firstFileData = newsHeadlines.get(i).text();
			String secondFileData = newsHeadlines.get(i+1).text();
			Matcher m1 = Pattern.compile("/(\\w+)/\\s*\\((.*?)%\\)").matcher(firstFileData);
			Matcher m2 = Pattern.compile("/(\\w+)/\\s*\\((.*?)%\\)").matcher(secondFileData);
			if(m1.find() && m2.find())
			{
				String firstFileName =m1.group(1);
				String secondFileName = m2.group(1);
				int firstFileIndex = Integer.parseInt(firstFileName.split("_")[3]);
				int secondFileIndex = Integer.parseInt(secondFileName.split("_")[3]);
				result[i][0] = firstFileIndex;
				result[i][1] = secondFileIndex;
				result[i][2] = Double.parseDouble(m1.group(2));
				result[i+1][0] = secondFileIndex;
				result[i+1][1] = firstFileIndex;
				result[i+1][2] = Double.parseDouble(m2.group(2));
			}
		}
		return result;
	}
}
