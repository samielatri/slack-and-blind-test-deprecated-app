package test;

import controller.user.UserService;
import model.user.User;

import java.sql.SQLException;

public class Test {
    public static void main(String[] arg) throws SQLException {

        String email="radj@sin.nahi", mdp = "@12!AZzzx", mdp1 = "@12!AZzzx";
        //email = readString("Email");
        //mdp = readString("Password");
        UserService userService = new UserService();

        User usr;

        usr = userService.register(email, mdp, mdp1);

    }
}
