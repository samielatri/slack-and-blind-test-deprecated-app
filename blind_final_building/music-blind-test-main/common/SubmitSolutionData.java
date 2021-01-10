package com.music.blind_test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class SubmitSolutionData implements Serializable {
    public final int roundId;
    public final String solution;

    public SubmitSolutionData(int roundId, String solution) {
        this.roundId = roundId;
        this.solution = solution;
    }

    public static SubmitSolutionData parse(byte[] body) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body))) {
            return (SubmitSolutionData) ois.readObject();
        }
    }
}
