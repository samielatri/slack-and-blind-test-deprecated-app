package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInPage extends JFrame{
    private JPanel loginPage;
    private JTextField textField1;
    private JButton signInButton;
    private JPasswordField passwordField1;
    private JButton button1;
    private JTextField textField2;

    public SignInPage(){
        add(loginPage);
        setSize(400,200);
        setTitle("app.Slack Login Page");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailEnter,passwordEnter;
                emailEnter= textField1.getText();
                passwordEnter=passwordField1.getText();
                //on appelle la methode sign in de la classe SQLUser
                //signIn(emailEnter,passwordEnter);
            }
        });
        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomePage();
            }
        });
    }
}
