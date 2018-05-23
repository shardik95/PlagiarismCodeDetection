package edu.northeastern.cs5500.services;
import edu.northeastern.cs5500.algorithms.Algorithm;
import edu.northeastern.cs5500.algorithms.AlgorithmFactory;
import edu.northeastern.cs5500.controllers.GUIController;
import edu.northeastern.cs5500.mail.MailSender;
import edu.northeastern.cs5500.parser.Tokenizer;
import it.zielke.moji.MossException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.io.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;
@Service
public class ReportService {

	/**
     *  Mail Sender
     */
	@Autowired
	@Qualifier("MailSender")
	public MailSender mailSender;
	
	private String algoSelected;
	
	/**
     *  ReportService reference
     */
    @Autowired
    DbService dbService;
    
    /**
     *  GUI Controller
     */
	@Autowired
	private GUIController guiController;

	public void setAlgoSelected(String algoSelected) {
		this.algoSelected = algoSelected;
	}

	private static final String MOSS = "Moss";
	private static final String RUNALL= "Run All";
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private AlgorithmFactory alFac = new AlgorithmFactory();
	private Algorithm lcs = alFac.makeLCS();
	private Algorithm ld = alFac.makeLD();
	private Algorithm ast = alFac.makeAST();
	private Algorithm mossObj = alFac.makeMoss();
	protected String[] fileNames = new String[10000];
	private String directoryPath = "UploadedFiles/";
	/**
	 * function to get report
	 * @param uploadingFiles Files
	 * @param fileindex1 type integer
	 * @param fileindex2 type integer
	 * @return int array
	 * @throws IOException
	 */
	public int[] fetchPlagiarismReportHelper(int fileindex1, int fileindex2)  {
		ArrayList<String[]> strArrayLst = new ArrayList<>();

		File mainFolder= new File(directoryPath);
		File[] allFiles = mainFolder.listFiles();
		int fileToConsider = fileindex1;
		try
		{
			for(int j=0; j < 2; j++)
			{
				String[] stringBuffer = addFileContentToStringBuffer(allFiles[fileToConsider]);
				
				List<String> list;
				list = new ArrayList<>(Arrays.asList(stringBuffer));
				list.removeAll(Collections.singleton(null));
				stringBuffer = list.toArray(new String[list.size()]);
				strArrayLst.add(stringBuffer);
				fileToConsider = fileindex2;
			}
			
			String[] code1=strArrayLst.get(0);
			String[] code2=strArrayLst.get(1);

			
			int[] resSimilarLineNoLCS;
			int[] resSimilarLineNoLD;
			int[] resObj;
			
			switch(algoSelected){

			case "LCS":
				Algorithm lcsObj = lcs.getAlgorithmImplementation(code1,code2);
				resObj = lcsObj.getsimilarLineNos();
				break;

			case "Levenshtein Distance":
				
				Algorithm ldObj = ld.getAlgorithmImplementation(code1,code2);
				resObj = ldObj.getsimilarLineNos();
				break;

			case RUNALL:
				
				ldObj = ld.getAlgorithmImplementation(code1,code2);
				lcsObj = lcs.getAlgorithmImplementation(code1,code2);
				resSimilarLineNoLCS = lcsObj.getsimilarLineNos();
				resSimilarLineNoLD = ldObj.getsimilarLineNos();
				resObj = new int[resSimilarLineNoLCS.length];
				for(int k=0; k< resSimilarLineNoLCS.length; k++)
				{
					resObj[k] = resSimilarLineNoLCS[k];
					if(resSimilarLineNoLCS[k] ==-1 &&  resSimilarLineNoLD[k] != -1)
					{
						resObj[k] = resSimilarLineNoLD[k];
					}

				}
				break;


			default : throw new IllegalArgumentException();

			}


			return resObj;


		}
		catch (IOException e)
		{
			logger.debug(e.toString());
		}
		return new int[0];
	}

