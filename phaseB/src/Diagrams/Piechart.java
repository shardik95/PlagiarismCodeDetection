package Diagrams;
import Results.Result;

// An object of type PieChart is a pie chart representation of 
// the similarity score of various aspects of the two compared files
public class Piechart extends DiagramChart{

    public Piechart(Result result){
        super(result);
    }

    @Override
    public void drawChart() {
    	// Draws pie chart on the UI
    }
}
