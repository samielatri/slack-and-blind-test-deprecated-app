package view;

import client.Client;
import controller.communication.ChannelServiceDAO;
import database.SQLWorkspaceChannelDAO;
import model.SlackSystem;
import model.communication.WorkspaceChannel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreateChannel extends JFrame {
    private JPanel createCH;
    private JTextField nameCH;
    private WorkspaceChannel ch=null;


    public CreateChannel(Client client, SlackSystem slackSystem) throws SQLException {
        JPanel createCH = new JPanel();
        JTextField nameCH= new JTextField();
        JButton CREATEchButton= new JButton("CREATE");
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c=new GridBagConstraints();
        CREATEchButton.setPreferredSize(new Dimension(100,20));
        nameCH.setPreferredSize(new Dimension(200,200));
        createCH.setLayout(gridbag);
        createCH.add(nameCH);
        createCH.add(CREATEchButton);
        add(createCH);
        setSize(400,400);
        setTitle("Create your Channel");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setContentPane(createCH);
        setLocationRelativeTo(null);

        ChannelServiceDAO channelServiceDAO=new ChannelServiceDAO(slackSystem);
        //creation d'un channel TODO
        CREATEchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String channelEntered = nameCH.getText();
                try {
                    ch=channelServiceDAO.createCh(channelEntered);
                    if(ch==null){
                        JOptionPane.showMessageDialog(createCH,"This channel couldn't be created", "LEGO Slack warning",JOptionPane.WARNING_MESSAGE);
                    }
                    JOptionPane.showMessageDialog(createCH,"Your channel is successfully created");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    dispose();
                    new ChannelList(client,slackSystem);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        setVisible(true);
    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
