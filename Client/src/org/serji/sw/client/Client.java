package org.serji.sw.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public Client() {
		System.out.println("Welcome to TCP Client");

		Socket socket = null;
		String host = "localhost";

		int port = 8000;

		try {
			System.out.println("Attempt to connect to TCP server");
			socket = new Socket(host, port);
		} catch (IOException e) {
			System.out.println("CLIENT cannot connect to server");
		}

		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			while (true) {

				Scanner s = new Scanner(System.in);
				System.out.println("Press a key to send a message");
				s.next();

				// prepare the String
				String message = "Hello World From TCP Client \n\n\n\n";
				out.writeObject(message);

//				BufferedReader br = new BufferedReader(in.readObject());
				try {
					message = (String) in.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Message received from server: \n\t" + message);

			}
		} catch (IOException e) {
			System.out.println("Could not get streams");
		}
	}

	public static void main(String[] args) {
		new Client();
	}
}
