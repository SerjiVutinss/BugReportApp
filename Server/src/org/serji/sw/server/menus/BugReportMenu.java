package org.serji.sw.server.menus;

import org.serji.sw.server.RequestHandler;
import org.serji.sw.server.data.ServerData;
import org.serji.sw.server.models.BugReport;

public class BugReportMenu {

	public static void addBugReport(RequestHandler handler) {

		BugReport bugReport = new BugReport();
//		StringBuilder sb = new StringBuilder();

		bugReport.setApplicationName("");
		bugReport.setPlatform("");
		bugReport.setDescription("");
		bugReport.setStatus("");

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
					handler.sendMessage("Please enter status: ");
					String status = handler.getMessage();

					if (status != null && status.length() > 0) {
						bugReport.setStatus(status);

						if (ServerData.addBugReport(bugReport)) {
							handler.sendMessage("Bug Added successfully");
						} else {
							handler.sendMessage("Unkown error occurred while adding bug, ABORTING");
						}

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
		} else {
			handler.sendMessage("Bad Input, ABORTING");
			return;
		}

	}

	public static void showBugReports(RequestHandler handler) {
		StringBuilder sb;
		sb = new StringBuilder();
		sb.append("\nDisplaying all employees:");
		for (BugReport b : ServerData.getBugReports()) {
			sb.append("\n*****************************************");
			sb.append("\n     BugReportID: " + b.getId());
			sb.append("\nApplication Name: " + b.getApplicationName());
			sb.append("\n           Email: " + b.getPlatform());
			sb.append("\n     Description: " + b.getDescription());
			sb.append("\n          Status: " + b.getStatus());
			sb.append("\n           Added: " + b.getTimestamp());
			sb.append("\n*****************************************");
		}
		handler.sendMessage(sb.toString());
	}
}
