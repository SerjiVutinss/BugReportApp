package org.serji.sw.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Main application class
 * 
 * @author Justin
 *
 */
public class Client {

	private Socket socket;

	/**
	 * Constructor
	 * 
	 * 
	 * 
	 */
	public Client() {
		System.out.println("[CLIENT]> Welcome to TCP Client");

		socket = null;

		// hardcode these for testing
		String host = "localhost";
		int port = 8000;

		System.out.println("[CLIENT]> Starting Client");
		// attempt opening a socket connection to the server
		try {
			System.out.println(Utils.dateToString(new Date()) + "[CLIENT]> Connecting to " + host + ":" + port);
			socket = new Socket(host, port);
			System.out.println("[CLIENT]> Connected");
		} catch (IOException e) {
			System.out.println("[CLIENT]> Server not found");
		}

		// create and start the listener and input threads
		Thread listener = new ClientListener(socket);
		listener.start();
		Thread input = new ClientInput(socket);
		input.start();
	}

	public static void main(String[] args) {
		new Client();
	}
}
