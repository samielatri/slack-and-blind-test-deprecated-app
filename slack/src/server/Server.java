package server;

import model.communication.Message;
import model.communication.WorkspaceChannel;
import model.user.Profile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {

    private final Set<Profile> profileList = new HashSet<>();
    private final Set<ServerThread> profileThreads = new HashSet<>();

    static int defaultPort = 62000;
    static int serverPort;

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            int i = 1;

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Inbound Connection #" + i);
                ServerThread newUser = new ServerThread(socket, this);
                profileThreads.add(newUser);
                newUser.start();
                i++;
            }

        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            serverPort = defaultPort;
            System.out.println("Using default port: " + defaultPort);
        } else {
            try {
                serverPort = Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
                serverPort = defaultPort;
                System.out.println("Error: Invalid port number: " + args[0]);
                System.out.println("Using default port: " + defaultPort);
            }
        }

        Server server = new Server();
        server.execute();
    }

    /**
     * Delivers a message from one profile to others (broadcasting)
     *
     * @param channel
     *
     * @throws IOException
     */
    void broadcast(Message message, ServerThread excludeProfile) throws IOException {
        for (ServerThread profile : profileThreads) {
            if (profile.getWorkspace.getChannel() != null) {
                WorkspaceChannel channel = profile.workspace();
                if (message.getWorkspaceChannel().equals(channel)) {
                    profile.sendMessage(message);
                }
            }

        }
    }

    /**
     * Stores username of the newly connected client.
     * @param profile
     */
    void addProfile(Profile profile) {
        profileList.add(profile);
    }

}