package Database;

import java.sql.Connection;
import java.sql.DriverManager;

/***
 *
 */
public class Database {
    public static Connection createConnection() {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/slack_db"; //MySQL URL and followed by the database name
        String username = "root"; //MySQL username
        String password = "tototata"; //MySQL password
        try
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver"); //loading mysql driver
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            con = DriverManager.getConnection(url, username, password); //attempting to connect to MySQL database
            System.out.println("Connection etablished "+con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }
}
