package view;

import controller.user.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class HomePage extends JFrame {
    private JPanel Home;
    private JButton signInButton;
    private JButton signUpButton;

    public HomePage(UserService userService){
        add(Home);
        setSize(600,400);
        setTitle("app.Slack Home Page");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(Home);

        //bouton Sign IN
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignInPage(userService);
            }
        });

        //bouton Sign UP
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new SignUpPage(userService);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        setVisible(true);

    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
