package ASTparser.tests;

import edu.northeastern.cs5500.astparser.FixedLengthHash;
import edu.northeastern.cs5500.astparser.HashValue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FixedLengthTests {
    @Test
    public void FixedLengthHashTests(){
        FixedLengthHash flh=new FixedLengthHash(20) {
            @Override
            public HashValue getHashValue(String s) {
                return new HashValue(s);
            }

            @Override
            public HashValue getNullNode() {
                return new HashValue(null);
            }
        };

        assertEquals(new HashValue("s1"),flh.getHashValue("s1"));

    }

    @Test(expected = NullPointerException.class)
    public void FixedLengthHashTests1(){
        FixedLengthHash flh=new FixedLengthHash(20) {
            @Override
            public HashValue getHashValue(String s) {
                return new HashValue(s);
            }

            @Override
            public HashValue getNullNode() {
                return new HashValue(null);
            }
        };

        assertEquals("s1",flh.getNullNode());

    }

    @Test(expected = ClassCastException.class)
    public void FixedLengthHashTests2(){
        FixedLengthHash flh=new FixedLengthHash(20) {
            @Override
            public HashValue getHashValue(String s) {
                return new HashValue(s);
            }

            @Override
            public HashValue getNullNode() {
                return new HashValue(null);
            }
        };
        List<String> l1=new ArrayList<>();
        l1.add("s1");
        l1.add("s2");
        flh.setLength(5);
        assertEquals("",flh.concatLst(l1));

    }

    @Test(expected = NullPointerException.class)
    public void FixedLengthHashTests3(){
        FixedLengthHash flh=new FixedLengthHash(20) {
            @Override
            public HashValue getHashValue(String s) {
                return new HashValue(s);
            }

            @Override
            public HashValue getNullNode() {
                return new HashValue(null);
            }
        };

        assertEquals(10,flh.getEmptyRegister(5));

    }

    @Test
    public void FixedLengthHashTests4(){
        FixedLengthHash flh=new FixedLengthHash(20) {
            @Override
            public HashValue getHashValue(String s) {
                return new HashValue(s);
            }

            @Override
            public HashValue getNullNode() {
                return new HashValue(null);
            }
        };

        assertEquals(20,flh.getLength());

    }

    @Test
    public void FixedLengthHash(){
        FixedLengthHash flh=new FixedLengthHash(20) {
            @Override
            public HashValue getHashValue(String s) {
                return new HashValue(s);
            }

            @Override
            public HashValue getNullNode() {
                return new HashValue(null);
            }
        };
        HashValue h=flh.h("s1");
        assertEquals("s1",h.toString());
    }

    @Test(expected = NullPointerException.class)
    public void FixedLengthHashNull(){
        FixedLengthHash flh=new FixedLengthHash(20) {
            @Override
            public HashValue getHashValue(String s) {
                return new HashValue(s);
            }

            @Override
            public HashValue getNullNode() {
                return new HashValue(null);
            }
        };
        HashValue h=flh.h(null);
        assertEquals(null,h.toString());
    }

    @Test
    public void FixedLengthHashNull2(){
        FixedLengthHash flh=new FixedLengthHash(20) {
            @Override
            public HashValue getHashValue(String s) {
                return new HashValue(s);
            }

            @Override
            public HashValue getNullNode() {
                return new HashValue(null);
            }
        };
        HashValue h=flh.h("s1",null);
        assertEquals("s1",h.toString());
    }

    @Test
    public void FixedLengthHash2(){
        FixedLengthHash flh=new FixedLengthHash(20) {
            @Override
            public HashValue getHashValue(String s) {
                return new HashValue(s);
            }

            @Override
            public HashValue getNullNode() {
                return new HashValue(null);
            }
        };
        HashValue h=flh.h("s1","s");
        assertEquals("s1s",h.toString());
    }
}
