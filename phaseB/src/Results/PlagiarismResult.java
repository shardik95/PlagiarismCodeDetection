package Results;


// An abstract class which extends the Result interface
public abstract class PlagiarismResult implements Result {

    private String studentName1;  // Represents the first student name 
    private String studentName2; // Represents the second student name
    protected int[] indexfile1; // Integer array to hold the matched line numbers of file1
    protected int[] indexfile2; // Integer array to hold the matched line numbers of file2
    protected int avgScore; // Represents the average similarity score
    protected int numVar; // Represents the number of matched variables
    protected int numStatmts; // Represents the number of matched statements
    protected int numFunc; // Represents the number of matched functions
    protected String[] parsefile1; // parsed file1
    protected String[] parsefile2 ; // parsed file2
    protected String file1; // first file for comparison
    protected String file2; // second file for comparison

    PlagiarismResult(String studentName1,String studentName2, String file1,String file2){
        this.studentName1=studentName1;
        this.studentName2=studentName2;
        this.file1=file1;
        this.file2=file2;
    }
    
    // Used to  generate the tokens
    @Override
    public void tokenise() {


    }

    // Returns student name of file1
    public String getStudentName1(){
        return this.studentName1;
    }

    // Returns student name of file2
    public String getStudentName2(){
        return this.studentName2;
    }

    
}
