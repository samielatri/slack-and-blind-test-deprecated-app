package com.music.blind_test.server;

import com.music.blind_test.server.game.Database;
import com.music.blind_test.server.game.Users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    private static Properties getDbConfig() {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");
        return props;
    }

    public static void main(String[] args) throws IOException, SQLException {
        Database database = new Database("jdbc:mysql://localhost/blindtest", getDbConfig());
        Users users = new Users(database);

        int port = Integer.parseInt(args[0]);
        Server server = new Server(users, port);
        System.out.println("Server listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.close();
            } catch (IOException ignored) {
            }
        }));
    }
}
