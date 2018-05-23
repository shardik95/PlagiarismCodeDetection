package algorithmTests;
import static org.junit.Assert.*;

import edu.northeastern.cs5500.algorithms.Algorithm;
import edu.northeastern.cs5500.algorithms.AlgorithmFactory;
import it.zielke.moji.MossException;
import org.junit.Test;

import java.io.IOException;

/**
 * Test suite to test Levenshtein's algorithm working
 *
 */
public class LDTests {

    AlgorithmFactory aFac = new AlgorithmFactory();
    Algorithm ldObj = aFac.makeLD();

    String[] javaCode1 = {"importjava.io.*;",
            "classGFG{",
            "staticintsearch(intarr[],intn,intx,intk)",
            "{",
            "inti=0;",
            "while(i<n){",
            "if(arr[i]==x)",
            "returni;",
            "i=i+Math.max(1, Math.abs(arr[i]-x)/k);",
            "}",
            "System.out.println(\"numberis\"+\"notpresent!]\");",
            "return-1;",
            "}",
            "publicstaticvoidmain(String[]args)",
            "{",
            "intarr[]={2,4,5,7,7,6};",
            "intx=6;",
            "intk=2;",
            "intn=arr.length;",
            "System.out.println(\"Element\"+x+\"ispresentatindex\"+search(arr,n,x,k));",
            "}",
            "}"};

    String[] javaCode2 = {"importjava.io.*;",
            "classGFG{",
            "staticintsearch(intarr[],intn,intx,intk)",
            "{",
            "inti=0;",
            "while(i<n){",
            "if(arr[i]==x)",
            "returni;",
            "i=i+Math.max(1, Math.abs(arr[i]-x)/k);",
            "}",
            "System.out.println(\"numberis\"+\"notpresent!]\");",
            "return-1;",
            "}",
            "publicstaticvoidmain(String[]args)",
            "{",
            "intarr[]={2,4,5,7,7,6};",
            "intx=6;",
            "intk=2;",
            "intn=arr.length;",
            "System.out.println(\"Element\"+x+\"ispresentatindex\"+search(arr,n,x,k));",
            "}",
            "}"};

    /**
     * Tests when both arrays are empty
     */
    @Test
    public void testBothEmpty() {
        String[] emptyArr1 = {};
        String[] emptyArr2 = {};

        Algorithm bothArrEmptyActualObj = ldObj.getAlgorithmImplementation(emptyArr1, emptyArr2);

        double bothArrEmptyActual = bothArrEmptyActualObj.getSimilarityScore();
        double bothArrEmptyExpected = 0;
        assertEquals("Testing LD when both the input string arrays"
                + "are empty", bothArrEmptyActual, bothArrEmptyExpected, 1);
    }

    /**
     * Tests when one array is empty
     */
    @Test
    public void testOneEmpty() throws IOException, MossException {
        String[] emptyArr1 = {};
        String[] emptyArr2 = {"hey"};

        Algorithm oneArrEmptyActualObj = ldObj.getAlgorithmImplementation(emptyArr1, emptyArr2);

        double oneArrEmptyActual = oneArrEmptyActualObj.getSimilarityScore();
        double oneArrEmptyExpected = 1;
        ldObj.getSimilarityResult();
        assertEquals("Testing LD when one of the input string arrays"
                + "is empty", oneArrEmptyActual, oneArrEmptyExpected, 1);
    }

    /**
     * Tests when both arrays are null
     */
    @Test
    public void testBothNull() {
        String[] nullArr1 = null;
        String[] nullArr2 = null;

        Algorithm bothArrNotInitActualObj = ldObj.getAlgorithmImplementation(nullArr1, nullArr2);

        double bothArrNotInitActual = (bothArrNotInitActualObj == null) ? -1 : bothArrNotInitActualObj.getSimilarityScore();
        double bothArrNotInitExpected = -1;
        assertEquals("Testing LD when both the input string arrays"
                + "are null", bothArrNotInitActual, bothArrNotInitExpected, 1);
    }

    /**
     * Tests when one array is null
     */
    @Test
    public void testOneNull() {
        String[] nullArr3 = null;
        String[] nonEmpStrArr = {"importjava.doc;", "publicstaticvoidmain(String[]argrs)", "{", "}"};

        Algorithm oneArrNotInitActualObj = ldObj.getAlgorithmImplementation(nullArr3, nonEmpStrArr);

        double oneArrNotInitActual = (oneArrNotInitActualObj == null) ? -1 : oneArrNotInitActualObj.getSimilarityScore();
        double oneArrNotInitExpected = -1;
        assertEquals("Testing LD when one of the input string arrays"
                + "is null", oneArrNotInitActual, oneArrNotInitExpected, -1);
    }

    /**
     * Tests when the two files are exactly the same
     */
    @Test
    public void testBothSame() {
        Algorithm arraysEqualActualObj = ldObj.getAlgorithmImplementation(javaCode1, javaCode1);

        double arraysEqualActual = (arraysEqualActualObj == null) ? -1 : arraysEqualActualObj.getSimilarityScore();
        double arraysEqualExpected = 100;
        assertEquals("Testing LD when both input string arrays"
                + "are the same", arraysEqualActual, arraysEqualExpected, -1);
    }

