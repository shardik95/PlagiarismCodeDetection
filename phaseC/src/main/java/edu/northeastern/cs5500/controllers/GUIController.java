package edu.northeastern.cs5500.controllers;

import edu.northeastern.cs5500.mail.MailSender;
import edu.northeastern.cs5500.services.*;
import it.zielke.moji.MossException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@RestController
public class GUIController {

    private static final String USERID= "userid";
    private String USERNAME;

    public String getUSERNAME() {
		return USERNAME;
	}


	@Autowired
    @Qualifier("MailSender")
    public MailSender mailSender;

    /**
     *  LoginService reference
     */
    @Autowired
    LoginService loginService;

    /**
     *  UploadService reference
     */
    @Autowired
    UploadService uploadService;

    /**
     *  ReportService reference
     */
    @Autowired
    ReportService reportService;

    /**
     *  ReportService reference
     */
    @Autowired
    DbService dbService;

    /**
     *  RegisterService reference
     */
    @Autowired
    RegisterService registerService;



    /**
     *
     * @param pwd password
     * @param uname username
     * @return ResponseEntity
     */
    @RequestMapping(value = {"/checkpassword"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> login(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestParam("pwd") String pwd, @RequestParam("uname") String uname) {
    	USERNAME = uname;
    	HttpSession session= request.getSession();
        Logger logger = LoggerFactory.getLogger(this.getClass());
        boolean member = false;
        try {
            member = loginService.authenticateUser(uname, pwd);
        } catch (SQLException e) {
            logger.debug("sql",e);
        }
        if (member) {
            session.setAttribute(USERID, uname);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    /**
     * @param request session variable
     * @return ResponseEntity
     */
    @RequestMapping(value="/checksession", method=RequestMethod.GET)
    public ResponseEntity<String> indexView(HttpServletRequest request){
        if ( request.getSession().getAttribute(USERID)==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else
            return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     *
     * @param session session variable
     * @return ResponseEntity
     */
    @RequestMapping(value="/invalidate", method=RequestMethod.POST)
    public ResponseEntity<String> invalidate(HttpSession session) {
        session.setAttribute(USERID, null);
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     *  function to upload files
     * @param uploadingFiles Files
     * @param studentName  String
     * @return  Boolean
     * @throws IOException
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Boolean uploadFile(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles,@RequestParam("studentName") String studentName) throws IOException {
        
        return uploadService.uploadFileHelper(uploadingFiles, studentName);
    }

    /**
     * function to get report
     * @param fileindex1 type integer
     * @param fileindex2 type integer
     * @return int array
     * @throws IOException
     */
    @RequestMapping(value = "/fetchPlagiarismReport", method = RequestMethod.POST)
    public int[] fetchLCSPlagiarismReport(@RequestParam("fileindex1")int fileindex1, @RequestParam("fileindex2")int fileindex2) {
        return reportService.fetchPlagiarismReportHelper( fileindex1, fileindex2);
    }

    /**
     * function to compare files
     * @param algorithmType String
     * @return Comparison result
     * @throws IOException
     * @throws MossException 
     * @throws SQLException 
     */
    @RequestMapping(value = "/compareFiles", method = RequestMethod.POST)
    public double[][] compareFiles(@RequestParam("algorithmType") String algorithmType) throws IOException, MossException, SQLException 
    {
        return reportService.compareFilesHelper(algorithmType);
    }

    /**
     * function to remove old files
     * @throws IOException 
     */
    @RequestMapping(value = "/cleanUploadedFiles", method = RequestMethod.POST)
    public ResponseEntity<Integer> cleanUploadedFiles() throws SQLException, IOException{
         uploadService.cleanUploadedFilesHelper();
         uploadService.cleanAssignmentFolderHelper();
         int result= dbService.getThresholdHelper();
         return new ResponseEntity<>(result, HttpStatus.OK);
    }


    
    /**
     * function to get plagiarism threshold
     */
    @RequestMapping(value = "/getThreshold", method = RequestMethod.GET)
    public ResponseEntity<Integer> getThreshold() throws SQLException{
        int result= dbService.getThresholdHelper();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * function to set plagiarism threshold
     */
    @RequestMapping(value = "/setThreshold", method = RequestMethod.POST)
    public ResponseEntity<String> setThreshold(@RequestParam("threshold")int threshold) throws SQLException{
        int result= dbService.setThresholdHelper(threshold);
        if (result==0){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * function to get app usage count
     */
    @RequestMapping(value = "/getUsageCount", method = RequestMethod.GET)
    public ResponseEntity<Integer> getCount(@RequestParam("uname") String uname) throws SQLException{
        int result= dbService.getUsageHelper(uname);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * function to get app usage count
     */
    @RequestMapping(value = "/setUsageCount", method = RequestMethod.POST)
    public ResponseEntity<Integer> setCount(@RequestParam("uname") String uname) throws SQLException{
        int result= dbService.setUsageHelper(uname);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     *
     * @param pwd password
     * @param uname username
     * @return http status
     * @throws SQLException
     */
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> register(@RequestParam("pwd") String pwd, @RequestParam("uname") String uname, @RequestParam("email") String email) throws SQLException {
        boolean success =registerService.registerUserHelper(uname,pwd, email);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * function to get role for user
     */
    @RequestMapping(value = "/getRole", method = RequestMethod.GET)
    public ResponseEntity<String> getRole(@RequestParam("uname") String uname) throws SQLException{
        String result=  dbService.getRoleHelper(uname);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/googleSignIn", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Boolean> googleSignIn(HttpServletRequest request, HttpServletResponse response, ModelMap map,@RequestParam("pwd") String pwd, @RequestParam("uname") String uname, @RequestParam("email") String email) throws SQLException {
        boolean success=registerService.registerUserHelper(uname,pwd, email);
        HttpSession session= request.getSession();
        Logger logger = LoggerFactory.getLogger(this.getClass());
        boolean member = false;
        try {
            member = loginService.authenticateUser(uname, pwd);
        } catch (SQLException e) {
            logger.debug("sql",e);
        }
        if (member) {
            session.setAttribute(USERID, uname);
            return new ResponseEntity<>(success,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(success,HttpStatus.NOT_FOUND);
        }
    }
    
    
//    /**
//    * E-mail the comparison result to the address provided
//    * @param toaddress 
//    * @return http status
//    * @throws SQLException
//     * @throws MossException 
//     * @throws IOException 
//    */
//   @RequestMapping(value = "/emailReport", method = RequestMethod.POST)
//   @ResponseBody
//   public ResponseEntity<String> register(@RequestParam("toAddress") String toAddress) throws  IOException, MossException {
//       boolean success =reportService.emailReport(toAddress);
//       if (success) {
//           return ResponseEntity.status(HttpStatus.OK).build();
//       }
//       else
//           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//   }


//    /**
//     * function to get email for user
//     */
//    @RequestMapping(value = "/getEmail", method = RequestMethod.GET)
//    public ResponseEntity<String> getEmail(@RequestParam("uname") String uname) throws SQLException{
//        String result=  dbService.getEmailHelper(uname);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

}
