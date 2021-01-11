package com.music.blind_test.server.game;

import com.music.blind_test.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Game {
    private static final int SUBMISSION_DELAY = 8;
    private static final int ROUND_MAX_TIME = 30;
    private static final int NEXT_ROUND_DELAY = 5;
    public final Set<User> players;
    public final boolean practice;
    public final Mode mode;
    private final ScheduledExecutorService scheduler;
    private final Database database;
    private final List<String> categories;
    private final LinkedList<Long> entries = new LinkedList<>();
    private final Map<String, Integer> points = Collections.synchronizedMap(new HashMap<>());
    private final int maxRounds;
    public String owner;
    public boolean playing;
    private int roundIdCounter = 0;
    private Round currentRound = null;
    private ScheduledFuture<?> endRoundFuture = null;
    private ScheduledFuture<?> nextRoundFuture = null;

    public Game(ScheduledExecutorService scheduler, Database database, User owner, CreateGameData data) {
        this.scheduler = scheduler;
        this.database = database;
        this.owner = owner.name;
        this.players = Collections.synchronizedSet(new HashSet<>(5));
        this.maxRounds = data.maxRounds;

        this.playing = false;
        this.practice = data.practice;
        this.mode = Mode.fromMode(data.mode);
        this.categories = Collections.unmodifiableList(data.categories);

        this.players.add(owner);
        this.points.put(owner.name, 0);
    }

    public synchronized void leave(String username) {
        players.removeIf(user -> user.name.equals(username));
        points.remove(username);

        if (username.equals(owner) && !this.players.isEmpty())
            owner = players.iterator().next().name;

        endRound(null, true);
    }

    public synchronized boolean join(User user) {
        if (playing || practice) return false;

        players.add(user);
        points.put(user.name, 0);
        return true;
    }

    public synchronized void start() {
        try {
            entries.clear();
            entries.addAll(database.loadEntries(categories, mode));
            Collections.shuffle(entries);
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed loading entries", ex);
        }

        playing = true;
        startNewRound();
    }

    public synchronized void stop() {
        endRound(null, false);
        playing = false;
        entries.clear();

        dispatchObj(Packet.Type.EVENT_STOP, "");
    }

    private void startNewRound() {
        if (nextRoundFuture != null) {
            nextRoundFuture.cancel(false);
            nextRoundFuture = null;
        }

        if (maxRounds <= roundIdCounter) {
            stop();
            return;
        }

        int roundId = ++roundIdCounter;

        Long entry = entries.poll();
        if (entry == null) {
            stop();
            return;
        }

        byte[] entryBytes;
        try {
            currentRound = new Round(roundId, database.loadSolution(entry));
            entryBytes = database.loadEntry(entry);
            if (entryBytes == null)
                throw new IllegalStateException("Failed loading entry.");
        } catch (SQLException | IOException ex) {
            throw new IllegalStateException("Failed loading solution or entry.", ex);
        }

        dispatchObj(Packet.Type.EVENT_START_ROUND, new StartRoundData(roundId, SUBMISSION_DELAY * 1000, entryBytes));
        endRoundFuture = scheduler.schedule(() -> endRound(null, true), ROUND_MAX_TIME + SUBMISSION_DELAY, TimeUnit.SECONDS);
    }

    public boolean submitSolution(User user, int roundId, String solution) {
        if (currentRound == null || currentRound.id != roundId || !currentRound.canSubmit(user))
            return false;

        if (currentRound.isSolution(solution)) {
            endRound(user, true);
        } else {
            currentRound.canSubmit.remove(user.name);
            if (currentRound.canSubmit.isEmpty())
                endRound(null, true);
        }

        return true;
    }

    private void endRound(User userWinner, boolean next) {
        if (endRoundFuture != null) {
            endRoundFuture.cancel(false);
            endRoundFuture = null;
        }
        if (nextRoundFuture != null) {
            nextRoundFuture.cancel(false);
            nextRoundFuture = null;
        }

        String winner;
        if (userWinner == null) {
            winner = null;
        } else {
            incrementPoints(userWinner.name);
            winner = userWinner.name;
        }

        dispatchObj(Packet.Type.EVENT_END_ROUND, new EndRoundData(winner, points));
        currentRound = null;

        if (next)
            nextRoundFuture = scheduler.schedule(this::startNewRound, NEXT_ROUND_DELAY, TimeUnit.SECONDS);
    }

    private void incrementPoints(String username) {
        int val = points.getOrDefault(username, 0);
        val++;
        points.put(username, val);
    }

    private void dispatchObj(Packet.Type type, Object obj) {
        for (User player : new ArrayList<>(players)) {
            try {
                player.client.sendObj(type, Packet.Status.OK, obj);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public GameInfo toGameInfo() {
        return new GameInfo(owner, !playing && !practice);
    }

    public GameData toGameData() {
        return new GameData(toGameInfo(), playing, mode.toMode(), points);
    }

    public enum Mode {
        IMAGE, SONG;

        static Mode fromMode(GameData.Mode mode) {
            switch (mode) {
                case SONG:
                    return SONG;
                case IMAGE:
                    return IMAGE;
                default:
                    throw new IllegalArgumentException(String.valueOf(mode));
            }
        }

        GameData.Mode toMode() {
            switch (this) {
                case SONG:
                    return GameData.Mode.SONG;
                case IMAGE:
                    return GameData.Mode.IMAGE;
                default:
                    throw new IllegalArgumentException(String.valueOf(this));
            }
        }
    }

    private class Round {
        private final int id;
        private final String solution;
        private final long minSubmissionTime;
        private final Set<String> canSubmit = new HashSet<>(5);

        Round(int id, String solution) {
            this.id = id;
            this.solution = solution;
            this.minSubmissionTime = System.currentTimeMillis() + SUBMISSION_DELAY * 1000;
            this.canSubmit.addAll(players.stream().map(user -> user.name).collect(Collectors.toList()));
        }

        boolean isSolution(String val) {
            return solution.equalsIgnoreCase(val);
        }

        public boolean canSubmit(User user) {
            return System.currentTimeMillis() > minSubmissionTime && canSubmit.contains(user.name);
        }
    }
}
