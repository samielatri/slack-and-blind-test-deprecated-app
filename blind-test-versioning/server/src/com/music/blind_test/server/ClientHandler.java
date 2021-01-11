package com.music.blind_test.server;

import com.music.blind_test.Packet;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class ClientHandler implements Closeable {
    private static final Logger LOGGER = Logger.getLogger("ClientHandler");
    private final Socket client;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final PacketsListener listener;
    private final SendLoop sendLooper;
    private final ReceiveLoop receiveLooper;

    public ClientHandler(Socket client, ExecutorService executorService, PacketsListener listener) throws IOException {
        this.client = client;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
        this.listener = listener;

        executorService.execute(receiveLooper = new ReceiveLoop());
        executorService.execute(sendLooper = new SendLoop());
    }

    public void sendObj(Packet.Type type, Packet.Status status, Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();

        sendLooper.packets.add(new Packet(type, status, baos.toByteArray()));
    }

    public void send(Packet.Type type, Packet.Status status) {
        send(type, status, new byte[0]);
    }

    public void send(Packet.Type type, Packet.Status status, byte[] body) {
        sendLooper.packets.add(new Packet(type, status, body));
    }

    @Override
    public void close() throws IOException {
        sendLooper.stop();
        receiveLooper.stop();
        client.close();
    }

    public interface PacketsListener {
        void onPacket(ClientHandler client, Packet.Type type, byte[] body) throws IOException, ClassNotFoundException;
        void onDisconnected(ClientHandler client);
    }

    private class SendLoop implements Runnable {
        private final BlockingQueue<Packet> packets = new LinkedBlockingQueue<>();
        private volatile boolean shouldStop = false;
        private Thread thread;

        void stop() {
            shouldStop = true;
            if (thread != null) thread.interrupt();
            thread = null;
        }

        @Override
        public void run() {
            thread = Thread.currentThread();

            while (!shouldStop) {
                Packet packet;
                try {
                    packet = packets.take();
                } catch (InterruptedException ignored) {
                    break;
                }

                try {
                    packet.writeTo(out);
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "[" + client.getInetAddress() + "] Failed sending packet.", ex);
                }
            }

            if (thread != null) thread.interrupt();
            thread = null;
        }
    }

    private class ReceiveLoop implements Runnable {
        private volatile boolean shouldStop = false;
        private Thread thread;

        void stop() {
            shouldStop = true;
            if (thread != null) thread.interrupt();
            thread = null;
        }

        @Override
        public void run() {
            thread = Thread.currentThread();

            while (!shouldStop) {
                Packet packet;
                try {
                    packet = Packet.readFrom(in);
                    if (packet == null) {
                        LOGGER.log(Level.SEVERE, "[" + client.getInetAddress() + "] Received bad packet.");
                        break;
                    }
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "[" + client.getInetAddress() + "] Failed receiving packet.", ex);
                    break;
                }

                try {
                    listener.onPacket(ClientHandler.this, packet.type, packet.body);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "[" + client.getInetAddress() + "] Failed sending response.", ex);
                    break;
                }
            }

            listener.onDisconnected(ClientHandler.this);

            if (thread != null) thread.interrupt();
            thread = null;
        }
    }
}
