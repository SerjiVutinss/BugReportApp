package org.serji.sw.server;

import org.serji.sw.server.models.Employee;

public class MainMenu {

	private RequestHandler handler;
//	private boolean keepAlive = true;

	public MainMenu(RequestHandler handler) {
		this.handler = handler;
	}

	public void login() {

		boolean inputIsValid = false;
		while (!inputIsValid) {
			String message = null;
			StringBuilder sb = new StringBuilder();

			// ask for email address and wait for client response
			sb.append("Please enter your email address: ");
			handler.sendMessage(sb.toString());
			sb.setLength(0);

			// check for email address in employees list
			message = handler.getMessage();
			if (message != null) {
				// check to see if email exists in list of employees
				Employee e = ServerData.checkEmailExists(message);
				if (e != null) {
					// email exists, proceed with login attempt, ask for id and await response
					sb.append("Employee found");
					handler.sendMessage(sb.toString());
					sb.setLength(0);
					sb.append("Please enter your employee ID: ");
					handler.sendMessage(sb.toString());
					sb.setLength(0);

					message = handler.getMessage();
					if (message != null) {
						// check that this id matches the user with email
						if (e.getId() == Integer.parseInt(message)) {
							System.out.println(e.getName() + " Logged In");
							sb.append("Welcome " + e.getName());
							handler.sendMessage(sb.toString());
							sb.setLength(0);
							inputIsValid = true;
						} else {
							sb.append("ID did not match for " + e.getName());
							handler.sendMessage(sb.toString());
							sb.setLength(0);
						}

					}

				} else {
					sb.append("Employee with email not found\n");
					handler.sendMessage(sb.toString());
				}
			}
		}

	}

	public void run() {

		StringBuilder sb = new StringBuilder();
		sb.append("\n\nPlease make a selection: ");
		sb.append("\n1. Register New Employee");
		sb.append("\n2. List Employees");
		sb.append("\n-1. Quit");

		handler.sendMessage(sb.toString());

		String message = null;
		message = handler.getMessage();
		if (message != null) {

			switch (message) {

			case "1":
				registerEmployee();
				break;

			case "2":
				showEmployees();
				break;

			case "-1":
//				keepAlive = false;
				handler.close();
				break;

			default:
				handler.sendMessage("Unknown Input, please try again");
			}
		}
	}

	public void registerEmployee() {
		StringBuilder sb;
		sb = new StringBuilder();
		sb.append("\nPlease enter employee name: ");
		handler.sendMessage(sb.toString());

		String message = handler.getMessage();

		if (message != null) {
			System.out.println("Employee Name: " + message);
			handler.sendMessage("RECEIVED: " + message);
		}
	}

	public void showEmployees() {
		StringBuilder sb;
		sb = new StringBuilder();
		sb.append("\nDisplaying all employees:");
		for (Employee e : ServerData.getEmployees()) {
			sb.append("\n*****************************************");
			sb.append("\nEmployee ID: " + e.getId());
			sb.append("\n       Name: " + e.getName());
			sb.append("\n      Email: " + e.getEmail());
			sb.append("\n Department: " + e.getDepartment());
			sb.append("\n*****************************************");
		}

		handler.sendMessage(sb.toString());

//		String message = handler.getMessage();
//
//		if (message != null) {
//			System.out.println("Employee Name: " + message);
//			handler.sendMessage("RECEIVED: " + message);
//		}
	}
}
