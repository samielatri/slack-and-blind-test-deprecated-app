package test;

import model.user.User;

import java.sql.SQLException;

import static controller.user.UserServiceDAO.register;
import static tool.Keyboard.readString;

public class Test {
    public static void main(String[] arg) throws SQLException {

        String email, mdp;
        email = readString("Email");
        mdp = readString("Password");
        User usr;

        usr = register(email,mdp,mdp);
        assert(usr!=null);
    }
}
