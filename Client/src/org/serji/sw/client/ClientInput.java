package org.serji.sw.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientInput extends Thread {

	private Socket socket;

	private boolean keepAlive = true;

	private ObjectOutputStream out;

	public ClientInput(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		listen();
	}

	public void listen() {
		try {
			out = new ObjectOutputStream(socket.getOutputStream());

			Scanner s = new Scanner(System.in);
			String message;
			while (keepAlive) {
				message = s.nextLine();
				if (message.equals("Y")) {
					keepAlive = false;
					System.out.println("Finish Input");
					break;
				} else {
					sendMessage(message);
				}
			}
			s.close();
		} catch (IOException e) {
			System.out.println("Could not get streams");
		}
	}

	public void sendMessage(String message) {

		try {
			out.writeObject(message);
			out.flush();
		} catch (IOException e) {
			System.out.println("Could not write");
		}

	}

	public void mainMenu() {
	}

}
