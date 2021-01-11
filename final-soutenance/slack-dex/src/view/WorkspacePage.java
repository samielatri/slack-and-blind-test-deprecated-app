package view;

import controller.communication.WorkspaceServiceDAO;
import database.DAO;
import database.DAOFactory;
import database.SQLWorkspaceDAO;
import model.SlackSystem;
import model.communication.Workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkspacePage extends JFrame {
    private Workspace w=null, wu=null;

    public WorkspacePage(SlackSystem slackSystem) throws SQLException {
        JPanel linkWK = new JPanel();
        JPanel linkAll=new JPanel();
        JPanel linkUserWK=new JPanel();
        JButton button= new JButton();
        JButton button2=new JButton();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c=new GridBagConstraints();
        linkWK.setLayout(gridbag);
        linkAll.setLayout(gridbag);
        linkUserWK.setLayout(gridbag);
        linkAll.add(linkUserWK);
        linkAll.add(linkWK);
        linkAll.revalidate();
        linkAll.repaint();
        add(linkWK);
        setSize(600,500);
        setTitle("Workspace");
        setContentPane(linkAll);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        button.setPreferredSize(new Dimension(200,100));
        button2.setPreferredSize(new Dimension(200,100));
        //ajout des workspace de l'appli à gauche TODO

        List<Workspace> lsWK;
        DAO<Workspace> DAOWorkspace = DAOFactory.workspace();


        WorkspaceServiceDAO workspaceServiceDAO = new WorkspaceServiceDAO(slackSystem);
        lsWK= DAOWorkspace.selectAll();
        int i=0;
        for (Workspace w : lsWK){ //add all workspaces in the left
            System.out.println(w);
            System.out.println("added");
            button= new JButton(w.getName());
            i++;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    try {
                        workspaceServiceDAO.joinWorkspace(w.getId());
                        System.out.println("joined");
                        new ProfileCreation(slackSystem);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            c.gridx=1;
            c.gridy=i;
            linkAll.add(button,c);
            linkAll.revalidate();
            linkAll.repaint();

        }

        setVisible(true);
    }

    //TODO: FAIRE UNE GROSSE VERIFICATION
}