package org.serji.sw.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;

	public Client() {
		try {
			socket = new Socket("127.0.0.1", 8000);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			listen();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listen() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			while (!scanner.hasNextLine()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String input = scanner.nextLine();
			try {
				out.writeUTF(input);
				while (in.available() == 0) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				String reply = in.readUTF();
				System.out.println(reply);
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}

		try {
			in.close();
			out.close();
			socket.close();
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Client();
	}
}
