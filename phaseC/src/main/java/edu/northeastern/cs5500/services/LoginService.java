package edu.northeastern.cs5500.services;

import edu.northeastern.cs5500.mail.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;


import java.sql.*;

@Service
public class LoginService {



	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}


	@Autowired
	@Qualifier("MailSender")
	public MailSender mailSender;

	@Value("${spring.datasource.url}")
	private String uRL;
	@Value("${spring.datasource.username}")
	private String uSERNAME;
	public void setuSERNAME(String uSERNAME) {
		this.uSERNAME = uSERNAME;
	}

	public void setpASSWORD(String pASSWORD) {
		this.pASSWORD = pASSWORD;
	}


	@Value("${spring.datasource.password}")
	private String pASSWORD;

	String passcode = null;
	PreparedStatement statement=null;
	Connection connection=null;
	ResultSet results=null;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private  static final String TRACE ="Just a stack TRACE";
	public boolean result() throws SQLException {
		try {
			results = statement.executeQuery();
			while (results.next()) {
				passcode = results.getString("password");
			}
		}catch (SQLException e) {
			logger.debug(TRACE,e);
			return false;
		}
		finally{
			if (results!=null) {
				results.close();
			}
		}
		return true;
	}

	public boolean connect(String uname) throws SQLException {
		try {
			connection = DriverManager.getConnection(uRL, uSERNAME, pASSWORD);
			String sql = "SELECT password FROM Login WHERE username = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, uname);
			result();
		} catch (SQLException e) {
			logger.debug(TRACE,e);
			return false;
		}
		finally {
			if (connection!=null) {
				connection.close();
			}
		}
		return true;
	}
	/**
	 *
	 * @param uname Username
	 * @param pwd Password
	 * @return Boolean
	 */

	public boolean authenticateUser(String uname, String pwd) throws SQLException {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			if(connect(uname)){
				if(passcode!=null) {
					if (passcode.equals(pwd)) {
						return true;
					}
				}else {
					return false;
				}
			}
		} catch (ClassNotFoundException  e) {
			logger.debug(TRACE,e);
		}  finally {
			try {
				if(statement!=null) {
					statement.close();
				}
			} catch (SQLException e) {
				logger.debug(TRACE,e);
			}
		}
		return false;
	}


    /**
     *
     * @param uRL type String
     */
	public void setURL(String uRL) {
		this.uRL = uRL;
	}

    /**
     *
     * @param uSERNAME type String
     */
	public void setUSERNAME(String uSERNAME) {
		this.uSERNAME = uSERNAME;
	}


    /**
     *
     * @param pASSWORD type String
     */
	public void setPASSWORD(String pASSWORD) {
		this.pASSWORD = pASSWORD;
	}
}