    /**
     * Testing Levenshtein Distance algorithm for space removal functionality
     */
    @Test
    public void testSpaceRemoval() {
        Algorithm lcsObj = aFac.makeLD();

        Algorithm simiarityPer1Obj = lcsObj.getAlgorithmImplementation(javaCode1, javaCode2);
        double simiarityPer1 = simiarityPer1Obj.getSimilarityScore();
        double expSimPer1 = 100;
        assertEquals("", simiarityPer1, expSimPer1, 1);

        String[] codeNoSpace = {"importjava.io.*;",
                "classGFG{",
                "staticintsearch(intarr[],intn,intx,intk)",
                "{",
                "inti=0;",
                "while(i<n){",
                "if(arr[i]==x)",
                "returni;",
                "i=i+Math.max(1, Math.abs(arr[i]-x)/k);",
                "}",
                "System.out.println(\"numberis\"+\"notpresent!]\");",
                "return-1;",
                "}",
                "publicstaticvoidmain(String[]args)",
                "{",
                "intarr[]={2,4,5,7,7,6};",
                "intx=6;",
                "intk=2;",
                "intn=arr.length;",
                "System.out.println(\"Element\"+x+\"ispresentatindex\"+search(arr,n,x,k));",
                "}",
                "}"};
        String[] codeWithSpace = {" importjava.io . *;",
                "classGFG { ",
                "static int   search( intarr[  ],intn ,intx ,intk )",
                "{ ",
                "int i=0  ;",
                "while ( i<n ) {",
                "if ( arr[i] ==  x )",
                "return  i;",
                "i = i +Math.  max(1  ,   Math.abs(arr[i]-x)/k);",
                "}",
                "System.out.println( \"numberis\" + \" notpresent!   ] \");",
                " return-1; ",
                " } ",
                " public  static  void  main(   String[  ] args ) ",
                " { ",
                " int  arr [  ]={ 2, 4 , 5 , 7 , 7 , 6 };",
                "intx = 6 ;",
                "intk = 2;",
                "intn = arr.length;",
                "System . out . println ( \" Element\"+ x +\"ispresentatinde x\"+search(arr,n,x,k));",
                "}",
                "}"};

        Algorithm output1Obj = ldObj.getAlgorithmImplementation(codeNoSpace, codeWithSpace);
        double output1 = output1Obj.getSimilarityScore();
        double output2 = 100;
        assertEquals("Checking for removal of spacce functionality", output1, output2, 1);
    }

    /**
     * Tests that the algorithm works against reordered code
     */
    @Test
    public void testChangedLineOrder() {
        String[] inOrderCode = {"importjava.io.*;",
                "classGFG{",
                "staticintsearch(intarr[],intn,intx,intk)",
                "{",
                "inti=0;",
                "while(i<n){",
                "if(arr[i]==x)",
                "returni;",
                "i=i+Math.max(1, Math.abs(arr[i]-x)/k);",
                "}",
                "System.out.println(\"numberis\"+\"notpresent!]\");",
                "return-1;",
                "}",
                "publicstaticvoidmain(String[]args)",
                "{",
                "intarr[]={2,4,5,7,7,6};",
                "intx=6;",
                "intk=2;",
                "intn=arr.length;",
                "System.out.println(\"Element\"+x+\"ispresentatindex\"+search(arr,n,x,k));",
                "}",
                "}"};
        String[] rearrangedCode = {" importjava.io . *;",
                "classGFG { ",
                "{ ",
                "int i=0  ;",
                "if ( arr[i] ==  x )",
                "while ( i<n ) {",
                "static int   search( intarr[  ],intn ,intx ,intk )",
                "return  i;",
                "}",
                "System.out.println( \"numberis\" + \" notpresent!   ] \");",
                "i = i +Math.  max(1  ,   Math.abs(arr[i]-x)/k);",
                " return-1; ",
                " public  static  void  main(   String[  ] args ) ",
                " } ",
                " int  arr [  ]={ 2, 4 , 5 , 7 , 7 , 6 };",
                " { ",
                "intx = 6 ;",
                "intk = 2;",
                "System . out . println ( \" Element\"+ x +\"ispresentatinde x\"+search(arr,n,x,k));",
                "intn = arr.length;",
                "}",
                "}"};

        Algorithm rearrActualObj = ldObj.getAlgorithmImplementation(inOrderCode, rearrangedCode);
        double rearrActual = rearrActualObj.getSimilarityScore();
        double rearrExpected = 100;
        assertEquals("Checking for removal of space functionality", rearrActual, rearrExpected, 1);
    }

    /**
     * Tests that the algorithm works against changed files
     */
    @Test
    public void testChangedFile() {
        String[] orgStr = {"   importjava. doc;   ", "intk", "intj", "public static  void  main(String [] argrs )    ", " { ", " }"};
        String[] changedStr = {"importjava.doc;", "publicstaticvoidmain(String[]argrs)", "{", "}"};

        Algorithm orgCdActualObj = ldObj.getAlgorithmImplementation(orgStr, changedStr);

        double orgCdActual = orgCdActualObj.getSimilarityScore();
        double orgCdExpected = 66;
        assertEquals("Checking for removal of space functionality", orgCdActual, orgCdExpected, 1);
    }
}