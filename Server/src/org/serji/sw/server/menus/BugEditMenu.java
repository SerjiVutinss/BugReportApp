package org.serji.sw.server.menus;

import org.serji.sw.server.client.RequestHandler;
import org.serji.sw.server.data.BugReportData;
import org.serji.sw.server.data.Config;
import org.serji.sw.server.data.EmployeeData;
import org.serji.sw.server.models.BugReport;
import org.serji.sw.server.models.Employee;

public class BugEditMenu {

	public static void runUpdateMenu(RequestHandler handler) {

		BugReportMenu.showAllBugReports(handler);
		StringBuilder sb = new StringBuilder("Please enter the ID of the bug you wish to update: ");
		handler.sendMessage(sb.toString());

		String input = handler.getMessage();
		int bugReportID;
		try {
			bugReportID = Integer.parseInt(input);

			if (BugReportData.bugIdExists(bugReportID)) {
				updateBugReport(handler, BugReportData.getBugReport(bugReportID));
			}
		} catch (NumberFormatException e) {
			handler.sendMessage("Input must be an integer");
		}
	}

	public static void updateBugReport(RequestHandler handler, BugReport b) {

		boolean keepAlive = true;
		StringBuilder sb = new StringBuilder();

		do {
			// now run an update menu
			sb.setLength(0);
			sb.append("What would you like to update?");
			sb.append("\n1. Status");
			sb.append("\n2. Append to Description");
			sb.append("\n3. Changed Assigned Employee");
			sb.append("\n-1. Return to Main Menu");
			handler.sendMessage(sb.toString());

			String input = handler.getMessage();
			switch (input) {

			case "1":
				// update the status
				updateStatus(handler, b);
				break;

			case "2":
				// append to the description
				appendToDescription(handler, b);
				break;

			case "3":
				// changed the assigned employee
				assignBugToEmployee(handler, b);
				break;

			case "-1":
				// exit this menu
				keepAlive = false;
				break;

			default:
				break;

			}
		} while (keepAlive);
	}

	private static void appendToDescription(RequestHandler handler, BugReport b) {
		StringBuilder sb = new StringBuilder();

		sb.append("\nThe current description of this bug report is: " + b.getDescription());
		sb.append(
				"\n\nPlease enter any text you would like to append to the description (Enter -1 to exit with no changes)");
		handler.sendMessage(sb.toString());

		String input = handler.getMessage();

		int cancel;
		try {
			cancel = Integer.parseInt(input);
			if (cancel == -1) {
				return;
			}
		} catch (NumberFormatException e) {
			// don't need to do anything here
		}

		if (input == null) {
			return;
		}

		b.setDescription(b.getDescription() + " " + input);
		handler.sendMessage("Description updated");

	}

	public static void updateStatus(RequestHandler handler, BugReport b) {

		StringBuilder sb = new StringBuilder();
		sb.append("Please select the status to set:");
		sb.append("\n1. Open");
//		sb.append("\n2. Assigned");
		sb.append("\n2. Closed");
		handler.sendMessage(sb.toString());

		String input = handler.getMessage();
		switch (input) {
		case "1":
			// set status to open
			b.setStatus(1);
			handler.sendMessage("Status changed to " + Config.Status.get(1));
			break;

//		case "2":
//			// set status to assigned
//			handler.sendMessage("This status cannot be changed");
//			break;

		case "2":
			// set status to closed
			b.setStatus(3);
			handler.sendMessage("Status changed to " + Config.Status.get(3));
			break;

		default:
			break;
		}
	}

	public static void assignBugToEmployee(RequestHandler handler, BugReport b) {
		handler.sendMessage(
				"Bug Report (ID: " + "" + "), please enter the ID of the employee you wish to assign this bug to:");
		String input = handler.getMessage();

		int empID;
		try {
			empID = Integer.parseInt(input);
			if (EmployeeData.empIdExists(Integer.parseInt(input))) {
				Employee emp = EmployeeData.getEmployee(empID);

				// assign the employee to the bug here
				BugReportData.assignBugToEmployee(b, emp);

				handler.sendMessage("Bug assigned to Employee ID " + emp.getId() + ", Email " + emp.getEmail());
			}
		} catch (NumberFormatException e) {
			handler.sendMessage("Input must be an integer");
		}
	}
}