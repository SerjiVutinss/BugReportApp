package org.serji.sw.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Thread which listens for server messages, if a message is received, print it
 * to the client console
 * 
 * @author Justin
 *
 */
public class ClientListener extends Thread {

	private Socket socket;

	/**
	 * Constructor
	 * 
	 * @param socket the socket used to create the input stream
	 */
	public ClientListener(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		listen(); // start listening for server messages
	}

	public void listen() {
		// attempt to create an input stream using the socket
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			String message;
			while (true) { // loop infinitely
				if (in.available() == 0) { // if the stream is not available, wait briefly
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try { // stream is available
					message = (String) in.readObject(); // try to read a message from the stream
					printMessage(message); // and print the received message to the client console
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			// stream could not be created or maintained
			System.out.println("[CLIENT]> Lost stream, disconnecting");
		}
	}

	public static void printMessage(String message) {
		System.out.println(message);
	}
}
