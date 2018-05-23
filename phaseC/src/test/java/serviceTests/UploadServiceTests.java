package serviceTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import edu.northeastern.cs5500.services.UploadService;

public class UploadServiceTests {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private UploadService  uploadSerObj = new UploadService();
	private static final String studentName1 = "ritu";
	private static final String studentName2 = "ayush";
	@Test
	public void test1()
	{
		String dirPath = "UploadedFiles/";
		File upFolder = new File(dirPath);
		if(upFolder.exists())
		{
			File[] allFiles = upFolder.listFiles();
			for(File f: allFiles)
			{
				f.delete();
			}
			upFolder.delete();
		}
		
		
	}

	@Test
	public void test2() throws IOException
	{

		String dirPath = "/UploadedFiles";
		File upFolder = new File(dirPath);
		if(upFolder.exists())
		{
			uploadSerObj.cleanUploadedFilesHelper();
			upFolder.delete();
		}

		// fetching files from the TestFolder1
		String directoryPath = "TestFolder1/";
		File testFolder= new File(directoryPath);

		File[] testfolderFiles = testFolder.listFiles();
		Arrays.sort(testfolderFiles, (f1, f2) -> Long.valueOf(f1.lastModified()).compareTo(f2.lastModified()));

		// creating multipartite files
		MultipartFile[] mulPartFile = new MultipartFile[2];
		int i=0;
		for(File f: testfolderFiles)
		{
			String filePath = f.getPath();
			Path path = Paths.get(filePath);
			String name = f.getName();
			String originalFileName = f.getName();
			String contentType = "text/x-python-script";
			byte[] content = null;
			try {
				content = Files.readAllBytes(path);
			}
			catch (final IOException e) 
			{
				logger.debug(e.toString());
			}
			MultipartFile result = new MockMultipartFile(name,
					originalFileName, contentType, content);
			mulPartFile[i++] = result;
		}


		
			uploadSerObj.cleanUploadedFilesHelper();
			uploadSerObj.uploadFileHelper(mulPartFile, studentName1);

		


		// fetching files from the TestFolder1
		String directoryPath1 = "TestFolder2/";
		File testFolder1= new File(directoryPath1);

		File[] testfolderFiles1 = testFolder1.listFiles();
		Arrays.sort(testfolderFiles1, (f1, f2) -> Long.valueOf(f1.lastModified()).compareTo(f2.lastModified()));

		// creating multipartite files
		MultipartFile[] mulPartFile1 = new MultipartFile[2];
		int i1=0;

		for(File f1: testfolderFiles1)
		{

			String filePath = f1.getPath();
			Path path = Paths.get(filePath);
			String name = f1.getName();
			String originalFileName = f1.getName();
			String contentType = "text/x-python-script";
			byte[] content = null;
			try {
				content = Files.readAllBytes(path);
			}
			catch (final IOException e) 
			{
				logger.debug(e.toString());
			}
			MultipartFile result1 = new MockMultipartFile(name,
					originalFileName, contentType, content);


			mulPartFile1[i1++] = result1;
		}


		
			uploadSerObj.uploadFileHelper(mulPartFile1, studentName2);


		


	}


	@Test
	public void test3() throws IOException
	{
		String directoryPath = "UploadedFiles/";
		File uploadedFile= new File(directoryPath);
		if(uploadedFile.exists())
		{
			Boolean expectedOp = uploadSerObj.cleanUploadedFilesHelper();
			Boolean actualOp = true;
			assertEquals(expectedOp, actualOp);
		}
		uploadSerObj.cleanUploadedFilesHelper();
		Boolean res3 =uploadSerObj.cleanUploadedFilesHelper();
		System.out.println("result2"+ res3);
	}

	@Test
	public void test4() throws IOException
	{
		String directoryPath = "UploadedFiles/";
		File uploadedFile= new File(directoryPath);
		if(uploadedFile.exists())
		{
			uploadSerObj.cleanUploadedFilesHelper();
			Boolean res = uploadedFile.delete();
			System.out.println(res);
			if(res)
			{
				Boolean expectedOp = uploadSerObj.cleanUploadedFilesHelper();
				Boolean actualOp = true;
				assertEquals(expectedOp, actualOp);
			}
		}
		Boolean res2 =uploadSerObj.cleanUploadedFilesHelper();
		System.out.println("result2"+ res2);

	}
	
	
	
	@Test
	public void test5()
	{
		String dirPath = "UploadedFiles/";
		File upFolder = new File(dirPath);
		if(upFolder.exists())
		{
			File[] allFiles = upFolder.listFiles();
			for(File f: allFiles)
			{
				f.delete();
			}
			upFolder.delete();
		}
	}
	
	@Test
	public void test6() throws IOException
	{
		uploadSerObj.cleanAssignmentFolderHelper();
		String dirPath = "Assignment/";
		File assignFolder = new File(dirPath);
		if(assignFolder.exists())
		{
			File[] allFiles = assignFolder.listFiles();
			for(File f: allFiles)
			{
				f.delete();
			}
			assignFolder.delete();
		}
	}
	



}
