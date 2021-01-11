package Server;

import java.io.*;
import java.net.*;
import java.util.*;

import Model.Message;
import Model.User;
import Model.Workspace;

public class ServerThread extends Thread {

	private Socket socket;
	private Server server;
	private ObjectOutputStream output = null;
	private boolean onetime = true;
	private ObjectInputStream input = null;
	public User currentUser = null;
	public Workspace workspace;

	public ServerThread(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

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
					currentUser = message.getSender();
					onetime = false;
					server.addUser(currentUser);
					System.out.println(message.toString());
				} else {
					Message received = (Message) input.readObject();

					Workspace newworkspace = received.getWorkspace();
					workspace = newworkspace;
					System.out.println(received.toString());
					if (!received.getContent().equals("")) {
						server.broadcast(received, this);
					}
				}
			} catch (IOException | ClassNotFoundException ex) {
				System.out.println("Server " + ex.getMessage());
			}

		}

	}

	void sendMessage(Message message) throws IOException {
		output.writeObject(message);
		resetStream();
	}

	public void resetStream() {
		try {
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}