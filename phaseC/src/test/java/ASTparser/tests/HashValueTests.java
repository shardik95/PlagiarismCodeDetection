package ASTparser.tests;

import edu.northeastern.cs5500.astparser.HashValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashValueTests {

    @Test(expected = ClassCastException.class)
    public void HashValueTest(){
        HashValue hv=new HashValue(2,4);
        HashValue hv1=new HashValue("");
        HashValue.maxValue(5);
        hv.toString();
        Object o=new Object();
        hv.compareTo(o);

    }

    String x="Hello";

    @Test
    public void equalsTest(){
        assertEquals(false,x.equals(new Object()));
    }
}
