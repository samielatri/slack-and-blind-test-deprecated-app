package view;

import database.DAO;
import database.DAOFactory;
import model.SlackSystem;
import model.communication.Workspace;
import model.communication.WorkspaceChannel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class ChannelList extends JFrame{
    private JPanel panel1;
    private JPanel chanPan;
    private JButton CREATECHANNELButton;
    private JButton buttonCh;


    public ChannelList(SlackSystem slackSystem) throws SQLException {
        add(panel1);
        setSize(600,700);
        setTitle("Liste des channels");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DAO<WorkspaceChannel> DAOWorkspaceChannel= DAOFactory.workspaceChannel();

        List<WorkspaceChannel> channels;
        channels= DAOWorkspaceChannel.selectAll();
        GridBagConstraints c=new GridBagConstraints();
        int i=0;
        for( WorkspaceChannel channel: channels){
            if (slackSystem.getCurrentConnectedWorkspace().getId().equals(channel.getWorkspaceId())){
                i++;
                buttonCh=new JButton(channel.getName());
                buttonCh.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //new ChatPage();
                    }
                });

                c.gridx=0;
                c.gridy=i;
                chanPan.add(buttonCh,c);
            }
        }
        CREATECHANNELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CreateChannel(slackSystem);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

}
