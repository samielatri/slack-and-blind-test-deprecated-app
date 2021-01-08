package server;

import model.communication.Message;
import model.communication.WorkspaceChannel;
import model.user.Profile;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 */
public class ServerThread extends Thread {

    private final Socket socket;
    private final Server server;
    private ObjectOutputStream output = null;
    private boolean onetime = true;
    private ObjectInputStream input = null;
    public Profile currentProfile = null;
    public WorkspaceChannel workspaceChannel = null;

    /**
     *
     * @param socket
     * @param server
     */
    public ServerThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     *
     */
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        while (!(socket.isClosed())) {
            try {
                if (onetime) {
                    Message message = (Message) input.readObject();

                    currentProfile = message.getSender();
                    onetime = false;
                    server.addProfile(currentProfile);
                    System.out.println(message.toString());
                } else {
                    Message received = (Message) input.readObject();

                    workspaceChannel = received.getWorkspaceChannel();
                    System.out.println(received.toString());
                    if (!received.getContent().equals("")) {
                        server.broadcast(received, this);
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("server " + ex.getMessage());
            }

        }

    }

    /**
     * Sends a message to the client.
     *
     * @throws IOException
     */
    void sendMessage(Message message) throws IOException {
        output.writeObject(message);
        resetStream();
    }

    /**
     *
     */
    public void resetStream() {
        try {
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}