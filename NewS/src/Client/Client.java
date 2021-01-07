package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Model.User;

public class Client {

	// User defined Fields
	private String host = "localhost";
	private int port = 62000;
	private User user;

	// For Socket Connection
	Socket socket;
	ObjectInputStream input = null;
	ObjectOutputStream output = null;

	public Client(User user) {
		this.user = user;
	}


	public void setConnection() {
		try {
			InetAddress ip = InetAddress.getByName(host);

			socket = new Socket(ip, port);

			output = new ObjectOutputStream(socket.getOutputStream());

			input = new ObjectInputStream(socket.getInputStream());

			output.writeObject(user);
			
			output.flush();
			
			System.out.println("Client  ");

			while (!(socket.isClosed())) {
				
			
			}
			
//			closeConnection();

		} catch (IOException ex) {
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

	public void handleMessage() {

//			String tosend = input.getText();

//			out.writeUTF(name + " " + tosend);

	}

}
