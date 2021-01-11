package com.music.blind_test.server.game;

import com.music.blind_test.Packet;
import com.music.blind_test.server.ClientHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Users implements ClientHandler.PacketsListener {
    private final Games games;
    private final List<User> users = Collections.synchronizedList(new ArrayList<>(50));

    public Users(Database database) {
        this.games = new Games(database);
    }

    private String addUser(ClientHandler client, String username) {
        synchronized (users) {
            for (User user : users) {
                if (user.name.equals(username))
                    return null;
            }
        }

        User user = new User(client, username);
        users.add(user);
        return user.token;
    }

    @Override
    public void onPacket(ClientHandler client, Packet.Type type, byte[] body) throws IOException, ClassNotFoundException {
        User user = null;
        if (type.requiresAuth) {
            if (body.length < 16) {
                client.send(type, Packet.Status.BAD_AUTH);
                return;
            }

            String token = new String(body, 0, 16);
            if (body.length > 16) body = Arrays.copyOfRange(body, 16, body.length);
            else body = new byte[0];

            for (User u : users) {
                if (u.token.equals(token)) {
                    user = u;
                    break;
                }
            }

            if (user == null) {
                client.send(type, Packet.Status.BAD_AUTH);
                return;
            }
        }

        switch (type) {
            case HELLO:
                String token;
                if ((token = addUser(client, new String(body))) != null)
                    client.send(Packet.Type.HELLO, Packet.Status.OK, token.getBytes(StandardCharsets.UTF_8));
                else
                    client.send(Packet.Type.HELLO, Packet.Status.FAILED_HELLO);
                break;
            case USER_LIST:
                String[] usersArray = users.stream().map(u -> u.name).toArray(String[]::new);
                client.sendObj(Packet.Type.USER_LIST, Packet.Status.OK, usersArray);
                break;
            default:
                games.onPacket(client, user, type, body);
                break;
        }
    }

    @Override
    public void onDisconnected(ClientHandler client) {
        users.removeIf(user -> user.client == client);
    }

    public interface PacketsListener {
        void onPacket(ClientHandler client, User user, Packet.Type type, byte[] body) throws IOException, ClassNotFoundException;
    }
}
