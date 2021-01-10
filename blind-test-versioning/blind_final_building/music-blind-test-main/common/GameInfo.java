package com.music.blind_test;

import java.io.Serializable;

public final class GameInfo implements Serializable {
    public final String name;
    public final boolean visible;

    public GameInfo(String name, boolean visible) {
        this.name = name;
        this.visible = visible;
    }
}
