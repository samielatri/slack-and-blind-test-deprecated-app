package Server;

import java.sql.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conect {
    public static void main(String[] args) {
        String BDD= "sys";
        String url="jdbc:mysql://localhost:3306/" + BDD;
        String user= "root";
        String pwd= "root";
        Connection conn = null;
        try {
            conn= DriverManager.getConnection(url, user, pwd);
            System.out.println("Connexion etablie");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur");
            System.exit(0);
        }
    }
}