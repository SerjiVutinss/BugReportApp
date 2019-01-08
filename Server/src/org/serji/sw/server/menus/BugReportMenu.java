package org.serji.sw.server.menus;

import org.serji.sw.server.client.RequestHandler;
import org.serji.sw.server.data.BugReportData;
import org.serji.sw.server.data.Config;
import org.serji.sw.server.data.EmployeeData;
import org.serji.sw.server.models.BugReport;

public class BugReportMenu {

	public static void addBugReport(RequestHandler handler) {

		BugReport bugReport = new BugReport();
		bugReport.setId(-1);
		bugReport.setAssignedTo(-1);
//		StringBuilder sb = new StringBuilder();

		bugReport.setApplicationName("");
		bugReport.setPlatform("");
		bugReport.setDescription("");
		bugReport.setStatus(1);

		handler.sendMessage("Please enter Application Name: ");

		String applicationName = handler.getMessage();

		if (applicationName != null && applicationName.length() > 0) {
			bugReport.setApplicationName(applicationName);
			handler.sendMessage("Please enter Platform: ");

			String platform = handler.getMessage();
			if (platform != null && platform.length() > 0) {
				bugReport.setPlatform(platform);
				handler.sendMessage("Please enter a description: ");
				String description = handler.getMessage();

				if (description != null && description.length() > 0) {
					bugReport.setDescription(description);
//					handler.sendMessage("Please enter status: ");
//					String status = handler.getMessage();

//					if (status != null && status.length() > 0) {
////						bugReport.setStatus(status);
//
//						BugEditMenu.updateStatus(handler, bugReport);

					if (BugReportData.addBugReport(bugReport)) {
						handler.sendMessage("Bug Added successfully");
					} else {
						handler.sendMessage("Unkown error occurred while adding bug, ABORTING");
					}

//					} else {
//						handler.sendMessage("Bad Input, ABORTING");
//						return;
//					}
				} else {
					handler.sendMessage("Bad Input, ABORTING");
					return;
				}
			} else {
				handler.sendMessage("Bad Input, ABORTING");
				return;
			}
		} else {
			handler.sendMessage("Bad Input, ABORTING");
			return;
		}
	}

	public static void assignBugReportToEmployee(RequestHandler handler) {

		showAllBugReports(handler);
		StringBuilder sb = new StringBuilder("Please enter the ID of the bug you wish to assign to an employee: ");
		handler.sendMessage(sb.toString());

		String input = handler.getMessage();
		int bugReportID;

		try {
			bugReportID = Integer.parseInt(input);

			if (BugReportData.bugIdExists(bugReportID)) {

				BugEditMenu.assignBugToEmployee(handler, BugReportData.getBugReport(bugReportID));

			} else {
				handler.sendMessage("Bug Report with ID " + bugReportID + " not found");
			}
		} catch (NumberFormatException e) {
			handler.sendMessage("Input must be an integer");
		}
	}

	public static void showAllBugReports(RequestHandler handler) {
		StringBuilder sb = new StringBuilder();
		sb.append("\nDisplaying all bug reports:");
		for (BugReport b : BugReportData.getBugReportList()) {
			sb.append(getBugReportStringBuilder(b));
		}
		handler.sendMessage(sb.toString());
	}

	public static void showUnassignedBugReports(RequestHandler handler) {
		StringBuilder sb = new StringBuilder();
		sb.append("\nDisplaying unassigned bug reports:");
		for (BugReport b : BugReportData.getBugReportList()) {
			if (b.getAssignedTo() == -1) {
				sb.append(getBugReportStringBuilder(b));
			}
		}
		handler.sendMessage(sb.toString());
	}

	private static StringBuilder getBugReportStringBuilder(BugReport b) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n*****************************************");
		sb.append("\n     BugReportID: " + b.getId());
		sb.append("\nApplication Name: " + b.getApplicationName());
		sb.append("\n        Platform: " + b.getPlatform());
		sb.append("\n     Description: " + b.getDescription());
		sb.append("\n          Status: " + Config.statuses.get(b.getStatus()));

		if (b.getAssignedTo() == -1) {
			sb.append("\n     Assigned To: Unassigned");
		} else {
			sb.append("\n     Assigned To: " + EmployeeData.getEmpEmail(b.getAssignedTo()));
		}

		sb.append("\n           Added: " + b.getTimestamp());
		sb.append("\n*****************************************");
		return sb;
	}
}
