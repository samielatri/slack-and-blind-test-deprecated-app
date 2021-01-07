package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private JPanel Home;
    private JButton signInButton;
    private JButton signUpButton;

    public HomePage(){
        add(Home);
        setSize(400,200);
        setTitle("app.Slack Home Page");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(Home);
        //getContentPane().add(signInButton);
        //getContentPane().add(signUpButton);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignInPage();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignUpPage();
            }
        });
        setVisible(true);

    }
}
