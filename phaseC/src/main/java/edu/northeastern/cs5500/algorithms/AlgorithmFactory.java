package edu.northeastern.cs5500.algorithms;

import edu.northeastern.cs5500.antlr.AstResult;

public class AlgorithmFactory
{
    /**
     *
     * @return Algorithm object of type LCS
     */
    public Algorithm makeLCS()
    {
        return new LCS();
    }

    /**
     *
     * @return Algorithm object of type lD
     */
    public Algorithm makeLD()
    {
        return new LevenshteinDistance();
    }

    /**
     *
     * @return Algorithm object of type AST
     */
    public Algorithm makeAST(){
        return new AstResult();
    }
    
    /**
    *
    * @return Algorithm object of type Moss
    */
   public Algorithm makeMoss(){
       return new Moss();
   }

}
