package org.serji.sw.server.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.serji.sw.server.models.BugReport;
import org.serji.sw.server.models.Employee;

/**
 * Class of static methods used to backup and restore data using the data files.
 * Methods are synchronized since these methods may be called from multiple
 * threads
 * 
 * Uses static helper methods from Employee and BugReport classes to convert
 * objects to and from strings
 * 
 * @author Justin
 *
 */
public class DataBackup {

	/**
	 * Write all in memory data to the backup files
	 */
	public static synchronized void writeData() {

		writeEmployeeData();
		writeBugReportData();
	}

	/**
	 * Read all data from backup files into memory
	 */
	public static synchronized void readData() {

		readEmployeeData();
		readBugReportData();
	}

	/**
	 * Read employee data from the employee backup file
	 */
	public static synchronized void readEmployeeData() {

		List<Employee> employees = new ArrayList<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(Config.employeeDataFile));

			String line;
			while ((line = br.readLine()) != null) {
				employees.add(Employee.csvStringToEmployee(line));
			}
			br.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		EmployeeData.setEmployees(employees);
	}

	/**
	 * Write employee data to the employee backup file
	 */
	private static synchronized void writeEmployeeData() {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(Config.employeeDataFile));

			for (Employee e : EmployeeData.getEmployees()) {
				// write a single line to the file
				bw.write(Employee.employeeToCsvString(e));
			}
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Read bug report data from the bug report backup file
	 */
	public static synchronized void readBugReportData() {

		List<BugReport> bugReportList = new ArrayList<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(Config.bugReportDataFile));

			String line;
			while ((line = br.readLine()) != null) {
				bugReportList.add(BugReport.csvStringToBugReport(line));
			}
			br.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BugReportData.setBugReportList(bugReportList);
	}

	/**
	 * Write bug report data to the bug report backup file
	 */
	private static synchronized void writeBugReportData() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(Config.bugReportDataFile));

			for (BugReport b : BugReportData.getBugReportList()) {
				// write a single line to the file
				bw.write(BugReport.bugReportToCsvString(b));
			}
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
