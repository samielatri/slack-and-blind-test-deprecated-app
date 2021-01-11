package com.music.blind_test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Packet {
    public final Type type;
    public final Status status;
    public final byte[] body;

    public Packet(Type type, Status status, byte[] body) {
        this.type = type;
        this.status = status;
        this.body = body;
    }

    public static Packet readFrom(DataInputStream in) throws IOException {
        int length = in.readInt() - 2 - 2;
        if (length < 0) return null;

        short typeVal = in.readShort();
        short statusVal = in.readShort();

        byte[] body = new byte[length];
        in.readFully(body);

        Type type = Type.parse(typeVal);
        if (type == null) return null;

        Status status = Status.parse(statusVal);
        if (status == null) return null;

        return new Packet(type, status, body);
    }

    public void writeTo(DataOutputStream out) throws IOException {
        out.writeInt(body.length + 2 + 2);
        out.writeShort(type.val);
        out.writeShort(status.val);
        out.write(body);
        out.flush();
    }

    public enum Type {
        HELLO(0x00, false),
        GAME_LIST(0x01, true),
        USER_LIST(0x02, true),
        CREATE_GAME(0x03, true),
        JOIN_GAME(0x04, true),
        LEAVE_GAME(0x05, true),
        START_GAME(0x06, true),
        GAME_DATA(0x07, true),
        STOP_GAME(0x08, true),
        CATEGORIES(0x09, true),
        EVENT_START_ROUND(0x0a, null),
        SUBMIT_ROUND_SOLUTION(0x0b, true),
        EVENT_END_ROUND(0x0c, null),
        EVENT_STOP(0x0d, null);

        public final Boolean requiresAuth;
        final short val;

        Type(int val, Boolean requiresAuth) {
            this.val = (short) val;
            this.requiresAuth = requiresAuth;
        }

        static Type parse(short val) {
            for (Type type : values())
                if (type.val == val)
                    return type;

            return null;
        }
    }

    public enum Status {
        OK(0x00), FAILED_HELLO(0x01),
        BAD_AUTH(0x02), FAILED_CREATE_GAME(0x03),
        FAILED_JOIN_GAME(0x04), FAILED_START_GAME(0x05),
        FAILED_GAME_DATA(0x06), FAILED_STOP_GAME(0x07),
        FAILED_SUBMIT_SOLUTION(0x08), SERVER_ERROR(0x09);

        final short val;

        Status(int val) {
            this.val = (short) val;
        }

        static Status parse(short val) {
            for (Status status : values())
                if (status.val == val)
                    return status;

            return null;
        }
    }
}
