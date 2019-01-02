package org.serji.sw.server.menus;

import org.serji.sw.server.RequestHandler;

public class MainMenu {

	private RequestHandler handler;

	public MainMenu(RequestHandler handler) {
		this.handler = handler;

		System.out.println("Main Menu Opened");

		while (true) {

		}
	}
	
	public void run() {

		StringBuilder sb = new StringBuilder();
		sb.append("\n\nPlease make a selection: ");
		sb.append("\n1. Register New Employee");
		sb.append("\n2. Login Existing Employee");
		sb.append("\n-1. Quit");

		handler.sendMessage(sb);

		String message = null;
		message = handler.getMessage();
		if (message != null) {

			switch (message) {

			case "1":
//				registerEmployee();
				break;

			case "2":
//				login();
				break;

			case "-1":
				handler.close();
				break;

			default:
				handler.sendMessage("Unknown Input, please try again");
				break;
			}
		}
	}

}
