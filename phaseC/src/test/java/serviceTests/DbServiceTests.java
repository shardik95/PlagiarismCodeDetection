package serviceTests;

import org.junit.Test;

import edu.northeastern.cs5500.services.DbService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils.*;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class DbServiceTests {
    private DbService dbSerObj = new DbService();

    @Test
    public void getThresholdTest() throws SQLException{
        int thresh= dbSerObj.getThresholdHelper();
    }

    @Test
    public void setThresholdTest() throws SQLException{
        int thresh=65;
        dbSerObj.setThresholdHelper(thresh);
        int result= dbSerObj.getThresholdHelper();
        assertEquals(result,thresh);
    }


    @Test
    public void getUsageCountTest() throws SQLException{
        String uname="professor";
        int count= dbSerObj.getUsageHelper(uname);
    }

    @Test
    public void setUsageCountTest() throws SQLException{
        String uname="professor";
        int count= dbSerObj.getUsageHelper(uname);
        int result=dbSerObj.setUsageHelper(uname);
        int expected=dbSerObj.getUsageHelper(uname);
    }

    @Test
    public void getRoleTest() throws SQLException{
        String uname="professor";
        String role= dbSerObj.getRoleHelper(uname);
    }

    @Test
    public void setRoleTest() throws SQLException{
        String uname="professor";
        String role= dbSerObj.getRoleHelper(uname);
        assertEquals(role,uname);
    }






}
