package org.serji.sw.server.menus;

import org.serji.sw.server.client.RequestHandler;
import org.serji.sw.server.data.BugReportData;
import org.serji.sw.server.data.Config;
import org.serji.sw.server.models.BugReport;

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
			sb.append("\nWhat would you like to update?");
			sb.append("\n1. Status");
			sb.append("\n2. Append to Description");
			sb.append("\n3. Changed Assigned Employee");
			sb.append("\n-1. Return to Main Menu");
			handler.sendMessage(sb);

			String input = handler.getMessage();
			switch (input) {

			case "1":
				// update the status
				updateStatus(handler, b);
				break;

			case "2":
				// append to the description
				break;

			case "3":
				// changed the assigned employee
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
}
