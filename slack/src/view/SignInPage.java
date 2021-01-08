package view;



import controller.user.UserServiceDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


public class SignInPage extends JFrame{
    private JPanel loginPage;
    private JTextField emailS;
    private JButton signInButton;
    private JPasswordField passwordS;
    private JButton button1;
    private JLabel linkToRegister;
    private String textLink="Not a member? Register";

    public SignInPage(){
        add(loginPage);
        setSize(600,500);
        setTitle("app.Slack Login Page");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailEnter,passwordEnter;
                emailEnter = emailS.getText();
                passwordEnter = passwordS.getText();
                try {
                    if(UserServiceDAO.login(emailEnter, passwordEnter)==null){
                        JOptionPane.showMessageDialog(loginPage,"Wrong password or wrong username","LEGO Slack warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    JOptionPane.showMessageDialog(loginPage,"You're now connected");
                    new WelcomePage();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        setVisible(true);
        linkToRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkToRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new SignUpPage();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                linkToRegister.setText("<html><a href=''>" + textLink + "</a></html>");
            }
        });


    }
}