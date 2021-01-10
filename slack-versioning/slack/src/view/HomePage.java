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
                new SignInPage();
            }
        });

        //bouton Sign UP
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignUpPage();
            }
        });
        setVisible(true);

    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
