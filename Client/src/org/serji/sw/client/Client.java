package org.serji.sw.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

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

		Scanner scanner = new Scanner(System.in);
		String host;
		int port;

		System.out.println("Please enter the server address: ");
		host = scanner.nextLine();
		System.out.println("Please enter the server port: ");
		port = scanner.nextInt();

		// USED FOR TESTING PURPOSES
//		host = "localhost";
//		host = "137.117.224.144";
//		port= 8000;

		// hardcode these for testing

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
