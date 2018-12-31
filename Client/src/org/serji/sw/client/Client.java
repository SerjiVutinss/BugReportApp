package org.serji.sw.client;

import java.io.IOException;
import java.net.Socket;

public class Client {

	private Socket socket;
//	private ObjectInputStream in;
//	private ObjectOutputStream out;

	public Client() {
		System.out.println("Welcome to TCP Client");

		socket = null;
		String host = "localhost";

		int port = 8000;
		System.out.println("Starting Client");
		try {
			System.out.println("Connecting to " + host + ":" + port);
			socket = new Socket(host, port);
		} catch (IOException e) {
			System.out.println("Server not found");
		}

		Thread listener = new ClientListener(socket);
		Thread input = new ClientInput(socket);
		input.start();
		listener.start();
	}

	public static void main(String[] args) {
		new Client();
	}
}
