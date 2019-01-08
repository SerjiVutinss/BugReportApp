package org.serji.sw.server.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import org.serji.sw.server.Utils;
import org.serji.sw.server.menus.MainMenu;

/**
 * Client thread class which is started for each connected client
 * 
 * A MainMenu object is created and attached to this class
 * 
 * @author Justin
 *
 */
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

	/**
	 * Create the input and output streams and begin listening for client input
	 * 
	 */
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

	/**
	 * Begin listening for client input through the MainMenu object
	 */
	private void listen() {

		// Only create the MainMenu if the socket client is created and connected
		// TODO: tidy this logic up
		if (socket != null) {
			if (socket.isConnected()) {
				mainMenu = new MainMenu(this);
				while (keepAlive) {
					if (socket.isConnected()) {
						mainMenu.run();
					} else {
						close(); // try to gracefully close the connection
						this.keepAlive = false;
					}
				}
			}
		} else {
			System.out.println("Lost connection to a client");
		}
	}

	/**
	 * Try to get a message from the client
	 * 
	 * @return message received from the client
	 */
	public String getMessage() {
		String msg = null;
		if (socket != null) {
			try {
				msg = (String) in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				keepAlive = false; // something has gone wrong, try to gracefully close the connection
				System.out.println("Client Disconnected");
				close();
			}
		}
		return msg;
	}

	/**
	 * Send a string to the client
	 * 
	 * @param message the String to be sent
	 */
	public void sendMessage(String message) {
		try {
			String strDate = Utils.dateToString(new Date());
			out.writeObject("\n[" + strDate + "][SERVER]> " + message);
			out.flush();
		} catch (IOException e) {
			close();
		}
	}

	/**
	 * Extension method which instead takes in a StringBuilder instead of a String
	 * 
	 * @param message StringBuilder object to e sent to the client as a series of
	 *                Strings
	 */
	public void sendMessage(StringBuilder message) {
		String[] lines = message.toString().split("\\n");
		for (String s : lines) {
			sendMessage(s);
		}
	}

	/**
	 * Try to gracefully close the connection
	 */
	public void close() {

		if (this.socket != null) {

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
}
