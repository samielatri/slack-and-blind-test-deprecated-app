package view;

import client.Client;
import controller.communication.WorkspaceServiceDAO;
import database.SQLWorkspaceDAO;
import model.SlackSystem;
import model.communication.Workspace;
//import view.WelcomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class CreateWorkspace extends JFrame{
    private Workspace wp;

    public CreateWorkspace(Client client, SlackSystem slackSystem) throws SQLException {
        JPanel createWSPage = new JPanel();
        JTextField wkName=new JTextField();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c=new GridBagConstraints();
        JButton CREATEButton=new JButton("CREATE");
        createWSPage.setLayout(gridbag);
        c.fill = GridBagConstraints.CENTER;
        c.weightx=2;
        c.weighty=2;
        gridbag.setConstraints(createWSPage, c);
        add(createWSPage);
        setSize(300,400);
        setTitle("Create your Workspace");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        CREATEButton.setPreferredSize(new Dimension(100,20));
        wkName.setPreferredSize(new Dimension(90,20));
        createWSPage.add(CREATEButton,c);
        createWSPage.add(wkName,c);
        setLocationRelativeTo(null);
        //creation du workspace TODO
        SQLWorkspaceDAO ws= new SQLWorkspaceDAO();
        WorkspaceServiceDAO workspaceServiceDAO = new WorkspaceServiceDAO(slackSystem);

        CREATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameOfWorkspace=wkName.getText();
                try {
                    wp=workspaceServiceDAO.createWs(nameOfWorkspace);
                    if(wp==null){
                        JOptionPane.showMessageDialog(createWSPage,"This name already exist please enter another one","LEGO Slack warning",JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                JOptionPane.showMessageDialog(createWSPage,"Your workspace was successfully created");
                dispose();
                new WelcomePage(client,slackSystem);
            }
        });
        setVisible(true);
    }

    //TODO: VERIFIER L'ENSEMBLE SI TOUT EST CORDA
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
