package org.serji.sw.server.menus;

import org.serji.sw.server.RequestHandler;
import org.serji.sw.server.ServerData;
import org.serji.sw.server.Utils;
import org.serji.sw.server.models.Employee;

public class EmployeeMenu {

	public static void registerEmployee(RequestHandler handler) {

		Employee e = new Employee();

		StringBuilder sb;
		sb = new StringBuilder();
		sb.append("\nPlease enter employee email: ");
		handler.sendMessage(sb);
		String eEmail = handler.getMessage();

		if (eEmail != null) {
			handler.sendMessage("RECEIVED: " + eEmail);
			if (Utils.isValidEmailAddress(eEmail)) {
				if (!ServerData.emailExists(eEmail)) {
					// we have a valid, unique email address
					e.setEmail(eEmail);
					handler.sendMessage("Please enter employee name: ");
					eEmail = null;

					String eName = handler.getMessage();
					if (eName != null && eName.length() > 0) {

						e.setName(eName);
						handler.sendMessage("Please enter department name: ");
						eName = null;

						String eDept = handler.getMessage();
						if (eDept != null && eDept.length() > 0) {
							e.setDepartment(eDept);

							if (ServerData.addEmployee(e)) {
								handler.sendMessage("Employee " + e.getEmail() + " successfully added");
							} else {
								handler.sendMessage("Failed to add employee " + e.getEmail() + ", reason unknown");
							}
						}
					}
				}

				else {
					handler.sendMessage("User with email " + eEmail + " already exists on system - ABORTING");
					return;
				}
			} else {
				handler.sendMessage("Invalid email address format for: " + eEmail + " - ABORTING");
				return;
			}

		}
	}

	public static void showEmployees(RequestHandler handler) {
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
	}
}
