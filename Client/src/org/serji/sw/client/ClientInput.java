package org.serji.sw.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientInput extends Thread {

	private Socket socket;

	public ClientInput(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		listen();
	}

	public void listen() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

			Scanner s = new Scanner(System.in);
			String message;
			while (true) {
				System.out.println("Press a key to send a message");
				message = s.next();
				out.writeObject(message);
				out.flush();

			}
		} catch (IOException e) {
			System.out.println("Could not get streams");
		}
	}

}
