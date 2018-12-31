package org.serji.sw.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public Server() {
		ServerSocket serverSocket = null;
		int port = 8000;

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

			processClientRequest(clientSocket);
		}
	}

	private void processClientRequest(Socket socket) {

		System.out.println("TCP Server processing the incoming request");

		try {
			PrintStream out = new PrintStream(socket.getOutputStream());
			InputStreamReader in = new InputStreamReader(socket.getInputStream());

			BufferedReader br = new BufferedReader(in);

			String message = null;
			message = br.readLine();

			System.out.println("Message recieved from client: ");
			System.out.println(message);

			System.out.println("Sending Response");

			// send response
			String messageSend = "Hello World from TCP Server";
			out.println(messageSend);
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Server();
	}

}
