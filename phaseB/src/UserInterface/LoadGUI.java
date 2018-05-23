package UserInterface;
import Results.Result;

// LoadGUI is an abstract class implementing the GUI interface
public abstract class LoadGUI implements GUI {

    protected String folderpath; // represents the path of the folder
    protected String[] files; // string array consisting of the file content
    protected int actor; // represents actor ID
    protected int threshold; // represents the plagiarism threshold
    protected Result[] result; // Result obtained after comparison

    
    LoadGUI(int actor){
        this.actor=actor;
    }

    // Given : a folder path
 	// Returns : data which is read from files in
 	//           the given folder path
    @Override
    public String[] readFiles(String folderpath) {
        return new String[0];
    }

    // Returns : an actor ID
    @Override
    public int getActor() {
        return this.actor;
    }

    // Sets the threshold value for plagiarism check
    @Override
    public void setThreshold(int threshold) {
            this.threshold=threshold;
    }

    // Returns : the plagiarism check threshold value
    @Override
    public int getThreshold() {
        return this.threshold;
    }
    
    // Returns : the Plagiarism check report
    @Override
    public void getReport() {

    }

    // Used to download the Plagiarism check report 
    @Override
    public void downloadReport() {

    }

    
}
