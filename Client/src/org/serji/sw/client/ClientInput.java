package org.serji.sw.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client input thread
 * 
 * @author Justin
 *
 */
public class ClientInput extends Thread {

	private Socket socket; // socket to connect to
	private boolean keepAlive = true; // sentinel
	private ObjectOutputStream out; // out put stream we will write to

	/**
	 * Constructor
	 * 
	 * 
	 * @param socket the socket which will be used to create the output stream
	 */
	public ClientInput(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		listen(); // start listening for client input
	}

	public void listen() {
		try {
			// create an object output stream using the socket
			out = new ObjectOutputStream(socket.getOutputStream());

			Scanner s = new Scanner(System.in); // scanner to get user input
			String message; // variable to store the

			// loop while input != "Y" - this is only used for testing
			while (keepAlive) {
				message = s.nextLine(); // get the next line from the scanner
				if (message.equals("Y")) { // break out of loop
					keepAlive = false;
					System.out.println("Finish Input");
					break;
				} else {
					sendMessage(message); // send a message to the server
				}
			}
			s.close();
		} catch (IOException e) {
			// exception caught, inform the user
			System.out.println("Could not get streams");
		}
	}

	/**
	 * Sends a message of type string to the server
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {

		// attempt to write to the stream
		try {
			out.writeObject(message); // write
			out.flush(); // flush the buffer
		} catch (IOException e) {
			// inform the user an exception has been caught
			System.out.println("Could not write");
		}

	}
}
