package com.music.blind_test.server.game;

import com.music.blind_test.CreateGameData;
import com.music.blind_test.GameInfo;
import com.music.blind_test.Packet;
import com.music.blind_test.SubmitSolutionData;
import com.music.blind_test.server.ClientHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Games implements Users.PacketsListener {
    private final List<Game> games = new ArrayList<>(20);
    private final Database database;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Games(Database database) {
        this.database = database;
    }

    private boolean createGame(User user, CreateGameData data) {
        if (user.currentGame != null) return false;
        games.add(user.currentGame = new Game(scheduler, database, user, data));
        return true;
    }

    private boolean joinGame(User user, String game) {
        if (user.currentGame != null) return false;

        for (Game g : games) {
            if (g.owner.equals(game)) {
                if (g.join(user)) {
                    user.currentGame = g;
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    private void leaveGame(User user) {
        if (user.currentGame == null) return;

        Game game = user.currentGame;
        user.currentGame = null;

        game.leave(user.name);
        if (game.players.isEmpty()) games.remove(game);
    }

    private boolean startGame(User user) {
        Game game = user.currentGame;
        if (game == null || !game.owner.equals(user.name))
            return false;

        if (game.playing)
            return true;

        game.start();
        return true;
    }

    private boolean stopGame(User user) {
        Game game = user.currentGame;
        if (game == null || !game.owner.equals(user.name))
            return false;

        if (!game.playing)
            return true;

        game.stop();
        return true;
    }

    private boolean submitSolution(User user, SubmitSolutionData data) {
        if (user.currentGame == null) return false;
        return user.currentGame.submitSolution(user, data.roundId, data.solution);
    }

    @Override
    public void onPacket(ClientHandler client, User user, Packet.Type type, byte[] body) throws IOException, ClassNotFoundException {
        switch (type) {
            case GAME_DATA:
                if (user.currentGame == null)
                    client.send(Packet.Type.CREATE_GAME, Packet.Status.FAILED_GAME_DATA);
                else
                    client.sendObj(Packet.Type.GAME_DATA, Packet.Status.OK, user.currentGame.toGameData());
                break;
            case GAME_LIST:
                GameInfo[] gamesArray = games.stream().map(Game::toGameInfo).toArray(GameInfo[]::new);
                client.sendObj(Packet.Type.GAME_LIST, Packet.Status.OK, gamesArray);
                break;
            case CREATE_GAME:
                if (createGame(user, CreateGameData.parse(body)))
                    client.send(Packet.Type.CREATE_GAME, Packet.Status.OK);
                else
                    client.send(Packet.Type.CREATE_GAME, Packet.Status.FAILED_CREATE_GAME);
                break;
            case JOIN_GAME:
                String game = new String(body);
                if (joinGame(user, game)) client.send(Packet.Type.JOIN_GAME, Packet.Status.OK);
                else client.send(Packet.Type.JOIN_GAME, Packet.Status.FAILED_JOIN_GAME);
                break;
            case LEAVE_GAME:
                leaveGame(user);
                client.send(Packet.Type.LEAVE_GAME, Packet.Status.OK);
                break;
            case START_GAME:
                if (startGame(user)) client.send(Packet.Type.START_GAME, Packet.Status.OK);
                else client.send(Packet.Type.JOIN_GAME, Packet.Status.FAILED_START_GAME);
                break;
            case STOP_GAME:
                if (stopGame(user)) client.send(Packet.Type.STOP_GAME, Packet.Status.OK);
                else client.send(Packet.Type.STOP_GAME, Packet.Status.FAILED_STOP_GAME);
                break;
            case SUBMIT_ROUND_SOLUTION:
                if (submitSolution(user, SubmitSolutionData.parse(body)))
                    client.send(Packet.Type.SUBMIT_ROUND_SOLUTION, Packet.Status.OK);
                else
                    client.send(Packet.Type.SUBMIT_ROUND_SOLUTION, Packet.Status.FAILED_SUBMIT_SOLUTION);
            default:
                database.onPacket(client, user, type, body);
                break;
        }
    }
}
