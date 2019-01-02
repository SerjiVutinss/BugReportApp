package org.serji.sw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.serji.sw.server.menus.LoginMenu;
import org.serji.sw.server.models.Employee;

public class RequestHandler extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean keepAlive = true;

	private Employee employee;

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee e) {
		this.employee = e;
	}

	public boolean isLoggedIn() {
		return this.employee != null;
	}

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
			LoginMenu loginMenu = new LoginMenu(this);
//			mainMenu.login();
			while (keepAlive) {
				if (socket.isConnected()) {
					loginMenu.run();
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
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
			Date dt = new Date();

			String strDate = df.format(dt);
			out.writeObject("[" + strDate + "][SERVER]> " + message);
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
