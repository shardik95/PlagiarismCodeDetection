package UserInterface;
import Results.*;
import Algorithms.AlgorithmFactory;
import Diagrams.*;

// PlagiarismCheck class represents an object which accepts file for comparison, Compares them and 
// generates the Plagiarism check results.
// This will be displayed on UI in different formats like
// PieChart, textEditor and a simple text representing similarity score
public class PlagiarismCheck extends LoadGUI {

    PlagiarismCheck(int actor){
        super(actor);
    }

    // Returns : the comparison result
    @Override
    public Result[] plagiarismCheck() {
        String file1=files[0];
        String file2=files[1];
        String name1="";
        String name2="";
        Result result=new PlagiarismResultCheck(name1,name2,file1,file2);
        result.tokenise();
        result.compare(new AlgorithmFactory());
        result.getAvgScore();
        this.result=new Result[5];
        return new Result[10];
    }

    // Used to draw a pie-chart to represent comparison result
    public Chart drawPieChart(Result result){
            Chart c=new Piechart(result);
            c.drawChart();
            return c;
    }

    // Given : a result file which holds the comparison result
    // Returns : an integer array which holds the matched line numbers of file1
    public int[] displayFile1(Result result){
            return result.getindexfile1();
    }

    // Given : a result which holds the comparison result
    // Returns : an integer array which holds the matched line numbers of file2
    public int[] displayFile2(Result result){
            return result.getindexfile2();
    }

    // Given : a result which holds the comparison result
    // Returns : an integer value which represents the average similarity score
    public int displaySimilarity(Result result){
            return result.getAvgScore();
    }


}
