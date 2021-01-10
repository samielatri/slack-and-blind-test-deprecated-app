package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String DATABASENAME= "slackdb";
    private static final String URL = "jdbc:mysql://localhost:3306/"+DATABASENAME+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // MySQL URL followed by the database name
    private static final String USERNAME = "root"; // MySQL username
    private static final String PASSWORD = "sinsin"; // MySQL password

    private DBConnection() {}

    public static Connection createConnection() {

        Connection connection = null ;

        try {
            // load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("MySQL JDBC Driver Registered");

            // get hold of the driver manager attempting to connect to MySQL database
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("The MySQL JDBC Driver is not found.");
            e.printStackTrace();
        } catch (Exception e) { // SQL exception already catched
            throw new RuntimeException(e);
        }

        if (connection != null) {
            System.out.println("Connection made to DB!");
        }
        return connection;

    }

}
