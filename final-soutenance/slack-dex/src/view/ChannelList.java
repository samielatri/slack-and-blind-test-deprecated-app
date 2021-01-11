package view;

import client.Client;
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

    public ChannelList(Client client, SlackSystem slackSystem) throws SQLException {
        JPanel chanPan=new JPanel();
        JPanel panel1=new JPanel();
        JButton buttonCh= new JButton();
        JButton CREATECHANNELButton=new JButton("CREATE CHANNEL");
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c=new GridBagConstraints();
        panel1.setLayout(gridbag);
        chanPan.setLayout(gridbag);
        panel1.add(chanPan);
        panel1.add(CREATECHANNELButton);
        panel1.revalidate();
        panel1.repaint();
        add(panel1);
        setSize(600,700);
        setTitle("Liste des channels");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(panel1);


        DAO<WorkspaceChannel> DAOWorkspaceChannel= DAOFactory.workspaceChannel();

        List<WorkspaceChannel> channels;
        channels= DAOWorkspaceChannel.selectAll();
        int i=0;
        for( WorkspaceChannel channel: channels){
            if (slackSystem.getCurrentConnectedWorkspace().getId().equals(channel.getWorkspaceId())){
                i++;
                buttonCh=new JButton(channel.getName());
                buttonCh.setPreferredSize(new Dimension(200,80));
                buttonCh.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            new ChatPage(slackSystem,client);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });

                c.gridx=0;
                c.gridy=i;
                chanPan.add(buttonCh,c);
                chanPan.revalidate();
                chanPan.repaint();
            }
        }
        CREATECHANNELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CreateChannel(client,slackSystem);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

}
