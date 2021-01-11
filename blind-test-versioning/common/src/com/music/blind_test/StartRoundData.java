package com.music.blind_test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class StartRoundData implements Serializable {
    public final int roundId;
    public final int delayMs;
    public final byte[] entry;

    public StartRoundData(int roundId, int delayMs, byte[] entry) {
        this.roundId = roundId;
        this.delayMs = delayMs;
        this.entry = entry;
    }

    public static StartRoundData parse(byte[] body) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body))) {
            return (StartRoundData) ois.readObject();
        }
    }
}
