package com.music.blind_test.client;

import com.music.blind_test.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameClient implements Client.PacketsListener {
    public final String username;
    private final Client client;
    private final String token;
    private final Set<Client.PacketsListener> listeners = Collections.synchronizedSet(new HashSet<>());

    public GameClient(String addr, String username) throws IOException, RequestException, InterruptedException {
        this.username = username;

        String[] split = addr.split(":");
        client = new Client(split[0], Integer.parseInt(split[1]), this);

        Packet resp = client.request(Packet.Type.HELLO, username);
        if (resp == null || resp.status != Packet.Status.OK) throw new RequestException(resp);

        token = new String(resp.body);
    }

    public String[] userList() throws RequestException {
        try {
            return client.requestObj(Packet.Type.USER_LIST, token.getBytes(StandardCharsets.UTF_8));
        } catch (InterruptedException | ClassNotFoundException | IOException ex) {
            throw new RequestException(ex);
        }
    }

    public GameInfo[] gameList() throws RequestException {
        try {
            return client.requestObj(Packet.Type.GAME_LIST, token.getBytes(StandardCharsets.UTF_8));
        } catch (InterruptedException | ClassNotFoundException | IOException ex) {
            throw new RequestException(ex);
        }
    }

    public void leaveGame() throws RequestException {
        try {
            client.request(Packet.Type.LEAVE_GAME, token.getBytes(StandardCharsets.UTF_8));
        } catch (InterruptedException ex) {
            throw new RequestException(ex);
        }
    }

    public void joinGame(String game) throws RequestException {
        try {
            client.request(Packet.Type.JOIN_GAME, (token + game).getBytes(StandardCharsets.UTF_8));
        } catch (InterruptedException ex) {
            throw new RequestException(ex);
        }
    }

    public void createGame(CreateGameData data) throws RequestException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            baos.write(token.getBytes(StandardCharsets.UTF_8));
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(data);
            }

            client.request(Packet.Type.CREATE_GAME, baos.toByteArray());
        } catch (InterruptedException | IOException ex) {
            throw new RequestException(ex);
        }
    }

    public void startGame() throws RequestException {
        try {
            client.request(Packet.Type.START_GAME, token.getBytes(StandardCharsets.UTF_8));
        } catch (InterruptedException ex) {
            throw new RequestException(ex);
        }
    }

    public void stopGame() throws RequestException {
        try {
            client.request(Packet.Type.STOP_GAME, token.getBytes(StandardCharsets.UTF_8));
        } catch (InterruptedException ex) {
            throw new RequestException(ex);
        }
    }

    public GameData gameData() throws RequestException {
        try {
            return client.requestObj(Packet.Type.GAME_DATA, token.getBytes(StandardCharsets.UTF_8));
        } catch (InterruptedException | ClassNotFoundException | IOException ex) {
            throw new RequestException(ex);
        }
    }

    public String[] getCategories() throws RequestException {
        try {
            return client.requestObj(Packet.Type.CATEGORIES, token.getBytes(StandardCharsets.UTF_8));
        } catch (InterruptedException | ClassNotFoundException | IOException ex) {
            throw new RequestException(ex);
        }
    }

    public void submitRoundSolution(int roundId, String solution) throws RequestException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            baos.write(token.getBytes(StandardCharsets.UTF_8));
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(new SubmitSolutionData(roundId, solution));
            }

            client.request(Packet.Type.SUBMIT_ROUND_SOLUTION, baos.toByteArray());
        } catch (InterruptedException |  IOException ex) {
            throw new RequestException(ex);
        }
    }

    @Override
    public void onPacket(Packet.Type type, byte[] body) throws IOException, ClassNotFoundException {
        synchronized (listeners) {
            for (Client.PacketsListener listener : listeners)
                listener.onPacket(type, body);
        }
    }

    public void addPacketsListener(Client.PacketsListener listener) {
        listeners.add(listener);
    }

    public static class RequestException extends Exception {
        RequestException(Packet resp) {
            super(resp == null ? "Network exception" : (resp.type + " -> " + resp.status));
        }

        RequestException(Throwable cause) {
            super(cause);
        }
    }
}
