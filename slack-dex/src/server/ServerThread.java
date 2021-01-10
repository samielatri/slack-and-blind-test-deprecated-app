package server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import database.DAO;
import database.DAOFactory;

import model.user.Profile;
import model.user.User;

import model.communication.Message;
import model.communication.WorkspaceChannel;

import model.communication.Message;
import model.communication.Workspace;
import model.user.User;


public class ServerThread extends Thread {

    private Socket socket;
    private Server server;
    private ObjectOutputStream output = null;
    private boolean onetime = true;
    private ObjectInputStream input = null;
    public ServerUser currentServerUser = null;
    public WorkspaceChannel channel;
    private DAO<Profile> DAOProfile = DAOFactory.profile();
    private DAO<WorkspaceChannel> DAOChannel = DAOFactory.workspaceChannel();

    public ServerThread(Socket socket, Server server) throws SQLException {
        this.socket = socket;
        this.server = server;
    }
/*
    public void run() {

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!(socket.isClosed())) {
            try {

                if (onetime) {
                    Message message = (Message) input.readObject();
                    currentServerUser = new ServerUser(socket,DAOProfile.select(message.getIdSenderMessage()));
                    onetime = false;
                    server.addUser(currentServerUser);
                    System.out.println(message.toString());
                } else {
                    Message received = (Message) input.readObject();
                    channel = DAOChannel.select(received.getIdCh());
                    System.out.println(received.toString());
                    if (!received.getContent().equals("")) {
                        server.broadcast(received, this);
                    }
                }
            } catch (IOException | ClassNotFoundException | SQLException ex) {
                System.out.println("server " + ex.getMessage());
            }
        }

    }
*/
    /**
     * Sends a message to the client.
     *
     * @throws IOException
     */
    void sendMessage(String message) throws IOException {
        Message msg = new Message(currentServerUser.getProfile(),message);
        output.writeObject(msg);
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