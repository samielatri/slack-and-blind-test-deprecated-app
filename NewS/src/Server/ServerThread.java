package Server;

import java.io.*;
import java.net.*;
import java.util.*;

import Model.User;

/**
 * This thread handles connection for each connected client, so the server can
 * handle multiple clients at the same time.
 */
public class ServerThread extends Thread {

	private Socket socket;
	private Server server;
	ObjectOutputStream output = null;
	boolean onetime = true;
	ObjectInputStream input = null;
	User currentUser = null;

	public ServerThread(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

	public void run() {

		while (!(socket.isClosed())) {
			try {
				
				output = new ObjectOutputStream(socket.getOutputStream());

				input = new ObjectInputStream(socket.getInputStream());


				if (onetime) {
					currentUser = (User) input.readObject();
					onetime = false;
					server.addUser(currentUser);
//					String serverMessage = username + " has connected";
//					server.broadcast(serverMessage, this);
//					greetingMessage();
					System.out.println(currentUser.toString() + " has Connected.");
					System.out.println("IF ");
				}
//				else {
//
//					received = input.readUTF();
//
//					server.broadcast(received, this);
//
//				}
			} catch (IOException | ClassNotFoundException ex) {
				System.out.println("Server " + ex.getMessage());
			}

		}

	}

	/**
	 * Sends a message to the client.
	 * 
	 * @throws IOException
	 */
	void sendMessage(String message) throws IOException {
		output.writeUTF(message);
	}

}