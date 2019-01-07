package org.serji.sw.server.data;

import java.util.HashMap;
import java.util.Map;

public class Config {

	private static final String filepath = System.getProperty("user.dir") + "/resources/";
	public static final String employeeDataFile = filepath + "employees.csv";
	public static final String bugReportDataFile = filepath + "bugreports.csv";

	public static final Map<Integer, String> Status = buildStatusMap();

	private static Map<Integer, String> buildStatusMap() {
		Map<Integer, String> statusMap = new HashMap<Integer, String>();

		statusMap.put(1, "open");
		statusMap.put(2, "assigned");
		statusMap.put(3, "closed");

		return statusMap;
	}

}
