package com.music.blind_test.server.game;

import com.music.blind_test.Packet;
import com.music.blind_test.server.ClientHandler;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public final class Database implements Users.PacketsListener {
    private final Connection connection;

    public Database(String url, Properties properties) throws SQLException {
        connection = DriverManager.getConnection(url, properties);
    }

    List<String> getCategories() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCT category FROM entries")) {
            try (ResultSet set = stmt.executeQuery()) {
                List<String> list = new LinkedList<>();
                while (set.next()) list.add(set.getString("category"));
                return list;
            }
        }
    }

    private List<Long> loadEntries(String category, Game.Mode mode) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT id FROM entries WHERE mode=? AND category=?")) {
            stmt.setString(1, mode.name());
            stmt.setString(2, category);
            try (ResultSet set = stmt.executeQuery()) {
                List<Long> list = new LinkedList<>();
                while (set.next()) list.add(set.getLong("id"));
                return list;
            }
        }
    }

    public List<Long> loadEntries(List<String> categories, Game.Mode mode) throws SQLException {
        LinkedList<Long> list = new LinkedList<>();
        for (String category : categories) list.addAll(loadEntries(category, mode));
        return list;
    }

    public byte[] loadEntry(long entry) throws IOException, SQLException {
        String path;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT path FROM entries WHERE id=?")) {
            stmt.setLong(1, entry);
            try (ResultSet set = stmt.executeQuery()) {
                if (!set.next()) path = null;
                else path = set.getString("path");
            }
        }

        if (path == null)
            return null;

        try (FileInputStream in = new FileInputStream(path)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(in.available());
            in.transferTo(baos);
            return baos.toByteArray();
        }
    }

    public String loadSolution(long entry) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT solution FROM entries WHERE id=?")) {
            stmt.setLong(1, entry);
            try (ResultSet set = stmt.executeQuery()) {
               if (!set.next()) return null;
               else return set.getString("solution");
            }
        }
    }

    @Override
    public void onPacket(ClientHandler client, User user, Packet.Type type, byte[] body) throws IOException {
        switch (type) {
            case CATEGORIES:
                try {
                    client.sendObj(Packet.Type.CATEGORIES, Packet.Status.OK, getCategories().toArray(new String[0]));
                } catch (SQLException ex) {
                    client.send(Packet.Type.CATEGORIES, Packet.Status.SERVER_ERROR);
                }
                break;
        }
    }
}
