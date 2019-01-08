package org.serji.sw.server.menus;

import org.serji.sw.server.Utils;
import org.serji.sw.server.client.RequestHandler;
import org.serji.sw.server.data.EmployeeData;
import org.serji.sw.server.models.Employee;

/**
 * Class containing static methods to register an employee and send employee
 * details to client. A RequestHandler must be passed as an argument to each
 * method.
 * 
 * @param handler the RequestHanlder object to run this method with, will be
 *                different for each thread calling this method
 * 
 * @author Justin
 *
 */
public abstract class EmployeeMenu {

	/**
	 * Logic to register an employee
	 */
	public static void registerEmployee(RequestHandler handler) {

		Employee e = new Employee();
		e.setId(-1);

		StringBuilder sb;
		sb = new StringBuilder();
		sb.append("\nPlease enter employee email: ");
		handler.sendMessage(sb);
		String eEmail = handler.getMessage();

		if (eEmail != null) {
			handler.sendMessage("RECEIVED: " + eEmail);
			if (Utils.isValidEmailAddress(eEmail)) {
				if (!EmployeeData.emailExists(eEmail)) {
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

							if (EmployeeData.addEmployee(e)) {
								handler.sendMessage("Employee " + e.getEmail() + " successfully added with ID " + e.getId());
							} else {
								handler.sendMessage("Failed to add employee " + e.getEmail() + ", reason unknown");
							}
						}
					}
				} else {
					handler.sendMessage("User with email " + eEmail + " already exists on system - ABORTING");
					return;
				}
			} else {
				handler.sendMessage("Invalid email address format for: " + eEmail + " - ABORTING");
				return;
			}
		}
	}

	/**
	 * Send a single employee's details to the client
	 * 
	 * @param handler
	 * @param e       Employee object details to be sent
	 */
	public static void showEmployee(RequestHandler handler, Employee e) {
		StringBuilder sb;
		sb = new StringBuilder();
		sb.append("\n*****************************************");
		sb.append("\nEmployee ID: " + e.getId());
		sb.append("\n       Name: " + e.getName());
		sb.append("\n      Email: " + e.getEmail());
		sb.append("\n Department: " + e.getDepartment());
		sb.append("\n*****************************************");
		handler.sendMessage(sb.toString());
	}

	/**
	 * Sends all employees' details to the client
	 * 
	 * @param handler
	 */
	public static void showEmployees(RequestHandler handler) {
		StringBuilder sb;
		sb = new StringBuilder();
		sb.append("\nDisplaying all employees:");
		handler.sendMessage(sb.toString());
		for (Employee e : EmployeeData.getEmployees()) {
			showEmployee(handler, e);
		}
		handler.sendMessage(new StringBuilder("Finished Displaying Employees"));
	}
}
