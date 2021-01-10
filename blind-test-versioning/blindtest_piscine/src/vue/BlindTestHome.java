package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class BlindTestHome extends JFrame{
    private JButton PLAYButton;
    private JPanel Home;

    public BlindTestHome() {
        add(Home);
        setSize(500,400);
        setTitle("Blind Test Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(Home);
        PLAYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new PlayPanel();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        setVisible(true);
    }
}
