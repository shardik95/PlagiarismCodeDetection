package ASTparser.tests;

import edu.northeastern.cs5500.astparser.EditDist;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditDistTests {

    @Test
    public void EditDistTest(){
        EditDist ed=new EditDist(false);
        ed.setDel(0.0);
        assertEquals(0.0,ed.getDel(),0.5);
    }
}
