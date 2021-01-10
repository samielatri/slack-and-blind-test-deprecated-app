package view;

import database.SQLMessageChannelDAO;
import database.SQLProfileDAO;
import database.SQLWorkspaceChannelDAO;
import model.SlackSystem;
import model.communication.Message;
import model.communication.WorkspaceChannel;
import model.user.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;


public class ChatPage extends JFrame {
    private JPanel chatPage;
    private JPanel channelList;
    private JButton buttonCreateCH;
    private JTextField msgEnter;
    private JButton sendbtn;
    private JTextPane msgSpace;
    private JTextPane listUsers;
    private JButton buttonCh;
    private JLabel profileName;
    private CardLayout cardLayout;
    PrintWriter output;
    private String oldMsg = "";
    private SlackSystem sys = new SlackSystem();
    private ArrayList<Profile> listP;
    private ArrayList<WorkspaceChannel> listOfCh;
    private SQLProfileDAO sp=new SQLProfileDAO();
    private SQLWorkspaceChannelDAO swC=new SQLWorkspaceChannelDAO();
    private SQLMessageChannelDAO smc=new SQLMessageChannelDAO();
    private Profile p=null;
    private WorkspaceChannel ch=null;
    private Message m=null;

    public ChatPage(String n) throws SQLException {
        add(chatPage);
        setSize(400,500);
        setTitle("LEGO Slack "+n);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //bouton creer channels TODO
        buttonCreateCH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new CreateChannel();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });




        //bouton envoyé TODO
        sendbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }
        });
        sendbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();//TODO: verifier si c'est la bonne methode pour envoyer le msg (mettre dans le fil de discussion)
                m=new Message(sys.getCurrentConnectedProfile(),msgEnter.getText());
                smc.insert(m);
            }
        });

        //liste des profile sur la droite TODO
        GridBagConstraints c=new GridBagConstraints();
        listP= (ArrayList<Profile>) sp.selectAll();
        for(int i=0; i<listP.size();i++){
            p=listP.get(i);
            profileName=new JLabel(p.getUsername());
            profileName.setCursor(new Cursor(Cursor.HAND_CURSOR));
            profileName.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        new ProfileUser();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    profileName.setText("<html><a href=''>" + p.getUsername() + "</a></html>");
                }
            });
            c.gridx=0;
            c.gridy=i;
            listUsers.add(profileName,c);
        }

        //liste des channels du workspace TODO
        listOfCh= (ArrayList<WorkspaceChannel>) swC.selectAll();
        for (int j=0;j<listOfCh.size();j++){
            if (sys.getCurrentConnectedWorkspace().equals(listOfCh.get(j))){
                ch=listOfCh.get(j);
                buttonCh=new JButton(ch.getName());
                buttonCh.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        cardLayout.show(channelList,ch.getName());
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                    }
                });
                c.gridx=0;
                c.gridy=j;
                channelList.add(buttonCh,c);
            }
            msgSpace.add(new ChatPage(ch.getName()), ch.getName());
            cardLayout.show(msgSpace,ch.getName());
        }

        setVisible(true);

    }
    //fonction pour ajouter les message dans l'espace message TODO
    public void sendMessage(){
        try {
            String message = msgEnter.getText().trim();
            if (message.equals("")) {
                return;
            }
            this.oldMsg = message;
            output.println(message);
            msgEnter.requestFocus();
            msgEnter.setText(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.exit(0);
        }
    }

    //recup des nom des profil affiché
    public String getProfileName(){
        return profileName.getText();
    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
