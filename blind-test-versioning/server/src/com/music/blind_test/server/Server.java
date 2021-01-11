package com.music.blind_test.server;

import com.music.blind_test.server.game.Users;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Server implements Closeable {
    private static final Logger LOGGER = Logger.getLogger("Server");
    private final Users users;
    private final ServerSocket server;
    private final Looper looper;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public Server(Users users, int port) throws IOException {
        this.users = users;
        server = new ServerSocket(port);
        new Thread(looper = new Looper()).start();
    }

    @Override
    public void close() throws IOException {
        looper.stop();
        server.close();
    }

    private class Looper implements Runnable {
        private final List<ClientHandler> clients = new ArrayList<>(20);
        private Thread thread;
        private volatile boolean shouldStop = false;

        void stop() {
            shouldStop = true;
            thread.interrupt();

            clients.forEach(client -> {
                try {
                    client.close();
                } catch (IOException ignored) {
                }
            });
        }

        @Override
        public void run() {
            thread = Thread.currentThread();

            while (!shouldStop) {
                Socket client;
                try {
                    client = server.accept();
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "Failed accepting client.", ex);
                    continue;
                }

                try {
                    clients.add(new ClientHandler(client, executorService, users));
                    LOGGER.log(Level.INFO, "[" + client.getInetAddress() + "] Accepted new client.");
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "[" + client.getInetAddress() + "] Failed creating client handler.", ex);
                }
            }

            if (thread != null) thread.interrupt();
            thread = null;
        }
    }
}
