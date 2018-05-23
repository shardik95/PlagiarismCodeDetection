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
public class RegisterService {

    @Autowired
    @Qualifier("MailSender")
    public MailSender mailSender;

    @Value("${spring.datasource.url}")
    private String uRL;
    @Value("${spring.datasource.username}")
    private String uSERNAME;
    @Value("${spring.datasource.password}")
    private String pASSWORD;

    String passcode = null;
    PreparedStatement statement=null;
    Connection connection=null;
    ResultSet results=null;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private  static final String TRACE ="Just a stack TRACE";


    /**
     * @return integer for db update success/failure
     * @throws SQLException
     */
    public boolean unameExists() throws SQLException {
        try {
            results = statement.executeQuery();
            if (results.next()) {
                return true;
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
        return false;
    }

    private boolean registerUserResult() throws SQLException {
        int result=0;
        try {
            result = statement.executeUpdate();
        }catch (SQLException e) {
            logger.debug(TRACE,e);
            return false;
        }

        return true;
    }

    /**
     * @return integer for db update success/failure
     * @throws SQLException
     */
    private boolean registerUserQuery(String uname, String pwd, String email) throws SQLException {
        try {
            connection = DriverManager.getConnection(uRL, uSERNAME, pASSWORD);
            String sql = "SELECT password FROM Login WHERE username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, uname);
            boolean exists = unameExists();
            if (!exists) {
                sql = "Insert into Login (username, password, email) VALUES (?,?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, uname);
                statement.setString(2, pwd);
                statement.setString(3, email);
                return registerUserResult();
            }
            } catch(SQLException e){
                logger.debug(TRACE, e);
            }
        finally{
                if (connection != null) {
                    connection.close();
                }
            }
        return false;
    }

    /**
     * @return integer for db update success/failure
     * @throws SQLException
     */
    public Boolean registerUserHelper(String uname, String pwd, String email) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return registerUserQuery(uname, pwd, email);

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
}
