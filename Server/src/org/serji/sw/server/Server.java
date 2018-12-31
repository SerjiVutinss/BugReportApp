package org.serji.sw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private int port;
	private ServerSocket serverSocket;

	public Server() {
		serverSocket = null;
		port = 8000;

		listen();
	}

	public void listen() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("TCP Server running on port " + port);
		} catch (IOException e) {
			System.out.println("TCP Server could not be started on port " + port);
		}

		while (true) {
			Socket clientSocket = null;
			try {
				System.out.println("TCP Server waiting for connections");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("ERROR: Cannot accept client request");
				return;
			}

			Thread t = new RequestHandler(clientSocket, "hello");
			t.start();
		}
	}

	public static void main(String[] args) {
		new Server();
	}

}
