package algorithmTests;

import edu.northeastern.cs5500.algorithms.Algorithm;
import edu.northeastern.cs5500.algorithms.AlgorithmFactory;
import it.zielke.moji.MossException;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class MossTests {

    AlgorithmFactory aFac = new AlgorithmFactory();

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
            "if(arr1[i]==x)",
            "returni1;",
            "i=i+Math.max(1, Math.abs(arr[i]-x)/k);",
            "}",
            "System.out.println(\"numberis\"+\"notpresent!]\");",
            "return-1;",
            "}",
            "publicstaticvoidmain(String[]args)",
            "{",
            "intarr[]={12,14,15,171,7,6};",
            "intx=8;",
            "intk=12;",
            "intn=arr.length;",
            "System.out.println(\"Element\"+x+\"ispresentatindex\"+search(arr,n,x,k));",
            "}",
            "}"};

    @Test
    public void MossTest() throws IOException, MossException {
            String path = "Assignment/";
            File f=new File(path);
            f.mkdir();
            File bfFolder = new File (path,"s1_version_1_0/");
            bfFolder.mkdir();
            File file1=new File(bfFolder,"s1_version_1_0.py");
            BufferedWriter bw1=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1)));
            for(int i=0;i<javaCode1.length;i++)
                bw1.append(javaCode1[i]);
            bw1.close();
        File bfFolder1 = new File (path,"s2_version_1_1/");
        bfFolder1.mkdir();
            File file2=new File(bfFolder1,"s2_version_1_1.py");
        BufferedWriter bw2=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2)));
        for(int i=0;i<javaCode2.length;i++)
            bw2.append(javaCode2[i]);
            bw2.close();
            Algorithm moss = aFac.makeMoss();
            moss.getAlgorithmImplementation(javaCode1, javaCode2);
            moss.getSimilarityResult();
            moss.getSimilarityScore();
            moss.getsimilarLineNos();
            assertEquals(0.0,moss.getSimilarityScore(),0.5);

    }
}
