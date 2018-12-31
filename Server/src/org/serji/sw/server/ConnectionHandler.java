//package org.serji.sw.server;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//
//public class ConnectionHandler extends Thread {
//
//	private Socket socket;
//	private Server server;
//	private ObjectInputStream in;
//	private ObjectOutputStream out;
//
//	boolean keepAlive = true;
//
//	public ConnectionHandler(Socket socket, Server server) {
//		super("ServerConnectionThread");
//		this.socket = socket;
//		this.server = server;
//	}
//
//	@Override
//	public void run() {
//
//		try {
//			in = new ObjectInputStream(socket.getInputStream());
//			out = new ObjectOutputStream(socket.getOutputStream());
//
////			while (keepAlive) {
////				while (in.available() == 0) {
////					Thread.sleep(1);
////				}
////			}
//
//			try {
//				listen();
//			} catch (ClassNotFoundException | InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
////			String input = (String) in.readObject();
////			sendStringToClient("HELLO");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
////		listen();
//	}
//
//	private void listen() throws IOException, InterruptedException, ClassNotFoundException {
//		while (true) {
//
//			while (in.available() == 0) {
//
//				Thread.sleep(1);
//
//			}
//
//			String input = (String) in.readObject();
////				System.out.println(input);
//			sendStringToClient(input);
////				out.writeObject(input);
////				out.flush();
//
//		}
//
////		in.close();
////		out.close();
////		socket.close();
//
//	}
//
//	public void sendStringToClient(String text) {
//
//		try {
//			out.writeObject(text);
//			out.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public void sendStringToAllClients(String text) {
//		for (ConnectionHandler c : server.getConnections()) {
//			c.sendStringToClient(text);
//		}
//	}
//}
