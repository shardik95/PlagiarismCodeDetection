package edu.northeastern.cs5500.services;

import edu.northeastern.cs5500.mail.MailSender;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UploadService {

	@Autowired
	@Qualifier("MailSender")
	public MailSender mailSender;
	private static final String PATH = "UploadedFiles";
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String PATHVARIABLE ="/";
	private Map<String, Integer> currStudentFileVersionMap = new HashMap<>();
	private Map<String, String> studentFilesForVersion = new HashMap<>();
	private Integer totalFilesUploaded =0;
	private Integer totalFilesInAssignFolder =0;
	private List<String> studentNameList= new ArrayList<>();
	private static final String ASSIGNMENTPATH = "Assignment";
	private static final String VERSION = "_version_";
	/**
	 *  function to upload files
	 * @param uploadingFiles Files
	 * @param studentName  String
	 * @return  Boolean
	 * @throws IOException
	 */
	public Boolean uploadFileHelper(MultipartFile[] uploadingFiles, String studentName) throws IOException  
	{
		studentNameList.add(studentName);
		Boolean res = false;
		
		Integer versionNo;
		Integer startIndex = totalFilesUploaded-1;
		Integer endIndex;
		if(currStudentFileVersionMap.containsKey(studentName))
			versionNo = currStudentFileVersionMap.get(studentName)+1;				
		else
			versionNo = 1;
		addFilesToAssignmentsFolder(uploadingFiles, studentName,versionNo);
		currStudentFileVersionMap.put(studentName, versionNo);
		String dirPath = PATH;
		File uploadedFolder = new File(dirPath);
		if(!uploadedFolder.exists())
		{
			uploadedFolder.mkdir();
		}
		int indx = new File(PATH).listFiles().length;
		ArrayList<String> stdFileNames = new ArrayList<>();
		ArrayList<Integer> stdFileIndx = new ArrayList<>();

		for(MultipartFile file : uploadingFiles )
		{
			stdFileNames.add(file.getOriginalFilename());
			stdFileIndx.add(indx);
			String filePath = dirPath + PATHVARIABLE + studentName +"_"+ versionNo;
			File newFile = new File(filePath);
			res=createNewFile(newFile);
			writeToFile(newFile, file, studentName, versionNo);
			indx++;
			totalFilesUploaded+=1;

		}
		endIndex = totalFilesUploaded-1;
		String studNameWithVersion = studentName + VERSION+versionNo;
		String indexRange = startIndex +"-"+ endIndex;
		studentFilesForVersion.put(studNameWithVersion, indexRange);

		return res;
	}



	private void addFilesToAssignmentsFolder(MultipartFile[] uploadingFiles, String studentName, int versionNo) throws IOException {
		
		File assFile = new File(ASSIGNMENTPATH);
		if(!assFile.exists())
		{
			assFile.mkdir();
		}
		String folderPath = ASSIGNMENTPATH + PATHVARIABLE+ studentName+ VERSION +versionNo+"_"+totalFilesInAssignFolder;
		File newFolder = new File(folderPath);
		newFolder.mkdir();
		for(MultipartFile file : uploadingFiles )
		{
			String filePath = folderPath + PATHVARIABLE+file.getOriginalFilename();
			File newFile = new File(filePath);
			createNewFile(newFile);
			writeToFileWithoutAppend(newFile, file);
		}
		totalFilesInAssignFolder+=1;
		
	}



	public List<String> getStudentNameList() {
		return studentNameList;
	}



	/**
	 * @param newFile
	 * @return Creates a new file in the uploadedFiles folder
	 * @throws IOException
	 */
	private Boolean createNewFile(File newFile) throws IOException
	{
		boolean res =false;
		res= newFile.createNewFile();
		logger.debug("Stack trace");
		return res;

	}

	/**
	 * @param newFile
	 * @param file
	 * Writes contents of file on to new file
	 * @throws IOException 
	 */
	private void writeToFile(File newFile, MultipartFile file, String studentName, Integer versionNo) throws IOException
	{
		try(Writer output = new BufferedWriter(new FileWriter(newFile,true)))
		{
			//output.append("\n");
			output.append("#<<"+ studentName + ">>"+ "<< version - " + versionNo + ">>" + "<<"+ file.getOriginalFilename() + ">>");
			output.append("\n");
		}
		try(FileOutputStream opStream = new FileOutputStream(newFile, true))
		{

			opStream.write(file.getBytes());
		}	
		logger.debug("writing to UpladedFiles Folders");
		
	}
	
	/**
	 * @param newFile
	 * @param file
	 * Writes contents of file on to new file
	 * @throws IOException 
	 */
	private void writeToFileWithoutAppend(File newFile, MultipartFile file)
    {
		try(FileOutputStream opStream = new FileOutputStream(newFile, false))
        {
            opStream.write(file.getBytes());
        }
        catch(Exception ex)
        {
           logger.debug(ex.toString());
        }
    }

	/**
	 * function to remove old files
	 * @throws IOException 
	 */
	public Boolean cleanUploadedFilesHelper() throws IOException
	{

		totalFilesUploaded = 0;
		String directoryPath = "UploadedFiles/";
		File dir= new File(directoryPath);
		if(!dir.exists())return true;
		FileUtils.cleanDirectory(dir);
		logger.debug("stack trace");
		return dir.listFiles().length ==0 ? true:false;

	}
	public Boolean cleanAssignmentFolderHelper() throws IOException
	{
		totalFilesInAssignFolder = 0;
		String directoryPath = "Assignment/";
		File dir= new File(directoryPath);
		if(!dir.exists())return true;
		FileUtils.cleanDirectory(dir);
		logger.debug("stack trace clean");
		return dir.listFiles().length ==0 ? true:false;
	}



}
