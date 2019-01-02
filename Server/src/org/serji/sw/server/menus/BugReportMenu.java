package org.serji.sw.server.menus;

import org.serji.sw.server.RequestHandler;
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
				handler.sendMessage("Please enter a description: ");
				String description = handler.getMessage();

				if (description != null && description.length() > 0) {
					handler.sendMessage("Please enter status: ");
					String status = handler.getMessage();

					if (status != null && status.length() > 0) {
						handler.sendMessage("Bug Added successfully");
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
}
