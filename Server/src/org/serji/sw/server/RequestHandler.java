package org.serji.sw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler extends Thread {

	private Socket socket;
	private String message;
	private boolean keepAlive = true;

	public RequestHandler(Socket socket, String message) {
		this.socket = socket;
		this.message = message;
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
					System.out.println(
							"[SERVER]> Closing connection to: " + socket.getInetAddress() + ":" + socket.getPort());
				}

			} catch (IOException | ClassNotFoundException e) {
				System.out.println("[SERVER]> Connection to client was lost");
				keepAlive = false;
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
