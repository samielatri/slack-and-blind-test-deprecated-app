package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Workspace w=null;
    private ArrayList<Workspace> lsWK=SQLWorkspaceDAO.selectAll;
    private SlackSystem sys=new SlackSystem();

    public WorkspacePage() {
        add(linkWK);
        setSize(600,500);
        setTitle("Workspace");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        GridBagConstraints c=new GridBagConstraints();

        for (int i=0;i<lsWK.size;i++){
            w=lsWk.get(i);
            button= new JButton(w.getName());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new ChatPage(w.getName());
                }
            });
            c.gridx=1;
            c.gridy=i;
            linkAll.add(button,c);
        }

        w=SlackSystem.getCurrentConnectedWorkspace();
        button2= new JButton(w.getName());
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ChatPage(w.getName());
            }
        });
        c.gridx=1;
        c.gridy=j;
        linkUserWK.add(button2,c);


        setVisible(true);
    }
    //TODO: coordiner le tout


}
