package edu.northeastern.cs5500.antlr;

import java.io.File;
import java.io.IOException;

import edu.northeastern.cs5500.astparser.*;
import it.zielke.moji.MossException;
import edu.northeastern.cs5500.algorithms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AstResult implements Algorithm {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @return plagiarism score
     */
    @Override
    public double getSimilarityScore() {
        String ast1 = "";
        String ast2 = "";
        int len1 = 0;
        int len2 = 0;
        ParserFacade parserFacade1 = new ParserFacade();
        AstPrinter astPrinter1 = new AstPrinter();

        ParserFacade parserFacade2 = new ParserFacade();
        AstPrinter astPrinter2 = new AstPrinter();
        try {

            ast1 = astPrinter1.getAst(parserFacade1.parse(new File(path1)));

            len1 = astPrinter1.getLength();

            ast2 = astPrinter2.getAst(parserFacade2.parse(new File(path2)));

            len2 = astPrinter2.getLength();


        } catch (Exception e) {
            logger.debug("ast", e);
        }


        LblTree t1 = LblTree.fromString(ast1);
        LblTree t2 = LblTree.fromString(ast2);

        EditDist ed = new EditDist(1, 1, 1, false);


        if (len1 > len2) {
            return 100-(ed.treeDist(t1, t2) * 100 / len1);
        } else if (len2 > 0) {
            return 100-(ed.treeDist(t1, t2) * 100 / len2);
        } else throw new ArithmeticException();
    }
    private String path1;
    private String path2;

    /**
     *
     * @param s1 String array
     * @param s2 Strinf array
     * @return Algorithm object
     */

    @Override
    public Algorithm getAlgorithmImplementation(String[] s1, String[] s2) {
        this.path1=s1[0];
        this.path2=s2[0];
        return null;
    }

    /**
     *
     * @return array of similar lines
     */
    @Override
    public int[] getsimilarLineNos() {
        return new int[0];
    }

    /**
    *
    * @return Returns similarity score for moss, not used here
    */
	@Override
	public double[][] getSimilarityResult() throws IOException, MossException {
		return new double[0][0];
	}


}