package com.music.blind_test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreateGameData implements Serializable {
    public final boolean practice;
    public final GameData.Mode mode;
    public final ArrayList<String> categories;
    public final int maxRounds;

    public CreateGameData(boolean practice, GameData.Mode mode, List<String> categories, int maxRounds) {
        this.practice = practice;
        this.mode = mode;
        this.categories = new ArrayList<>(categories);
        this.maxRounds = maxRounds;
    }

    public static CreateGameData parse(byte[] body) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body))) {
            return (CreateGameData) ois.readObject();
        }
    }
}
