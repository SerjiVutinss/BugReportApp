package org.serji.sw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class RequestHandler extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean keepAlive = true;

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		System.out.println("[SERVER]> Received connection from " + socket.getInetAddress() + ":" + socket.getPort());

		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			listen();
		} catch (IOException e) {
			close();
		}

	}

	private void listen() {
		if (socket.isConnected()) {
			MainMenu mainMenu = new MainMenu(this);
			mainMenu.login();
			while (keepAlive) {
				if (socket.isConnected()) {
					mainMenu.run();
				} else {
					close();
					this.keepAlive = false;
				}
			}
		}
	}

	public String getMessage() {
		String msg = null;
		try {
			msg = (String) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			keepAlive = false;
			close();
		}
		return msg;
	}

	public void sendMessage(String message) {
		try {
			Date dt = new Date();
			String dtFormat = dt.toGMTString();
			out.writeObject("[" + dtFormat + "][SERVER]> " + message);
			out.flush();
		} catch (IOException e) {
			close();
		}
	}

	public void close() {
		try {
//			this.in.close();
//			this.out.close();
			this.socket.close();
			if (this.socket.isClosed()) {
				System.out.println(
						"[SERVER]> Connection to: " + socket.getInetAddress() + ":" + socket.getPort() + " closed");

			}
			this.socket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
