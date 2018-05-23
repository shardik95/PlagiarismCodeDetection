package ASTparser.tests;

import edu.northeastern.cs5500.astparser.*;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class EditBasedTests {

    @Test
    public void AStTest1(){
        EditBasedDist eb1=new EditBasedDist(false) {
            @Override
            public double nonNormalizedTreeDist(LblTree t1, LblTree t2) {
                return 0;
            }
        };
        eb1.setDel(0.0);
        eb1.setIns(0.0);
        eb1.setUpdate(0.0);
        assertEquals(0.0,eb1.getDel(),0.5);
        assertEquals(0.0,eb1.getIns(),0.5);
        assertEquals(0.0,eb1.getUpdate(),0.5);
    }










}
