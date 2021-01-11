package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import Controllers.ChatController;
import Model.Message;
import javafx.scene.control.TextArea;

public class Client implements Runnable {

	// User defined Fields
	private String host = "localhost";
	private int port = 62000;
	private Message message;

	// For Socket Connection
	Socket socket;
	ObjectInputStream input = null;
	ObjectOutputStream output = null;

	TextArea M_output;
	private static ArrayList<Message> messages = new ArrayList<>();

	public Client(Message message, TextArea M_output) {
		this.message = message;
		this.M_output = M_output;
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
				ChatController.setMessages(messages);
				M_output.appendText(message.getSender().getProfile().getShownName() + ":" + message.getContent() + "\n");
				System.out.println(message.toString());
			}

			closeConnection();

		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Client" + ex.getMessage());

		}
	}

	public void closeConnection() {
		try {
			socket.close();
			output.close();
			input.close();
			System.out.println("Client Closed Connection");

		} catch (IOException ex) {
			System.out.println("Client" + ex.getMessage());
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
			e.printStackTrace();
		}
	}

}
