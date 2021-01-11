package com.music.blind_test.client;

import com.music.blind_test.Packet;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Client implements Closeable {
    private static final Logger LOGGER = Logger.getLogger("Client");
    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;
    private final ReceiveLoop receiveLooper;
    private final SendLoop sendLooper;
    private final PacketsListener listener;
    private final Object packetWaitLock = new Object();
    private Packet.Type packetWaitType = null;
    private Packet packetWaitResp = null;

    public Client(String host, int port, PacketsListener listener) throws IOException {
        this.listener = listener;

        socket = new Socket(host, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        new Thread(receiveLooper = new ReceiveLoop(), "packets-receiver").start();
        new Thread(sendLooper = new SendLoop(), "packets-sender").start();
    }

    public <T> T requestObj(Packet.Type type) throws InterruptedException, IOException, ClassNotFoundException {
        return requestObj(type, new byte[0]);
    }

    public <T> T requestObj(Packet.Type type, byte[] body) throws InterruptedException, IOException, ClassNotFoundException {
        Packet resp = request(type, body);
        if (resp == null) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(resp.body))) {
            return (T) ois.readObject();
        }
    }

    public Packet request(Packet.Type type) throws InterruptedException {
        return request(type, new byte[0]);
    }

    public Packet request(Packet.Type type, String body) throws InterruptedException {
        return request(type, body.getBytes(StandardCharsets.UTF_8));
    }

    public Packet request(Packet.Type type, byte[] body) throws InterruptedException {
        if (packetWaitType != null) {
            synchronized (packetWaitLock) {
                packetWaitLock.wait(50000);
            }
        }

        send(type, body);

        synchronized (packetWaitLock) {
            packetWaitType = type;
            packetWaitLock.wait(2000);
        }

        packetWaitType = null;
        return packetWaitResp;
    }

    public void send(Packet.Type type) {
        send(type, new byte[0]);
    }

    public void send(Packet.Type type, byte[] body) {
        sendLooper.packets.add(new Packet(type, Packet.Status.OK, body)); // Status isn't used, anything will work
    }

    @Override
    public void close() throws IOException {
        receiveLooper.stop();
        sendLooper.stop();
        socket.close();
    }

    public interface PacketsListener {
        void onPacket(Packet.Type type, byte[] body) throws IOException, ClassNotFoundException;
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
                    LOGGER.log(Level.SEVERE, "Failed sending packet.", ex);
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
                        if (packetWaitType != null) {
                            packetWaitResp = null;
                            synchronized (packetWaitLock) {
                                packetWaitLock.notify();
                            }
                        }

                        LOGGER.log(Level.SEVERE, "Received bad packet.");
                        continue;
                    }
                } catch (IOException ex) {
                    if (packetWaitType != null) {
                        packetWaitResp = null;
                        synchronized (packetWaitLock) {
                            packetWaitLock.notify();
                        }
                    }

                    if (shouldStop) break;

                    LOGGER.log(Level.SEVERE, "Failed receiving packet.", ex);
                    continue;
                }

                if (packetWaitType != null && packetWaitType == packet.type) {
                    packetWaitResp = packet;
                    synchronized (packetWaitLock) {
                        packetWaitLock.notify();
                    }
                } else {
                    try {
                        listener.onPacket(packet.type, packet.body);
                    } catch (IOException | ClassNotFoundException ex) {
                        LOGGER.log(Level.SEVERE, "Failed handling packet.", ex);
                    }
                }
            }

            if (thread != null) thread.interrupt();
            thread = null;
        }
    }
}
