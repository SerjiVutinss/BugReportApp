package org.serji.sw.server.models;

import java.io.Serializable;
import java.util.Date;

import org.serji.sw.server.Utils;

public class BugReport implements Serializable {

	/**
	 * Bean class for a Bug Report
	 * 
	 * Also some static methods related to bug reports
	 */
	private static final long serialVersionUID = 7276222399633088108L;

	private int id;
	private String applicationName;
	private Date timestamp;
	private String platform;
	private String description;
	private int status;
	private int assignedTo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(int assignedTo) {
		this.assignedTo = assignedTo;
	}

	public static BugReport csvStringToBugReport(String line) {
		String[] data = line.split(";");
		BugReport b = new BugReport();

		b.setId(Integer.parseInt(data[0]));
		b.setApplicationName(data[1].trim());
		b.setTimestamp(Utils.stringToDate(data[2].trim()));
		b.setPlatform(data[3].trim());
		b.setDescription(data[4].trim());
		b.setStatus(Integer.parseInt(data[5].trim()));
		b.setAssignedTo(Integer.parseInt(data[6].trim()));

		System.out.println("Bug ASsigned to : " + b.getAssignedTo());

		return b;
	}

	public static String bugReportToCsvString(BugReport b) {
		StringBuilder result = new StringBuilder();
		result.append(b.getId() + ";");
		result.append(b.getApplicationName() + ";");
		result.append(Utils.dateToString(b.getTimestamp()) + ";");
		result.append(b.getPlatform() + ";");
		result.append(b.getDescription() + ";");
		result.append(b.getStatus() + ";");
		result.append(b.getAssignedTo());
		result.append("\n");

		return result.toString();
	}

}
