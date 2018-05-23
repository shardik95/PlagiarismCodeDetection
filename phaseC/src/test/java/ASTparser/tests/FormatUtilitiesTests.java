package ASTparser.tests;

import edu.northeastern.cs5500.astparser.FormatUtilities;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FormatUtilitiesTests {

    @Test
    public void FormatUtilitiesTests() {
        assertEquals(5,FormatUtilities.getRandomString(5).length());
    }


    @Test
    public void matchingBracket1() {
        int x = FormatUtilities.matchingBracket("([<>],)", 1);
        assertEquals(4, x);
    }

    @Test
    public void matchingBracket2() {
        int x = FormatUtilities.matchingBracket("", 1);
        assertEquals(-1, x);
    }

    @Test
    public void escapeLatex() {
        String latex = FormatUtilities.escapeLatex("#$&_");
        assertEquals("\\#\\$\\&\\_", latex);
    }

    @Test
    public void getChildren1() {
        List<String> s1 = FormatUtilities.getChildren("{[)(><]}");
        assertEquals(new ArrayList<String>(), s1);

    }

    @Test
    public void getChildren2() {
        List<String> s1 = FormatUtilities.getChildren("");
        assertEquals(new ArrayList<String>(), s1);
    }

    @Test
    public void getFieldsTest() {
        String x[] = FormatUtilities.getFields("Hello", 'h');
        assertEquals(new String[1].length, x.length);
    }

    @Test
    public void getFieldsTest2() {
        String x[] = FormatUtilities.getFields("Hello", 'h', '"');
        assertEquals(new String[1].length, x.length);
    }

    @Test
    public void getFieldTest() {
        String s = FormatUtilities.getField(1, "hello", 'h');
        assertEquals("ello", s);
    }

    @Test
    public void getrootTest() {
        String s = FormatUtilities.getRoot("{x}");
        assertEquals("x", s);
    }

    @Test
    public void getTreeIdTests() {
        int x = FormatUtilities.getTreeID("Hello");
        assertEquals(-1, x);
    }

    @Test
    public void getTreeIdTests2() {
        int x = FormatUtilities.getTreeID("");
        assertEquals(-1, x);
    }

    @Test
    public void resizeend1(){
        String x=FormatUtilities.resizeEnd("Hello", 2, 'a');
        assertEquals("He",x);
    }

    @Test
    public void resizeend2(){
        String x=FormatUtilities.resizeEnd("Hello", 2);
        assertEquals("He",x);
    }

    @Test
    public void resizeend3(){
        String x=FormatUtilities.resizeEnd("", 3);
        assertEquals("   ",x);
    }

    @Test
    public void resizefront1(){
        String x=FormatUtilities.resizeFront("", 3);
        assertEquals("   ",x);
    }

    @Test
    public void resizefront2(){
        String x=FormatUtilities.resizeFront("Hello", 2, 'a');
        assertEquals("He",x);
    }

    @Test
    public void resizefront3(){
        String x=FormatUtilities.resizeFront("Hello", 2);
        assertEquals("He",x);
    }

    @Test
    public void italianTest(){
        String x=FormatUtilities.spellOutItalian("èéàòìg");
        assertEquals("e'e'a'o'i'g",x);
    }

    @Test
    public void germanTest(){
        String x=FormatUtilities.spellOutGerman("üßÜäÄöÖd");
        assertEquals("uessUEaeAEoeOEd",x);
    }

    @Test
    public void parsetreetest(){
        List l = new ArrayList<>();
        l.add("e");
        String x=FormatUtilities.parseTree("{}Hello}", l);
        assertEquals("",x);
    }

    @Test
    public void substituteBlanks(){
        String x=FormatUtilities.substituteBlanks("Hello pl", "-");
        assertEquals("Hello-pl",x);
    }

    @Test
    public void numbertest(){
        String x=FormatUtilities.spellOutNumber("0123456789");
        assertEquals("zeroonetwothreefourfivesixseveneightnine",x);
    }

    @Test
    public void StripQuoteTest(){
        String x=FormatUtilities.stripQuotes("'hello'", '\'');
        assertEquals("hello",x);
    }

    @Test
    public void separatedTest(){
        String[] stringlist = new String[]{"Hello", "{<>}"};
        String s=FormatUtilities.commaSeparatedList(stringlist);
        assertEquals("Hello,{<>}",s);
    }

    @Test
    public void separatedTest1(){
        String[] stringlist = new String[]{"Hello", "{<>}"};
        String x=FormatUtilities.commaSeparatedList(stringlist, 's');
        assertEquals("sHellos,s{<>}s",x);
    }


}
