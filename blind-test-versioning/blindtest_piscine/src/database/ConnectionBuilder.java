package database;


import java.sql.Connection;
import java.sql.DriverManager;

/***
 *
 */
public class ConnectionBuilder {

    public static Connection createConnection() {
        String url = "jdbc:mysql://localhost:3306/blindtest"; //MySQL URL and followed by the database name
        String username = "root"; //MySQL username
        String password = "tototata"; //MySQL password
        try {
            Class.forName("com.mysql.jdbc.Driver"); //loading mysql driver
            return DriverManager.getConnection(url, username, password); //attempting to connect to MySQL database
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionBuilder() {}

}