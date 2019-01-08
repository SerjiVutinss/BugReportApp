package org.serji.sw.server.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.serji.sw.server.models.BugReport;
import org.serji.sw.server.models.Employee;

/**
 * Class to implement the in memory BugReport data structures
 * 
 * @author Justin
 *
 */
public abstract class BugReportData {

	private volatile static List<BugReport> bugReportList = new ArrayList<>();
	private volatile static Set<Integer> bugReportId = new HashSet<>();

	public synchronized static List<BugReport> getBugReportList() {
		return bugReportList;
	}

	public synchronized static void setBugReportList(List<BugReport> newBugReportList) {
		BugReportData.bugReportList = newBugReportList;
		rebuildDataStructures();
	}

	/**
	 * Rebuild all data structures - empIdSet, emailSet and bug reports List
	 * 
	 * Before an bug report may be added to the bug reportList, we must ensure that
	 * the ID and email have not already been added, i.e. they do not exist in the
	 * respective sets
	 * 
	 * Once it has been verified that both the new bug report id and email are
	 * unique, add each to the sets and then add the bug report to the list
	 * 
	 * This method is called after reloading data from the data file, therefore,
	 * each bug report should already have an ID, i.e. a new ID should not be
	 * assigned
	 */
	public synchronized static void rebuildDataStructures() {

		// reinitialize these sets
		bugReportId = new HashSet<>();

		// clone the list to avoid ConcurrentModificationException
		List<BugReport> bugListClone = new ArrayList<>();
		for (BugReport e : bugReportList) {
			bugListClone.add(e);
		}

		// now reinitialize the static list to add the new bug reports to
		bugReportList = new ArrayList<>();
		// try to add each bug report to the bug report list
		for (BugReport e : bugListClone) {
			// ensure that this bug report's ID and email are unique within the system
			if (!bugReportId.contains(e.getId())) {
				bugReportId.add(e.getId()); // add id to set
				bugReportList.add(e); // finally add the bug report object to the list
				System.out.println("New bug report added");
			}
		}
	}

	public synchronized static void assignBugToEmployee(BugReport b, Employee e) {

		b.setAssignedTo(e.getId());
	}

	/**
	 * Method used to register a new bug report and assign it an ID
	 * 
	 * @param bugReport the bug report object to be added to the static list of bug
	 *                  reports
	 * @return true if bug report was successfully added to the list
	 */
	public synchronized static boolean addBugReport(BugReport bugReport) {

		if (bugReport.getId() < 0) {
			bugReport.setId(getValidBugId()); // set a unique id for this bug report
		}

		if (!bugReportId.contains(bugReport.getId())) {
			bugReportId.add(bugReport.getId());

			// set the time stamp
			bugReport.setTimestamp(new Date());

			bugReportList.add(bugReport);
			System.out.println("New bug report added");
			return true;
		}
		return false; // bug report ID or email was not unique
	}

	/**
	 * Returns the bug report with the supplied ID if that bug report exists within
	 * the static list
	 * 
	 * TODO: optimize to first check the set?
	 * 
	 * @param id the unique ID of the bug report to be returned
	 * @return the bug report with the supplied ID if it exists, else return null
	 */
	public synchronized static BugReport getBugReport(int id) {
		System.out.println(bugReportList.size());
		for (BugReport b : bugReportList) {
			if (b.getId() == id) {
				return b;
			}
		}
		return null;
	}

	/**
	 * Method to return a new, unique ID (integer) to be assigned to a newly
	 * registered bug report
	 * 
	 * @return a new valid bug report ID
	 */
	private synchronized static int getValidBugId() {
		boolean isValidId = false;
		int uniqueId = 0;
		while (!isValidId) {
			if (BugReportData.bugIdExists(uniqueId)) {
				uniqueId++;
				System.out.println(uniqueId);
			} else {
				isValidId = true;
			}
		}
		return uniqueId;
	}

	/**
	 * Returns true if the suppled ID already exists in the system
	 * 
	 * @param id the ID to check
	 * @return true if ID exists in the system, else false
	 */
	public synchronized static boolean bugIdExists(int id) {
		return bugReportId.contains(id);
	}
}
