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
    private JPanel linkWK;
    private JPanel linkAll;
    private JPanel linkUserWK;
    private JButton button;
    private JButton button2;
    private Workspace w=null, wu=null;

    public WorkspacePage(SlackSystem slackSystem) throws SQLException {
        JPanel linkWK = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c=new GridBagConstraints();
        linkWK.setLayout(gridbag);
        add(linkWK);
        setSize(600,500);
        setTitle("Workspace");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //ajout des workspace de l'appli à gauche TODO

        List<Workspace> lsWK;
        DAO<Workspace> DAOWorkspace = DAOFactory.workspace();

        WorkspaceServiceDAO workspaceServiceDAO = new WorkspaceServiceDAO(slackSystem);
        lsWK= DAOWorkspace.selectAll();
        for (int i=0;i<lsWK.size();i++){ //add all workspaces in the left
            w=lsWK.get(i);
            button= new JButton(w.getName());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    try {
                        workspaceServiceDAO.joinWorkspace(w.getId());
                        new ProfileCreation(slackSystem);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            c.gridx=1;
            c.gridy=i;
            linkAll.add(button,c);

            //add all workspace of user in the right
            wu=slackSystem.getCurrentConnectedWorkspace();
            button2= new JButton(w.getName());
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    try {
                       workspaceServiceDAO.joinWorkspace(wu.getId());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            c.gridx=1;
            c.gridy=i;
            linkUserWK.add(button2,c);
        }




        setVisible(true);
    }

    //TODO: FAIRE UNE GROSSE VERIFICATION
}
