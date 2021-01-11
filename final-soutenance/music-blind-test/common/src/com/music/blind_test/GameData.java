package com.music.blind_test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class GameData implements Serializable {
    public final GameInfo info;
    public final boolean playing;
    public final HashMap<String, Integer> points;
    public final Mode mode;

    public GameData(GameInfo info, boolean playing, Mode mode, Map<String, Integer> points) {
        this.info = info;
        this.playing = playing;
        this.mode = mode;
        this.points = new HashMap<>(points);
    }

    public enum Mode {
        SONG, IMAGE
    }
}
