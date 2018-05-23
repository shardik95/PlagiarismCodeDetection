package ControllerTest;

import edu.northeastern.cs5500.controllers.GUIController;
import it.zielke.moji.MossException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class GUIControllerTest {

    @Test(expected = NullPointerException.class)
    public void test1() throws IOException {
        GUIController gui =new GUIController();
        final Boolean s1 = gui.uploadFile(new MultipartFile[0], "s1");
    }

    public void test2(){
        GUIController gui =new GUIController();
        gui.fetchLCSPlagiarismReport(0,0);
    }

    public void test3() throws IOException, MossException, SQLException {
        GUIController gui =new GUIController();
        gui.compareFiles("LCS");
    }

    public void test4() throws SQLException {
        GUIController gui =new GUIController();
        gui.register("pwd","prof","ruchithams1@gmail.com");
    }

    public void test5() throws SQLException {
        GUIController gui =new GUIController();
        HttpServletResponse r1=null;
        HttpServletRequest r=null;
        ModelMap map=new ModelMap();
        gui.googleSignIn(r, r1,map,"pwd","prof","ruchithams1@gmail.com");
    }
}
