package view;



import client.Client;
import controller.user.UserService;
import model.SlackSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


public class SignInPage extends JFrame {
    private JPanel loginPage;
    private JTextField emailS;
    private JButton signInButton;
    private JPasswordField passwordS;
    private JButton button1;
    private JLabel linkToRegister;
    private String textLink="Not a member? Register";

    public SignInPage(Client client, SlackSystem slackSystem, UserService userService){
        super("");
        add(loginPage);
        setSize(600,500);
        setTitle("app.Slack Login Page");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);


        //connexion user TODO
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailEnter,passwordEnter;
                emailEnter = emailS.getText();
                passwordEnter = passwordS.getText();
                try {
                    if(userService.login(emailEnter, passwordEnter)==null){
                        JOptionPane.showMessageDialog(loginPage,"Wrong password or wrong username","LEGO Slack warning",
                                JOptionPane.WARNING_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(loginPage,"You're now connected");
                        dispose();
                        new WelcomePage(client,slackSystem);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        setVisible(true);

        //lien vers la page Sign UP TODO
        linkToRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkToRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                try {
                    new SignUpPage(client,slackSystem,userService);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                linkToRegister.setText("<html><a href=''>" + textLink + "</a></html>");
            }
        });


    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}