	/**
	 * function to compare files
	 * @param algorithmType String
	 * @return Comparison result
	 * @throws MossException 
	 * @throws IOException
	 * @throws SQLException 
	 */
	public double[][] compareFilesHelper(String algorithmType) throws IOException, MossException, SQLException {
		HashMap<String,Integer> fileComparedMap = new HashMap<>();
		ArrayList<String[]> allCodeLst;
		File mainFolder= new File(directoryPath);
		File[] allFiles = mainFolder.listFiles();
		algoSelected = algorithmType;
		allCodeLst = fetchAllCodeArrayList(allFiles);
		String[] allFilepath = fetchAllFilePaths();
		int noOfFiles = allCodeLst.size();
		int noOfComparisons = (noOfFiles * (noOfFiles-1));
		double[][] results;
		double[][] thresholdRes;
		if(algoSelected.equals(RUNALL))  results = new double[noOfComparisons*2][6];
		else if(algoSelected.equals(MOSS)) results = new double[noOfComparisons*2][3];
		else results = new double[noOfComparisons*2][3];
		
		thresholdRes = new double[noOfComparisons*2][3];
		int thresholdCntr =0;
		int threshold = dbService.getThresholdHelper();
		int m =0;
		if(algoSelected.equals(MOSS))
		{

			return sortBySimilarityResult(mossObj.getSimilarityResult());
		}
		else
		{

			for(int k=0;  k < allCodeLst.size(); k++)
			{
				for(int l=0; l < allCodeLst.size(); l++)
				{
					String[] firstFileStudName = fileNames[k].split("_");
					String[] secondFileStudName = fileNames[l].split("_");
					String firstStudName = firstFileStudName[0];
					String seconStudName = secondFileStudName[0];


					if(!firstStudName.equals(seconStudName) && k !=l)
					{
						String path1 = allFilepath[k];
						String path2 = allFilepath[l];
						String[] patharray1 = new String[]{path1};
						String[] patharray2 = new String[]{path2};
						results[m][0] = k;
						results[m][1] = l;
						Tokenizer t1=new Tokenizer();
						Tokenizer t2=new Tokenizer();
						fileComparedMap.put(l+"-"+k, 1);
						fileComparedMap.put(k+"-"+l, 1);
						String[] code1=t1.normaliseCode(allCodeLst.get(k));
						String[] code2=t2.normaliseCode(allCodeLst.get(l));
						double result;
						double lcsRes;
						double ldRes;
						double astRes;
						Algorithm lcsObj;
						Algorithm ldObj;
						switch (algoSelected)
						{ 
						case "LCS":
							lcsObj = lcs.getAlgorithmImplementation(code1, code2);
							result = lcsObj.getSimilarityScore();
							results[m][2] = result;
							if(result > threshold)
							{
								thresholdRes[m][0] = k;
								thresholdRes[m][1] = l;
								thresholdRes[m][2] = result;
								thresholdCntr++;
							}
							m++;
							break;
						case "Levenshtein Distance" :   	
							ldObj = ld.getAlgorithmImplementation(code1, code2);
							result = ldObj.getSimilarityScore();
							results[m][2] = result;
							if(result > threshold)
							{
								thresholdRes[m][0] = k;
								thresholdRes[m][1] = l;
								thresholdRes[m][2] = result;
								thresholdCntr++;
							}
							m++;
							break;
						case "AST Comparison":
							ast.getAlgorithmImplementation(patharray1, patharray2);
							result = ast.getSimilarityScore();
							results[m][2] = result;
							if(result > threshold)
							{
								thresholdRes[m][0] = k;
								thresholdRes[m][1] = l;
								thresholdRes[m][2] = result;
								thresholdCntr++;
							}
							m++;
							break;

						case RUNALL:    
							lcsObj = lcs.getAlgorithmImplementation(code1, code2);
							ldObj = ld.getAlgorithmImplementation(code1, code2);
							lcsRes = lcsObj.getSimilarityScore();
							ldRes = ldObj.getSimilarityScore();
							ast.getAlgorithmImplementation(patharray1, patharray2);
							astRes = ast.getSimilarityScore();
							result = polynomialFunction(t1,allCodeLst.get(k),t2,allCodeLst.get(l),lcsRes,ldRes,astRes);
							results[m][2] = result;
							results[m][3] = lcsRes;
							results[m][4] = ldRes;
							results[m][5] = astRes;
							if(result > threshold)
							{
								thresholdRes[m][0] = k;
								thresholdRes[m][1] = l;
								thresholdRes[m][2] = result;
								thresholdCntr++;
							}
							m++;
							break;

						default:throw new IllegalArgumentException();
						}

					}

				}


			}
			if(thresholdCntr > 0)
			{
				emailReport(thresholdRes);
			}
			results = sortBySimilarityResult(results);
			return results;
		}
	}

