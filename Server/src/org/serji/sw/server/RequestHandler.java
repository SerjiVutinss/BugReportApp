package org.serji.sw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler extends Thread {

	private Socket socket;
	private boolean keepAlive = true;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		System.out.println("[SERVER]> Received connection from " + socket.getInetAddress() + ":" + socket.getPort());

		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (keepAlive) {

			try {
				if (socket.isConnected()) {
					String message = null;
					message = (String) in.readObject();

					System.out.println("[SERVER]> Message recieved from client: ");
					System.out.println("[SERVER]> Sending Response");
					out.writeObject(message.toUpperCase());
					out.flush();
				} else {
					keepAlive = false;
				}
			} catch (IOException | ClassNotFoundException e) {
				keepAlive = false;
				System.out.println("[SERVER]> Connection to client was lost");
			}

		}

		try {
			socket.close();
			System.out.println(
					"[SERVER]> Connection to: " + socket.getInetAddress() + ":" + socket.getPort() + " closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
