package UserInterface;
import Results.Result;


// An object of type GUI is used to
// handle events triggered by user interaction
public interface GUI {

	// Given : a string which represents the folder path
	// Returns : an array of type string, which consists of the
	//			 data which is read from files in
	//           the given folder path
    public String[] readFiles(String folderpath);


    // Returns: the actor ID
    public int getActor();

    // Sets the threshold value for plagiarism check
    public void setThreshold(int threshold) ;

    // Returns: the threshold value set for plagiarism check
    public int getThreshold();

    // Gets the Plagiarism check report  
    public void getReport();

    // Downloads the Plagiarism check report 
    public void downloadReport();

    // Returns: Plagiarism check result for all the uploaded files
    public Result[] plagiarismCheck();

}
