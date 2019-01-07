package org.serji.sw.server.menus;

import org.serji.sw.server.client.RequestHandler;
import org.serji.sw.server.data.EmployeeData;
import org.serji.sw.server.models.Employee;

public class LoginMenu {

	public static void login(RequestHandler handler) {

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
				if (EmployeeData.emailExists(message)) {

					Employee e = EmployeeData.getEmployee(message);

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

							handler.getMainMenu().setEmployee(e);
							inputIsValid = true;
						} else {
							sb.append("ID did not match for " + e.getName());
							handler.sendMessage(sb.toString());
							sb.setLength(0);
							return;
						}
					}

				} else {
					sb.append("Employee with email not found\n");
					handler.sendMessage(sb.toString());
					return;
				}
			}
		}
	}

}
