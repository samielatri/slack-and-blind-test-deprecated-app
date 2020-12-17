package Server;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// home computer
public class Client extends JFrame {
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output; // client to server
    private ObjectInputStream input; // server to client
    private String message = "";
    private String serverIP;
    private Socket connection;

    // constructor
    public Client(String host) {
        super("Client message app!");
        serverIP = host;
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        sendMessage(event.getActionCommand());
                        userText.setText("");
                    }
                }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(300, 150);
        setVisible(true);
    }

    // connect to server
    public void startRunning() {
        try {
            connectToServer(); // no wait -> connect to direct computer
            setupStreams();
            whileChatting();
        } catch (EOFException eofException) {
            showMessage("\n Client terminated connection");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            closeCrap();
        }
    }

    //connect to server
    private void connectToServer() throws IOException {
        showMessage("Attempting connection...\n");
        // creating a socket this computer with the other
        connection = new Socket(InetAddress.getByName(serverIP), 6789);
        showMessage("Connected to:" + connection.getInetAddress().getHostName());
    }

    //set up streams to send and receive messages
    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n message ready to go");
    }

    //while chatting with server
    private void whileChatting() throws IOException {
        ableToType(true);
        do {
            try {
                message = (String) input.readObject();
                showMessage("\n" + message);
            } catch (ClassNotFoundException classNotFoundException) {
                showMessage("\n unknown object type");
            }
        } while (!message.equals("SERVER - END"));
    }

    //close the streams and sockets
    private void closeCrap() {
        showMessage("\n closing crap down...");
        ableToType(false);
        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    //send messages to Server
    private void sendMessage(String message) {
        try {
            output.writeObject("CLIENT - " + message);
            output.flush();
            showMessage("\nCLIENT - " + message);
        } catch (IOException ioException) {
            chatWindow.append(("\n error while sending client message"));
        }
    }

    //change or update chatWindow
    private void showMessage(final String m) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    chatWindow.append(m);
                }
            }
        );
    }

    //gives user permission to type into the text box
    private void ableToType(final boolean tof) {
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run() {
                    userText.setEditable(tof);
                }
            }
        );
    } // end of ableToType
} // end of Client