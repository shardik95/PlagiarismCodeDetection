package ASTparser.tests;

import edu.northeastern.cs5500.astparser.LblTree;
import edu.northeastern.cs5500.astparser.TreeDist;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TreeDistTests {
    @Test
    public void TreeDistTest(){
        TreeDist td=new TreeDist(false) {
            @Override
            public double treeDist(LblTree t1, LblTree t2) {
                return 0;
            }
        };
        assertEquals("(not normalized)",td.toString());
    }
}
