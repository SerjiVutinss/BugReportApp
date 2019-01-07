package org.serji.sw.server.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import org.serji.sw.server.Utils;
import org.serji.sw.server.menus.MainMenu;

public class RequestHandler extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean keepAlive = true;
	private MainMenu mainMenu;

	public MainMenu getMainMenu() {
		return this.mainMenu;
	}

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
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
			mainMenu = new MainMenu(this);
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
//			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
//			Date dt = new Date();

			String strDate = Utils.dateToString(new Date());
			out.writeObject("\n[" + strDate + "][SERVER]> " + message);
			out.flush();
		} catch (IOException e) {
			close();
		}
	}

	public void sendMessage(StringBuilder message) {

		String[] lines = message.toString().split("\\n");
		for (String s : lines) {
			sendMessage(s);
		}
	}

	public void close() {
		try {
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
