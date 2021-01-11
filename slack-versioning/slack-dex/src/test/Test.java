package test;

import controller.user.UserService;
import model.SlackSystem;
import model.user.User;
import view.HomePage;

import javax.swing.*;
import java.sql.SQLException;

public class Test {
    public static void main(String[] arg) throws SQLException {
        SlackSystem slackSystem = new SlackSystem();
        /*String email="radj@sin.nahi", mdp = "@12!AZzzx", mdp1 = "@12!AZzzx";
        email = readString("Email");
        mdp = readString("Password");*/
        UserService userService = new UserService(slackSystem);

        /*User usr;
        usr = userService.register(email, mdp, mdp1);*/
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomePage(slackSystem,userService);

            }
        });


    }
}
