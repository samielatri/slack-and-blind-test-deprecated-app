package com.music.blind_test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EndRoundData implements Serializable {
    public final String roundWinner;
    public final HashMap<String, Integer> points;

    public EndRoundData(String roundWinner, Map<String, Integer> points) {
        this.roundWinner = roundWinner;
        this.points = new HashMap<>(points);
    }

    public static EndRoundData parse(byte[] body) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body))) {
            return (EndRoundData) ois.readObject();
        }
    }
}
