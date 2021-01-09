package view;

import database.SQLWorkspaceChannelDAO;
import model.SlackSystem;
import model.communication.WorkspaceChannel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreateChannel extends JFrame {
    private JPanel createCH;
    private JTextField nameCH;
    private JButton CREATEchButton;
    private SQLWorkspaceChannelDAO wc = new SQLWorkspaceChannelDAO();
    private WorkspaceChannel ch=null;
    private SlackSystem sys = new SlackSystem();

    public CreateChannel() throws SQLException {
        add(createCH);
        setSize(300,400);
        setTitle("Create your Channel");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //creation d'un channel TODO
        CREATEchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String channelEntered = nameCH.getText();
                ch=new WorkspaceChannel(channelEntered,sys.getCurrentConnectedProfile());
                wc.insert(ch);
                JOptionPane.showMessageDialog(createCH,"Your channel is successfully created");
                sys.setCurrentConnectedWorkspaceChannel(ch);
                try {
                    dispose();
                    new ChatPage(ch.getName());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        setVisible(true);
    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
