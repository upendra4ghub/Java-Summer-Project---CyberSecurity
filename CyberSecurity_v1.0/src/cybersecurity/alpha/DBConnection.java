/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cybersecurity.alpha;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This Class Contain only one static method and it is used for obtaining the connection with database
 */
public class DBConnection {
/**
     *This method will make the connectivity with database and returns the reference of Connection type
     *@return Connection
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            String serverIP=new ReadFromPropertyFile().getServerIP();
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://"+serverIP+":3306/cybersecurity","root","root");
        } catch (Exception e) {
            System.out.println("Exception in getConnection og DBConnection : "+e);
        }
        return con;
    }
}
