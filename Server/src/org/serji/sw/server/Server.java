package org.serji.sw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.serji.sw.server.client.RequestHandler;
import org.serji.sw.server.data.BugReportData;
import org.serji.sw.server.data.DataBackup;
import org.serji.sw.server.data.EmployeeData;

/**
 * The main application class - starts the server and listens for client
 * connections
 * 
 * 
 * @author Justin
 *
 */
public class Server {

	private static final int port = 8000;
	private ServerSocket serverSocket;

	/**
	 * Constructor
	 */
	public Server() {

		System.out.println("Starting Server...");
		System.out.println("Reading Data Backup Files...");
		// read data from the backup files and load these into memory
		DataBackup.readData();
		// print out a summary of loaded data
		System.out.println("\t" + EmployeeData.getEmployees().size() + " employees added");
		System.out.println("\t" + BugReportData.getBugReportList().size() + " bug reports added");
		System.out.println();

		serverSocket = null;

		listen(); // start listening for client connections
	}

	/**
	 * Listens infinitely for client connections
	 */
	public void listen() {

		// attempt to create the server socket using the hard coded port
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("[SERVER]> Server running on port " + port);
		} catch (IOException e) {
			System.out.println("[SERVER]> Server could not be started on port " + port);
		}

		while (true) { // loop infinitely
			Socket clientSocket = null;
			try {
				System.out.println("[SERVER]> Waiting for connections");
				clientSocket = serverSocket.accept(); // accept a client connection if available
			} catch (IOException e) {
				System.out.println("[SERVER]> ERROR: Cannot accept client request");
				return;
			}

			// create and start a new thread for each client connection
			Thread t = new RequestHandler(clientSocket);
			t.start();
		}
	}

	public static void main(String[] args) {
		new Server();
	}

}
