package view;

import client.Client;
import model.SlackSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class WelcomePage extends JFrame{


    private JLabel welcomeName;
    private JPanel welcomePage;
    private JButton createWorkspaceButton;
    private JButton joinWorkspaceButton;

    public WelcomePage(Client client, SlackSystem slackSystem) {
        JPanel welcomePage = new JPanel();
        JButton createWorkspaceButton = new JButton("Create Workspace");
        JButton joinWorkspaceButton = new JButton("Join Workspace");
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c= new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.gridheight=2;
        c.gridwidth=2;
        gridbag.setConstraints(welcomePage, c);
        add(welcomePage);
        setSize(600,700);
        setTitle("Welcome to LEGO Slack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(gridbag);

        createWorkspaceButton.setPreferredSize(new Dimension(100, 20));
        joinWorkspaceButton.setPreferredSize(new Dimension(100,20));
        welcomePage.add(createWorkspaceButton,c);
        welcomePage.add(joinWorkspaceButton,c);

        //bouton creer un workspace TODO
        createWorkspaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new CreateWorkspace(client,slackSystem);
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
                    new WorkspacePage(client,slackSystem);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
