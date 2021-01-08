package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.communication.Workspace;

public class WorkspacePage extends JFrame {
    private JPanel linkWK;
    private JButton button;
    private Workspace w=null;
    private ArrayList<Workspace> lsWK=w.getWorkspaceChannels();

    public WorkspacePage() {
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
            linkWK.add(button,c);
        }
        setVisible(true);
    }


}
