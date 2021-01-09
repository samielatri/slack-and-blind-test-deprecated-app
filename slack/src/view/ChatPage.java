package view;

import model.SlackSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;


public class ChatPage extends JFrame {
    private JPanel chatPage;
    private JPanel channelList;
    private JButton buttonCreateCH;
    private JTextField msgEnter;
    private JButton sendbtn;
    private JTextPane msgSpace;
    private JTextPane listUsers;
    private JLabel profileName;
    PrintWriter output;
    private String oldMsg = "";
    private SlackSystem sys = new SlackSystem();

    public ChatPage(String n) {
        add(chatPage);
        setSize(400,500);
        setTitle("LEGO Slack "+n);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        buttonCreateCH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreateChannel();
            }
        });
        setVisible(true);
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
            }
        });

        //liste des profile connecté
        profileName.setText(""+sys.getCurrentConnectedProfile()); //TODO: verifier si c'est la bonne methode
        listUsers.add(profileName);

    }
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
    //TODO: finaliser la page
    //TODO: ajouter les boutons des channel du workspace
}
