package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPage extends JFrame {
    private JPanel chatPage;
    private JPanel channelList;
    private JPanel msgSpace;
    private JPanel userOfCh;
    private JButton buttonCreateCH;
    private JTextField textField1;
    private JButton button1;

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
    }
    //TODO: finaliser la page
}
