package server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;

import src.model.communication.Message;

public class Server {

    private Set<ServerUser> userlist = new HashSet<>();
    private Set<ServerThread> userThreads = new HashSet<>();
    static int defaultport = 62000;
    static int serverPort;

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            int i = 1;

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Inbound Connection #" + i);
                ServerThread newUser = new ServerThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
                i++;
            }

        } catch (IOException | SQLException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            serverPort = defaultport;
            System.out.println("Using default port: " + defaultport);
        } else {
            try {
                serverPort = Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
                serverPort = defaultport;
                System.out.println("Error: Invalid port number: " + args[0]);
                System.out.println("Using default port: " + defaultport);
            }
        }

        Server server = new Server();
        server.execute();
    }

    /**
     * Delivers a message from one user to others (broadcasting)
     *
     //* @param channel
     *
     * @throws IOException
     */
    /*void broadcast(Message message, ServerThread excludeUser) throws IOException {
        for (ServerThread user : userThreads) {
            if (user.getWS.getChannel() != null) {
                String channel = user.workspace.getChannel();
                if (message.getWorkspace().getChannel().equals(channel)) {
                    user.sendMessage(message.getContent());
                }
            }

        }
    }*/

    /**
     * Stores username of the newly connected client.
     */
    void addUser(ServerUser user) {
        userlist.add(user);
    }

    /**
     * When a client is disconneted, removes the associated username and UserThread
     */
//	void remove(String userName, ServerThread aUser) {
//		boolean removed = userNames.remove(userName);
//		if (removed) {
//			userThreads.remove(aUser);
//		}
//	}

//	Set<String> getUserNames() {
//		return this.userNames;
//	}

    /**
     * Returns true if there are other users connected (not count the currently
     * connected user)
     */
//	boolean hasUsers() {
//		return !this.userNames.isEmpty();
//	}

}