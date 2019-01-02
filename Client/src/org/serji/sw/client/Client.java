package org.serji.sw.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class Client {

	private Socket socket;
//	private ObjectInputStream in;
//	private ObjectOutputStream out;

	public Client() {
		System.out.println("[CLIENT]> Welcome to TCP Client");

		socket = null;
		String host = "localhost";

		int port = 8000;
		System.out.println("[CLIENT]> Starting Client");
		try {
			System.out.println(new Date().toGMTString() + "[CLIENT]> Connecting to " + host + ":" + port);
			socket = new Socket(host, port);
			System.out.println("[CLIENT]> Connected");
		} catch (IOException e) {
			System.out.println("[CLIENT]> Server not found");
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
