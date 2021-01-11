package Server;

import java.io.*;
import java.net.*;
import java.util.*;

import Model.Message;
import Model.User;

public class Server {

	private Set<User> userlist = new HashSet<>();
	private Set<ServerThread> userThreads = new HashSet<>();
	static int defaultport = 62000;
	static int serverPort;

	public void execute() {
		try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
			int i = 1;

			while (true) {
				Socket socket = serverSocket.accept();

				System.out.println("Inbound Connection #" + i);
				// create a new User
				ServerThread newUser = new ServerThread(socket, this);
				// add new User to list
				userThreads.add(newUser);

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
			serverPort = defaultport;
			System.out.println("Using default port: " + defaultport);
		} else {
			try {
				serverPort = Integer.parseInt(args[0]);
			} catch (NumberFormatException nfe) {
				serverPort = defaultport;
				System.out.println("Error: Invalid port number: " + args[0]);
				System.out.println("Using default port: " + defaultport);
			}
		}

		Server server = new Server();
		server.execute();
	}


	void broadcast(Message message, ServerThread excludeUser) throws IOException {
		for (ServerThread user : userThreads) {
			System.out.println("Channel: " + user.workspace.getChannel());

			if (user.workspace.getChannel() != null) {
				String channel = user.workspace.getChannel();
				if (message.getWorkspace().getChannel().equals(channel)) {
					user.sendMessage(message);
				}
			}

		}
	}

	void addUser(User user) {
		userlist.add(user);
	}

//	void remove(String userName, ServerThread aUser) {
//		boolean removed = userNames.remove(userName);
//		if (removed) {
//			userThreads.remove(aUser);
//		}
//	}

//	Set<String> getUserNames() {
//		return this.userNames;
//	}


//	boolean hasUsers() {
//		return !this.userNames.isEmpty();
//	}

}