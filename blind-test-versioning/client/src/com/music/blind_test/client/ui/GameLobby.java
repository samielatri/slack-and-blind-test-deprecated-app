package com.music.blind_test.client.ui;

import com.music.blind_test.CreateGameData;
import com.music.blind_test.GameInfo;
import com.music.blind_test.client.GameClient;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class GameLobby {
    private final GameClient client;
    private final JFrame frame;
    private final DefaultListModel<String> usersModel = new DefaultListModel<>();
    private final DefaultListModel<String> gamesModel = new DefaultListModel<>();
    JPanel rootPanel;
    private JList<String> gamesList;
    private JList<String> usersList;
    private JButton refreshGames;
    private JButton refreshUsers;
    private JButton createGameButton;

    public GameLobby(GameClient client) {
        this.client = client;

        frame = new JFrame("Music Blind Test");
        frame.setContentPane(rootPanel);
        frame.pack();

        usersList.setModel(usersModel);
        gamesList.setModel(gamesModel);
        gamesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String game = gamesModel.get(gamesList.getSelectedIndex());
                    if (game == null) return;

                    try {
                        client.joinGame(game);
                        new OngoingGame(frame, client, game);
                        frame.setVisible(false);
                    } catch (GameClient.RequestException ex) {
                        JOptionPane.showMessageDialog(rootPanel, ex.getMessage(), "Failed joining game!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        createGameButton.addActionListener(e -> {
            CreateGame dialog = new CreateGame(client);
            dialog.setOkListener(new Consumer<>() {
                @Override
                public void accept(CreateGameData data) {
                    try {
                        client.createGame(data);
                        new OngoingGame(frame, client, client.username);
                        frame.setVisible(false);
                    } catch (GameClient.RequestException ex) {
                        JOptionPane.showMessageDialog(rootPanel, ex.getMessage(), "Failed creating game!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            dialog.pack();
            dialog.setVisible(true);
        });
        refreshUsers.addActionListener(e -> loadUsers());
        refreshGames.addActionListener(e -> loadGames());

        loadUsers();
        loadGames();

        frame.setVisible(true);
    }

    private void loadUsers() {
        try {
            usersModel.clear();
            String[] users = client.userList();
            for (String username : users) usersModel.addElement(username);
        } catch (GameClient.RequestException ex) {
            ex.printStackTrace();
        }
    }

    private void loadGames() {
        try {
            gamesModel.clear();
            GameInfo[] games = client.gameList();
            for (GameInfo game : games) {
                if (game.visible)
                    gamesModel.addElement(game.name);
            }
        } catch (GameClient.RequestException ex) {
            ex.printStackTrace();
        }
    }
}
