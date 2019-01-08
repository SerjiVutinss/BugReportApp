package org.serji.sw.server.menus;

import org.serji.sw.server.client.RequestHandler;
import org.serji.sw.server.models.Employee;

/*
 * Main Menu which is created with each client handler.
 * 
 * Calls static methods in other menu classes
 */
public class MainMenu {

	private RequestHandler handler;
	private Employee employee;

	public MainMenu(RequestHandler handler) {
		this.handler = handler;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee e) {
		this.employee = e;
	}

	public boolean isLoggedIn() {
		return this.employee != null;
	}

	/**
	 * Logic for the main menu, messages are sent to the client and responses are
	 * awaited
	 */
	public void run() {

		StringBuilder sb = new StringBuilder();
		sb.append("Please make a selection: ");
		sb.append("\n1. Register New Employee");
		sb.append("\n2. Login Existing Employee");

		// only show this portion of the menu if the user is logged in
		if (this.isLoggedIn()) {
			sb.append("\n3. Add New Bug Report");
			sb.append("\n4. Assign Bug Report to Employee");
			sb.append("\n5. View Unassigned Bug Reports");
			sb.append("\n6. View All Bug Reports");
			sb.append("\n7. Edit a Bug Report");
			sb.append("\n9. List Existing Employees");
			sb.append("\n21. Save Backup Data");
			sb.append("\n22. Load Backup Data");
		}

		sb.append("\n-1. Quit");
		handler.sendMessage(sb.toString());

		String message = null;
		message = handler.getMessage();

		// switch for unauthenticated users
		if (message != null && !this.isLoggedIn()) {

			switch (message) {

			case "1":
				EmployeeMenu.registerEmployee(handler);
				break;

			case "2":
				LoginMenu.login(handler);
				break;

			case "-1":
				handler.close();
				break;

			default:
				handler.sendMessage("Unknown Input, please try again");
				break;
			}
		}
		// switch for authenticated users
		else if (message != null && this.isLoggedIn()) {

			switch (message) {

			case "1":
				EmployeeMenu.registerEmployee(handler);
				break;

			case "2":
				LoginMenu.login(handler);
				break;

			case "3":
				BugReportMenu.addBugReport(handler);
				break;

			case "4":
				BugReportMenu.assignBugReportToEmployee(handler);
				break;

			case "5":
				BugReportMenu.showUnassignedBugReports(handler);
				break;

			case "6":
				BugReportMenu.showAllBugReports(handler);
				break;

			case "7":
				BugEditMenu.runUpdateMenu(handler);
				break;

			case "9":
				EmployeeMenu.showEmployees(handler);
				break;

			case "21":
				BackupMenu.saveBackupData(handler);
				break;
			case "22":
				BackupMenu.readBackupData(handler);
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
