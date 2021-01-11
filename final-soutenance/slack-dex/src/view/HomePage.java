package view;

import client.Client;
import controller.user.UserService;
import model.SlackSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class HomePage extends JFrame {
    private JPanel Home;
    private JButton signInButton;
    private JButton signUpButton;

    public HomePage(Client client, SlackSystem slackSystem, UserService userService){
        add(Home);
        setSize(600,400);
        setTitle("app.Slack Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(Home);
        setLocationRelativeTo(null);

        //bouton Sign IN
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignInPage(client,slackSystem,userService);
            }
        });

        //bouton Sign UP
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new SignUpPage(client,slackSystem,userService);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        setVisible(true);

    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
