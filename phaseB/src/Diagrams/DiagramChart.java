package Diagrams;
import Results.*;

// DiagramChart  is an abstract class which inherits Chart interface
public abstract class DiagramChart implements Chart {

    protected int numFunc; // Represents the count of similar function names
    protected int numVar;  // Represents the count of similar variable names
    protected int numStatmts; // Represents the count of similar statements

    DiagramChart(Result result){
        numFunc=result.getNumFunc();
        numVar=result.getNumVar();
        numStatmts=result.getNumStatmts();
    }

    
}
