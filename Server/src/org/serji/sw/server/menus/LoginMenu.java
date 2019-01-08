package org.serji.sw.server.menus;

import org.serji.sw.server.client.RequestHandler;
import org.serji.sw.server.data.EmployeeData;
import org.serji.sw.server.models.Employee;

/**
 * Class with static method to log in a user. A RequestHandler must be passed as an argument to each
 * method.
 * 
 * @author Justin
 *
 */
public class LoginMenu {

	public static void login(RequestHandler handler) {

		boolean inputIsValid = false;
		while (!inputIsValid) {
			StringBuilder sb = new StringBuilder();

			// ask for email address and wait for client response
			sb.append("Please enter your email address: ");
			handler.sendMessage(sb.toString());
			sb.setLength(0);

			// check for email address in employees list
			String input = handler.getMessage();
			if (input != null) {
				// check to see if email exists in list of employees
				if (EmployeeData.emailExists(input)) {

					Employee emp = EmployeeData.getEmployee(input);

					// email exists, proceed with login attempt, ask for id and await response
					sb.append("Employee found");
					handler.sendMessage(sb.toString());
					sb.setLength(0);
					sb.append("Please enter your employee ID: ");
					handler.sendMessage(sb.toString());
					sb.setLength(0);

					input = handler.getMessage();
					if (input != null) {
						int empID;
						try {
							empID = Integer.parseInt(input);

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
							handler.sendMessage("Employee ID must be an integer");
						}
					} else {
						sb.append("Employee cannot be null\n");
						handler.sendMessage(sb.toString());
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
