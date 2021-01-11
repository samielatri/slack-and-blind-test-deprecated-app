package com.music.blind_test.client.ui;

import com.music.blind_test.client.GameClient;

import javax.swing.*;
import java.io.IOException;

public class ConnectToServer {
    private final JFrame frame;
    public JPanel rootPanel;
    private JTextField serverAddrField;
    private JTextField usernameField;
    private JButton connectButton;

    public ConnectToServer() {
        frame = new JFrame("Connect - Music Blind Test");
        frame.setContentPane(rootPanel);
        frame.pack();

        connectButton.addActionListener(e -> {
            try {
                GameClient client = new GameClient(serverAddrField.getText(), usernameField.getText());
                new GameLobby(client);

                frame.setVisible(false);
                frame.dispose();
            } catch (IOException | GameClient.RequestException ex) {
                JOptionPane.showMessageDialog(rootPanel, ex.getMessage(), "Failed joining server!", JOptionPane.ERROR_MESSAGE);
            } catch (InterruptedException ignored) {
            }
        });

        frame.setVisible(true);
    }
}
