
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Public computer that everyone can access
 */
public class Server1 extends JFrame {

    private JTextField userText; // user text filed
    private JTextArea chatWindow; // overall chat window
    private ObjectOutputStream output; // message coming out
    private ObjectInputStream input; // message coming in
    private ServerSocket server; // server configuration
    private Socket connection; // connection between two computers

    // constructor
    public Server1(){
        super("Serveur");
        userText = new JTextField();
        userText.setEditable(false); // if no connection is established, the user is not allowed to text
        userText.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    sendMessage(event.getActionCommand()); // send message
                    userText.setText(""); // resetting to blank after message sent
                }
            }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane());
        setSize(300,150);
        setVisible(true);
    }

    //Set up and run the server
    public void startRunning(){
        try{
            server = new ServerSocket(6789, 100);
            while(true){
                try{
                    // connect and have conversation
                    waitForConnection(); // wait for connection
                    setupStreams(); // set up streams
                    whileChatting(); // send messages
                }catch(EOFException eofException){ // end of connection
                    showMessage("\n Server ended the connection! ");
                }finally {
                    closeCrap(); // close sockets
                }
            }
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    //wait for connection, then diplay connection information*
    private void waitForConnection() throws IOException{
        showMessage("Waiting for someone to connect... \n");
        connection = server.accept(); // setting up the socket with acceptiong the connection to the socket
        showMessage(" Now connect to " + connection.getInetAddress().getHostName());
    }

    //get stream to send and receive data
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream()); // pathway to the computer connected to
        output.flush(); // flush data left over
        input = new ObjectInputStream(connection.getInputStream());
        // no flush because they flush not us
        showMessage("\n Streamsare now setup! \n");
    }

    //during the chat conversation
    private void whileChatting() throws IOException {
        String message =" You are now connected! ";
        sendMessage(message);
        ableToType(true); // let user type into text box
        do{
            // have a conversation
            try{
                message = (String) input.readObject();
            }catch(ClassNotFoundException classNotFoundException){
                showMessage("\n not knowing what the user send");
            }
        }while(!message.equals("CLIENT - END")); // message END to end
    }

    // Close streams and sockets after your are done chatting
    private void closeCrap(){
        showMessage("\n Closing connection.. \n");
        ableToType(false);
        try{
            output.close();
            input.close();
            connection.close();
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    // send a message to client
    private void sendMessage(String message){
        try{
            output.writeObject("SERVER - " + message);
            output.flush();
            // display message to you for history of messages
            showMessage("\nSERVER - " + message);

        }catch(IOException ioException){
            chatWindow.append("\n Error can't send message");
        }
    }

    // updates chatWindow
    private void showMessage(final String text){
        SwingUtilities.invokeLater(
                // create a thread to update a GUI to not close the GUI and update it
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append(text); // add message to end of document
                    }
                }
        );
    } // end of showMessage

    // let the user type stuff into their box
    private void ableToType(final boolean tof){
        SwingUtilities.invokeLater(
                // create a thread to update a GUI to not close the GUI and update it
                new Runnable() {
                    @Override
                    public void run() {
                        userText.setEditable(tof); // can type or not
                    }
                }
        );
    } // end of ableToType
} // end of Server
