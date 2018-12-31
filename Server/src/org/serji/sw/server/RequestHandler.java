package org.serji.sw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler extends Thread {

	private Socket socket;
	private String message;

	public RequestHandler(Socket socket, String message) {
		this.socket = socket;
		this.message = message;
	}

	public void run() {

		System.out.println("TCP Server processing the incoming request");

		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

//			BufferedReader br = new BufferedReader(in.);

			String message = null;
			try {
				message = (String) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Message recieved from client: ");
			System.out.println(message);

			System.out.println("Sending Response");

			// send response
			String messageSend = "Hello World from TCP Server";
			out.writeObject(messageSend);
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