	/**
	 * Stores the content of a file in a string array and appends it to list of string array
	 * @param allFiles
	 * @return List which holds file content
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private ArrayList<String[]> fetchAllCodeArrayList(File[] allFiles) throws  IOException
	{
		ArrayList<String[]> allCodeLst = new ArrayList<>();


		Arrays.sort(allFiles, (f1, f2) -> Long.valueOf(f1.lastModified()).compareTo(f2.lastModified()));
		for(int j=0; j < allFiles.length; j++)
		{
			fileNames[j] = allFiles[j].getName();
			String[] stringBuffer = addFileContentToStringBuffer(allFiles[j]);
			List<String> list;
			list = new ArrayList<>(Arrays.asList(stringBuffer));
			list.removeAll(Collections.singleton(null));
			stringBuffer = list.toArray(new String[list.size()]);
			allCodeLst.add(stringBuffer);
		}
		logger.debug("upload file functionality");
		return allCodeLst;
	}



	/** Reads the content of a file on to a buffer
	 * @return 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private String[] addFileContentToStringBuffer(File file) throws IOException
	{
		String[] stringBuffer;
		List<String> list;
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) 
		{

			stringBuffer = new String[10000];
			String line;
			int i = 0;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer[i] = line + "\n";
				i++;
			}
		}
		return stringBuffer;
	}

	/**
	 * Sort the array content
	 * @param arr type Double
	 * @return Sorted Array
	 */
	private double[][] sortBySimilarityResult(double[][] arr)
	{
		Arrays.sort(arr, (o1, o2) -> {
			if(o2[2] > o1[2])
				return 1;
			else
				return -1;
		});

		return arr;
	}

	/**
	 *
	 * @return String array with file paths
	 */
	private String[] fetchAllFilePaths()
	{


		File mainFolder= new File(directoryPath);
		File[] allUploadedFiles = mainFolder.listFiles();
		Arrays.sort(allUploadedFiles, (f1, f2) -> Long.valueOf(f1.lastModified()).compareTo(f2.lastModified()));

		String[] allFilesPaths = new String[allUploadedFiles.length];

		int i =0;
		for(File f: allUploadedFiles)
		{
			allFilesPaths[i] = f.getPath();
			i++;
		}
		return allFilesPaths;
	}


	/**
	 *
	 * @param t1 type Tokenizer
	 * @param file1 type string
	 * @param t2 type Tokenizer
	 * @param file2 type string
	 * @param lcs lcs score
	 * @param ld ld score
	 * @param ast ast score
	 * @return average
	 */
	private double polynomialFunction(Tokenizer t1,String[] file1,Tokenizer t2,String[] file2,double lcs,double ld,double ast){

		Set<String> functionfile1=t1.findFunction(file1);
		Set<String> functionfile2=t2.findFunction(file2);
		Set<String> variablefile1=t1.findVariables(file1);
		Set<String> variablefile2=t2.findVariables(file2);
		Map<Integer,Integer> filemap1=t1.getLineMapping();
		Map<Integer,Integer> filemap2=t2.getLineMapping();

		int nofuntion1=functionfile1.size();
		int nofunction2=functionfile2.size();
		int novariable1=variablefile1.size();
		int novaraible2=variablefile2.size();
		int line1=filemap1.size();
		int line2=filemap2.size();

		double astper;
		double lcsper;
		double ldper;

		if(Math.min(line1,line2)>500){
			astper=0.4;
			lcsper=0.35;
			ldper=0.25;
		}
		else if(Math.min(nofuntion1,nofunction2)>10){
			astper=0.60;
			lcsper=0.30;
			ldper=0.1;
		}
		else if(Math.min(novariable1,novaraible2)>25&&Math.min(line1,line2)>500&&Math.min(nofuntion1,nofunction2)<10){
			lcsper=0.4;
			ldper=0.4;
			astper=0.2;
		}
		else{
			astper=0.33;
			lcsper=0.34;
			ldper=0.33;
		}

		return  (astper*ast+lcsper*lcs+ldper*ld)/(astper+ldper+lcsper);

	}
	
	public void emailReport(double[][] comparisonresult) throws SQLException
	{
		
		
		StringBuilder emailBody = new StringBuilder();
		String userName = guiController.getUSERNAME();
		String toAddress = dbService.getEmailHelper(userName);
		for(int i=0; i< comparisonresult.length; i++)
		{
			if((int)comparisonresult[i][0]== 0.0 && (int)comparisonresult[i][1] == 0.0)
			{
				continue;
			}
			else
			{
				String student1Name = fileNames[(int)comparisonresult[i][0]].split("_")[0];
				String student1Version = fileNames[(int)comparisonresult[i][0]].split("_")[1];
				String student2Name = fileNames[(int)comparisonresult[i][1]].split("_")[0];
				String student2Version = fileNames[(int)comparisonresult[i][1]].split("_")[1];
				double similarityScore =  (int) Math.round( comparisonresult[i][2]);
				emailBody.append(student1Name + "( version "+ student1Version +")    /" + student2Name + "( version "+ student2Version +")"
						+"  : " + similarityScore +"%  (Similarity score)" );
				emailBody.append("\n");
			}
			
		}
		
		mailSender.sendMail(emailBody.toString(), toAddress);
		
	}


}

