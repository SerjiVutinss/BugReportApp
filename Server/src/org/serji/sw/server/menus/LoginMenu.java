package org.serji.sw.server.menus;

import org.serji.sw.server.client.RequestHandler;
import org.serji.sw.server.data.EmployeeData;
import org.serji.sw.server.models.Employee;

public class LoginMenu {

	public static void login(RequestHandler handler) {

		boolean inputIsValid = false;
		while (!inputIsValid) {
			String email = null;
			StringBuilder sb = new StringBuilder();

			// ask for email address and wait for client response
			sb.append("Please enter your email address: ");
			handler.sendMessage(sb.toString());
			sb.setLength(0);

			// check for email address in employees list
			email = handler.getMessage();
			if (email != null) {
				// check to see if email exists in list of employees
				if (EmployeeData.emailExists(email)) {

					Employee emp = EmployeeData.getEmployee(email);

					// email exists, proceed with login attempt, ask for id and await response
					sb.append("Employee found");
					handler.sendMessage(sb.toString());
					sb.setLength(0);
					sb.append("Please enter your employee ID: ");
					handler.sendMessage(sb.toString());
					sb.setLength(0);

					email = handler.getMessage();
					if (email != null) {
						int empID;
						try {
							empID = Integer.parseInt(email);

							// check that this id matches the user with email
							if (empID == emp.getId()) {
								System.out.println(emp.getName() + " Logged In");
								sb.append("Welcome " + emp.getName());
								handler.sendMessage(sb.toString());
								sb.setLength(0);

								handler.getMainMenu().setEmployee(emp);
								inputIsValid = true;
							} else {
								sb.append("ID did not match for " + emp.getName());
								handler.sendMessage(sb.toString());
								sb.setLength(0);
								return;
							}

						} catch (Exception e) {

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
