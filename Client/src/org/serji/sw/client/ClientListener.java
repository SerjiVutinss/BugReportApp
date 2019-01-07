package org.serji.sw.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListener extends Thread {

	private Socket socket;

	public ClientListener(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		listen();
	}

	public void listen() {
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			String message;
			while (true) {
				if (in.available() == 0) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					message = (String) in.readObject();
					printMessage(message);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			System.out.println("[CLIENT]> Lost stream, disconnecting");
		}
	}

	public static void printMessage(String message) {
		System.out.println();
		System.out.println("######## SERVER MESSAGE #######");
		System.out.println(message);
		System.out.println();
	}
}
