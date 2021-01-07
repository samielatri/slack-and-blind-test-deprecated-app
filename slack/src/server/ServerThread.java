package server;

import java.io.*;
import java.net.*;

import model.Message;
import model.User;
import model.Workspace;


public class ServerThread extends Thread {

    private Socket socket;
    private Server.Server server;
    private ObjectOutputStream output = null;
    private boolean onetime = true;
    private ObjectInputStream input = null;
    public User currentUser = null;
    public Workspace workspace;

    public ServerThread(Socket socket, Server.Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while (!(socket.isClosed())) {
            try {

                if (onetime) {
                    Message message = (Message) input.readObject();
                    currentUser = message.getSender();
                    onetime = false;
                    server.addUser(currentUser);
                    System.out.println(message.toString());
                } else {
                    Message received = (Message) input.readObject();

                    workspace = received.getWorkspace();
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

    public void resetStream() {
        try {
            output.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}