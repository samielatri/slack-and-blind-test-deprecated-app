package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import model.communication.Workspace;
import database.SQLWorkspaceDAO;
import model.SlackSystem;

public class WorkspacePage extends JFrame {
    private JPanel linkWK;
    private JPanel linkAll;
    private JPanel linkUserWK;
    private JButton button;
    private JButton button2;
    private Workspace w=null, wu=null;
    private ArrayList<Workspace>lsWK;
    private SlackSystem sys=new SlackSystem();
    private SQLWorkspaceDAO sq=new SQLWorkspaceDAO();


    public WorkspacePage() throws SQLException {
        add(linkWK);
        setSize(600,500);
        setTitle("Workspace");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //ajout des workspace de l'appli à gauche TODO
        GridBagConstraints c=new GridBagConstraints();
        lsWK= sq.selectAll();
        for (int i=0;i<lsWK.size();i++){ //add all workspaces in the left
            w=lsWK.get(i);
            button= new JButton(w.getName());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    try {
                        new ChatPage(w.getName());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            c.gridx=1;
            c.gridy=i;
            linkAll.add(button,c);

            //add all workspace of user in the right
            wu=sys.getCurrentConnectedWorkspace();
            button2= new JButton(w.getName());
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    try {
                        new ChatPage(wu.getName());
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
