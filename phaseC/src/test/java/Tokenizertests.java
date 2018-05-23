import edu.northeastern.cs5500.parser.Tokenizer;
import org.junit.Test;

import java.util.Set;
import static org.junit.Assert.assertEquals;

public class Tokenizertests {

    @Test
    public void testtokenizer(){
        String input="import math\n" +
                "def printme( str ):\n" +
                "   \"This prints a passed string into this function\"\n" +
                "   print str\n" +
                "   return\n"+
                "# Strings can be converted to numbers and vice versa with a functional\n" +
                "# notation.\n" +
                "a = \"459\"\n" +
                "b = \"1891\"\n" +
                "c1 = a + b\n" +
                "c2 = int(a) + int(b)\n" +
                "print(a,b,c1,c2)\n" +
                "print()\n" +
                "\n" +
                "# The input method reads a line as a string.  Then you can process the\n" +
                "# string.\n" +
                "in1 = input(\"Please enter a string: \")\n" +
                "print(\"A string:\", in1)\n" +
                "in2 = input(\"Please enter an integer: \")\n" +
                "in3 = input(\"Please enter another integer: \")\n" +
                "print(\"The sum is:\", int(in2)+int(in3))\n" +
                "print(\"The concatination is:\", in2+in3)";


        String statement[]=input.split("\n");


        Tokenizer t=new Tokenizer();
        String expected="";
        String withoutspace[]=t.normaliseCode(statement);

        /**
         * Get Mapping of old and new line numbers.
         * -1 when line is not present in new line.
         */
        for(int i:t.getLineMapping().keySet()){
            System.out.println(i+"-"+t.getLineMapping().get(i));
        }
        for(int i=0;i<withoutspace.length;i++){
            expected=expected+withoutspace[i]+"\n";
        }
        Set<String> variables=t.findVariables(statement);
        System.out.println(variables);

        Set<String> func=t.findFunction(statement);
        System.out.println(func);

        String test="def printme( str ):\n" +
                "\"This prints a passed string into this function\"\n" +
                "print str\n" +
                "return\n" +
                "# Strings can be converted to numbers and vice versa with a functional\n" +
                "# notation.\n" +
                "a = \"459\"\n" +
                "b = \"1891\"\n" +
                "c1 = a + b\n" +
                "c2 = int(a) + int(b)\n" +
                "print(a,b,c1,c2)\n" +
                "print()\n" +
                "# The input method reads a line as a string.  Then you can process the\n" +
                "# string.\n" +
                "in1 = input(\"Please enter a string: \")\n" +
                "print(\"A string:\", in1)\n" +
                "in2 = input(\"Please enter an integer: \")\n" +
                "in3 = input(\"Please enter another integer: \")\n" +
                "print(\"The sum is:\", int(in2)+int(in3))\n" +
                "print(\"The concatination is:\", in2+in3)\n";
        assertEquals(test,expected);
    }
}
