package serviceTests;
import org.junit.Test;

import edu.northeastern.cs5500.services.LoginService;

import java.sql.SQLException;

import static org.junit.Assert.*;
public class LoginServiceTests {

	private LoginService logSerObj = new LoginService();
	@Test
	/**
	 * function to test login
	 */
	public void loginAuthecticationTest() throws SQLException {
		String url = "jdbc:mysql://cs5500-spring2018.ccvlhkhtgw2c.us-east-2.rds.amazonaws.com/Plagiarism_Detector";
		String username ="Admin";
		String	psd = "team_210";
		logSerObj.setPASSWORD(psd);
		logSerObj.setUSERNAME(username);
		logSerObj.setURL(url);
		String userName = "professor";
		String password = "pass";
		Boolean authenticationOutputActual = logSerObj.authenticateUser(userName,password);

		Boolean authenticationOutputExpected = true;
		assertEquals(authenticationOutputActual,authenticationOutputExpected);


		String userNameTa = "ta";
		String passwordTa = "tapass";
		Boolean authenticationOutputActualTA = logSerObj.authenticateUser(userNameTa,passwordTa);

		Boolean authenticationOutputExpectedTA = true;
		assertEquals(authenticationOutputActualTA, authenticationOutputExpectedTA);

	}
	@Test
	public void loginAuthecticationDBConnectionNull() throws SQLException {
		String url = null;
		String username ="Admin";
		String	psd = "team_210";
		logSerObj.setPASSWORD(psd);
		logSerObj.setUSERNAME(username);
		logSerObj.setURL(url);
		String userName = "professor";
		String password = "pass";
		Boolean authenticationOutputActual = logSerObj.authenticateUser(userName,password);

		Boolean authenticationOutputExpected = false;
		assertEquals(authenticationOutputActual,authenticationOutputExpected);

	}
	@Test
	public void loginAuthecticationWrongUserPassword() throws SQLException {
		String url = "jdbc:mysql://cs5500-spring2018.ccvlhkhtgw2c.us-east-2.rds.amazonaws.com/Plagiarism_Detector";
		String username ="Admin";
		String	psd = "wrongpass";
		logSerObj.setPASSWORD(psd);
		logSerObj.setUSERNAME(username);
		logSerObj.setURL(url);
		String userName = "professor";
		String password = "pass";
		Boolean authenticationOutputActual = logSerObj.authenticateUser(userName,password);

		Boolean authenticationOutputExpected = false;
		assertEquals(authenticationOutputActual,authenticationOutputExpected);


		
	}
	@Test
	public void loginAuthecticationWrongUserName() throws SQLException {
		String url = "jdbc:mysql://cs5500-spring2018.ccvlhkhtgw2c.us-east-2.rds.amazonaws.com/Plagiarism_Detector";
		String username ="wrongusername";
		String	psd = "team_210";
		logSerObj.setPASSWORD(psd);
		logSerObj.setUSERNAME(username);
		logSerObj.setURL(url);
		String userName = "professor";
		String password = "pass";
		Boolean authenticationOutputActual = logSerObj.authenticateUser(userName,password);

		Boolean authenticationOutputExpected = false;
		assertEquals(authenticationOutputActual,authenticationOutputExpected);


		
	}
	@Test
	public void loginAuthecticationNullPassword() throws SQLException {
		String url = "jdbc:mysql://cs5500-spring2018.ccvlhkhtgw2c.us-east-2.rds.amazonaws.com/Plagiarism_Detector";
		String username ="Admin";
		String	psd = "wrongpass";
		logSerObj.setPASSWORD(psd);
		logSerObj.setUSERNAME(username);
		logSerObj.setURL(url);
		String userName = "professor";
		String password = "pass";
		Boolean authenticationOutputActual = logSerObj.authenticateUser(userName,password);

		Boolean authenticationOutputExpected = false;
		assertEquals(authenticationOutputActual,authenticationOutputExpected);


		
	}
	@Test
	public void loginAuthecticationNullUserName() throws SQLException {
		String url = "jdbc:mysql://cs5500-spring2018.ccvlhkhtgw2c.us-east-2.rds.amazonaws.com/Plagiarism_Detector";
		String username ="Admin";
		String	psd = "wrongpass";
		logSerObj.setPASSWORD(psd);
		logSerObj.setUSERNAME(username);
		logSerObj.setURL(url);
		String userName = "professor";
		String password = "pass";
		Boolean authenticationOutputActual = logSerObj.authenticateUser(userName,password);

		Boolean authenticationOutputExpected = false;
		assertEquals(authenticationOutputActual,authenticationOutputExpected);

	}
	
	@Test
	public void loginAuthecticationNullCase() throws SQLException {
		String url = null;
		String username = null;
		String	psd = null;
		logSerObj.setPASSWORD(psd);
		logSerObj.setUSERNAME(username);
		logSerObj.setURL(url);
		logSerObj.setPasscode(null);
		logSerObj.setuSERNAME(null);
		logSerObj.setpASSWORD(null);
		
		String userName = "professor";
		String password = "pass";
		Boolean authenticationOutputActual = logSerObj.authenticateUser(userName,password);

		Boolean authenticationOutputExpected = false;
		assertEquals(authenticationOutputActual,authenticationOutputExpected);

	}
}
