package view;

import client.Client;
import controller.user.UserService;
import model.SlackSystem;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class SignUpPage extends JFrame {
    private JPanel SignUp;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JButton signUpButton;
    private JButton backButton;
    private JPasswordField passConfirm;
    private JLabel confirmPwd;



    public SignUpPage(Client client, SlackSystem slackSystem, UserService userService) throws SQLException {
        add(SignUp);
        setSize(600,600);
        setTitle("app.Slack Login Page");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        

        //Inscription de l'user TODO
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailEnter, passwordEnter, passwordEnter2;
                emailEnter = textField1.getText();
                passwordEnter = passwordField1.getText();
                passwordEnter2 = passConfirm.getText();
                try {
                    if (userService.register(emailEnter,passwordEnter,passwordEnter2)==null) {
                        JOptionPane.showMessageDialog(SignUp,"Password doesn't match !","LEGO Slack warning",
                                JOptionPane.WARNING_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(SignUp,"User successfully registered !");
                        dispose();
                        new WelcomePage(client,slackSystem);
                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        setVisible(true);
    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
