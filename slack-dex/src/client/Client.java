package src.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


//import javafx.scene.control.TextArea;
import model.communication.Message;
import model.communication.Message;

public class Client implements Runnable {

    // User defined Fields
    private String host = "localhost";
    private int port = 62000;
    private Message message;

    // For Socket Connection
    Socket socket;
    ObjectInputStream input = null;
    ObjectOutputStream output = null;

    private static ArrayList<Message> messages = new ArrayList<>();

    public Client(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        try {
            InetAddress ip = InetAddress.getByName(host);

            socket = new Socket(ip, port);

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            output.writeObject(message);
            resetStream();

            while (!(socket.isClosed())) {
                Message message = (Message) input.readObject();
                messages.add(message);
                System.out.println(message.toString());
            }

//			closeConnection();

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("client" + ex.getMessage());

        }
    }

    public void closeConnection() {
        try {
            socket.close();
            output.close();
            input.close();
            System.out.println("Client Closed Connection");

        } catch (IOException ex) {
            System.out.println("client" + ex.getMessage());
        }
    }

    public void sendMessage(Message message) {

        try {
            output.writeObject(message);
            resetStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
