package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPage extends JFrame {
    private JPanel SignUp;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JButton signUpButton;
    private JButton backButton;
    private JPasswordField passConfirm;
    private JLabel confirmPwd;

    public SignUpPage(){
        add(SignUp);
        setSize(600,400);
        setTitle("app.Slack Login Page");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailEnter,passwordEnter,passwordEnter2;
                emailEnter= textField1.getText();
                passwordEnter=passwordField1.getText();
                passwordEnter2=passConfirm.getText();
                if (!comparePassword(passwordEnter,passwordEnter2)){
                    JOptionPane.showMessageDialog(SignUp,"Password doesn't match !","LEGO Slack warning",
                            JOptionPane.WARNING_MESSAGE);
                }
                //on appelle la methode insert de la classe SQLUser
                //insert(emailEnter,passwordEnter);
            }
        });
        setVisible(true);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomePage();
            }
        });
    }

    public boolean comparePassword(String p1, String p2){
        if (p1.equals(p2)){
            return true;
        }
        return false;
    }
}
