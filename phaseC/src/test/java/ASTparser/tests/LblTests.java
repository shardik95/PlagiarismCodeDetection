package ASTparser.tests;

import edu.northeastern.cs5500.astparser.LblTree;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LblTests {
    LblTree lbl=new LblTree("l1",1);
    @Test
    public void LblTree(){

        LblTree t = LblTree.fromString("1:{a{b{d}{e}}{c{f}{g}}}");
        String s="parent: " + t+"\n";
        LblTree child = (LblTree)t.getChildAt(0);
        child.removeFromParent();
        s=s+"child: " + child+"\n";
        LblTree granchild = (LblTree)child.getChildAt(0);
        s=s+"granchild: " + granchild+"\n";
        t.insert(granchild, 0);
        s=s+"Inserting granchild to parent..."+"\n";
        s=s+"parent: " + t+"\n";
        s=s+"child: " + child+"\n";
        s=s+"granchild: " + granchild+"\n";
        assertEquals("parent: 1:{a{b{d}{e}}{c{f}{g}}}\n" +
                "child: {b{d}{e}}\n" +
                "granchild: {d}\n" +
                "Inserting granchild to parent...\n" +
                "parent: 1:{a{d}{c{f}{g}}}\n" +
                "child: {b{e}}\n" +
                "granchild: {d}\n",s);


    }

    @Test
    public void setLabelTest(){
        lbl.setLabel("l2");
    }

    @Test
    public void showNodeTest(){
        assertEquals("l1",lbl.showNode());
    }

    @Test
    public void getTreeIdTest(){
        assertEquals(1,lbl.getTreeID());
    }

    @Test
    public void LatexTest(){
        assertEquals("\\pstree[linewidth=0.2pt,levelsep=20pt,treefit=tight,treesep=4pt,nodesep=2pt]{\\Tr{l1}}{}"
                ,lbl.toLatex());
    }

    @Test
    public void setTreeIdSet(){
        lbl.setTreeID(2);
    }

    @Test
    public void prettyprintTest(){
        lbl.prettyPrint();
    }

    @Test
    public void getMediumTest(){
        assertEquals(0.0,lbl.getMediumFanout(),0.5);
    }

    @Test
    public void getInternalNodeTest1(){
        assertEquals(0,lbl.getInternalNodes(0).length);
    }

    @Test
    public void getInternalNodeTest2(){
        assertEquals(0,lbl.getInternalNodes(1).length);
    }

    @Test
    public void getInternalNodeTest3(){
        assertEquals(0,lbl.getInternalNodes(2).length);
    }

    @Test
    public void getLeafTest(){
        assertEquals(1,lbl.getLeafs().length);
    }

    @Test
    public void toStringtests(){
        assertEquals("1:{l1}",lbl.toString());
    }


    @Test
    public void relabelNodeTest(){
        assertEquals(false,lbl.relabelNode("l1"));
    }

    @Test
    public void getNodeTest(){
        assertEquals(-1,lbl.getNodeID());
    }

    @Test
    public void setNodeTest(){
        lbl.setNodeID(2);
    }

    @Test(expected = ClassCastException.class)
    public void compareToTest(){
        lbl.compareTo(new Object());
    }

    @Test(expected = ClassCastException.class)
    public void equalsToTest(){
        lbl.equals(new Object());

    }

    @Test(expected = ClassCastException.class)
    public void InsertToTest(){
        lbl.insertNode(new Object(),1,1);;
    }

    @Test(expected = ClassCastException.class)
    public void insertPathTest(){
        lbl.insertPath(new Object());
    }

    @Test(expected = ClassCastException.class)
    public void deleteTest(){
        lbl.deletePath(new Object());
    }

    @Test
    public void DeleteNodeTest(){
        assertEquals(false,lbl.deleteNode(new Object()));
    }






}
