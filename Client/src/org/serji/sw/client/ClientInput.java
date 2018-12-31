package org.serji.sw.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientInput extends Thread {

	private Socket socket;

	private boolean keepAlive = true;

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
			while (keepAlive) {
				System.out.println("Press a key to send a message");
				message = s.next();

				if (message.equals("Y")) {
					keepAlive = false;
					System.out.println("Finish Input");
					break;
				} else {
					out.writeObject(message);
					out.flush();
				}
			}
			s.close();
		} catch (IOException e) {
			System.out.println("Could not get streams");
		}
	}

}
