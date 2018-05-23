package algorithmTests;
import static org.junit.Assert.*;

import edu.northeastern.cs5500.algorithms.AlgorithmFactory;
import edu.northeastern.cs5500.algorithms.Algorithm;
import edu.northeastern.cs5500.antlr.AstPrinter;
import it.zielke.moji.MossException;
import org.junit.Test;

import java.io.IOException;

public class AstTests {

    @Test
    public void test() {
        AlgorithmFactory aFac = new AlgorithmFactory();
        Algorithm astObj = aFac.makeAST();
        Double expectedScore= 81.38528138528139;
        String[] Arr1 = {"src/main/java/edu/northeastern/cs5500/parser/client.py"};
        String[] Arr2 = {"src/main/java/edu/northeastern/cs5500/parser/parse_2.py"};
        astObj.getAlgorithmImplementation(Arr1, Arr2);
        astObj.getsimilarLineNos();
        try {
            astObj.getSimilarityResult();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MossException e) {
            e.printStackTrace();
        }
        AstPrinter apt=new AstPrinter();
        apt.setIgnoringWrappers(false);
        assertEquals ("similarity test",expectedScore, astObj.getSimilarityScore(), 0.001);

    }
}