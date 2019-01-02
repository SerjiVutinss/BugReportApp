package org.serji.sw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.serji.sw.server.models.Employee;

public class Server {

	private int port;
	private ServerSocket serverSocket;

	public Server() {

		ServerData.addEmployee(new Employee(1, "justin", "justin@example.com", "finance"));

		serverSocket = null;
		port = 8000;

		listen();
	}

	public void listen() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("[SERVER]> Server running on port " + port);
		} catch (IOException e) {
			System.out.println("[SERVER]> Server could not be started on port " + port);
		}

		while (true) {
			Socket clientSocket = null;
			try {
				System.out.println("[SERVER]> Waiting for connections");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("[SERVER]> ERROR: Cannot accept client request");
				return;
			}

			Thread t = new RequestHandler(clientSocket);
			t.start();
		}
	}

	public static void main(String[] args) {
		new Server();
	}

}
