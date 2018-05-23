package serviceTests;


import edu.northeastern.cs5500.controllers.GUIController;
import edu.northeastern.cs5500.services.DbService;
import edu.northeastern.cs5500.services.LoginService;
import edu.northeastern.cs5500.services.ReportService;
import edu.northeastern.cs5500.services.UploadService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ControllerTests {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private LoginService loginService;

    @Mock
    private ReportService reportService;

    @Mock
    private UploadService uploadService;

    @Mock
    private DbService dbService;

    @Mock
    HttpSession session;

    private MockMvc mockMvc;

    @InjectMocks
    private GUIController gui;

    @Before
    public void Setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(gui).build();
        when(loginService.authenticateUser("professor","pass")).thenReturn(Boolean.TRUE);
        when(uploadService.uploadFileHelper(new MultipartFile[0],"s1")).thenReturn(Boolean.TRUE);
        when(reportService.compareFilesHelper("LCS")).thenReturn(new double[0][0]);
    }

    @Test
    public void testPassword() throws Exception {

        HttpServletRequest request =  Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        ResponseEntity<String> h=Mockito.mock(ResponseEntity.class);
        session.setAttribute("userid", "professor");
        mockMvc.perform(post("/checkpassword").param("pwd","pass").param("uname","professor"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFailedLogin() throws Exception {
        HttpServletRequest request =  Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        ResponseEntity<String> h=Mockito.mock(ResponseEntity.class);
        session.setAttribute("userid", null);
        mockMvc.perform(post("/checkpassword").param("pwd", "pass").param("uname", "hardik"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

//    @Test
//    public void testPasswordIncorrect() throws Exception {
//
//        HttpServletRequest request =  Mockito.mock(HttpServletRequest.class);
//        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
//        ResponseEntity<String> h=Mockito.mock(ResponseEntity.class);
//        session.setAttribute("userid", "professor");
//        mockMvc.perform(post("/checkpassword").param("pwd","passed").param("uname","professor"))
//                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
//    }

    @Test
    public void testSession() throws Exception {
        mockMvc.perform(get("/checksession"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    public void testSessionInvalidation() throws Exception {
        mockMvc.perform(post("/invalidate"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testSetThreshold() throws Exception {
        mockMvc.perform(post("/setThreshold").param("threshold", "70"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetThreshold() throws Exception {
        mockMvc.perform(get("/getThreshold"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCleanUploadedFiles() throws Exception {
        mockMvc.perform(post("/cleanUploadedFiles"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUsageCount() throws Exception {
        mockMvc.perform(get("/getUsageCount").param("uname", "professor"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testSetUsageCount() throws Exception {
        mockMvc.perform(post("/setUsageCount").param("uname", "professor"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetRole() throws Exception {
        mockMvc.perform(get("/getRole").param("uname", "professor"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCompareFiles() throws Exception {
        mockMvc.perform(post("/compareFiles").param("algorithmType", "LCS"));
    }

    @Test
    public void testFetchPlagiarismReport() throws Exception {
        mockMvc.perform(post("/fetchPlagiarismReport").param("fileindex1", "1").param("fileindex2", "2"));
    }


    @Test
    public void testInvalidateSession() throws Exception {
        mockMvc.perform(post("/invalidate"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(post("/adduser").param("uname","pwd"));
    }

}

