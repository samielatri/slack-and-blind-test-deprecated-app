package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class WelcomePage extends JFrame{
    private JButton createWorkspaceButton;
    private JPanel welcomePage;
    private JButton joinWorkspaceButton;
    private JLabel welcomeName;

    public WelcomePage() {
        add(welcomePage);
        setSize(600,700);
        setTitle("Welcome to LEGO Slack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //bouton creer un workspace TODO
        createWorkspaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new CreateWorkspace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        //bouton rejoindre un workspace TODO
        joinWorkspaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new WorkspacePage();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
