package com.music.blind_test.server.game;

import com.music.blind_test.server.ClientHandler;

import java.util.concurrent.ThreadLocalRandom;

public class User {
    public final String name;
    public final String token;
    public final ClientHandler client;
    public Game currentGame = null;

    public User(ClientHandler client, String name) {
        this.client = client;
        this.name = name;
        this.token = Long.toHexString(ThreadLocalRandom.current().nextLong());
    }
}
