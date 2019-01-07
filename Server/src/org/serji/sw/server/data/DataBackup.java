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

public class DataBackup {

	public static synchronized void writeData() {

		writeEmployeeData();
		writeBugReportData();
	}

	public static synchronized void readData() {

		readEmployeeData();
		readBugReportData();
	}

	public static synchronized void readEmployeeData() {

		List<Employee> employees = new ArrayList<>();
//		EmployeeData.setEmployees(new ArrayList<>());

		try {
			BufferedReader br = new BufferedReader(new FileReader(Config.employeeDataFile));

			String line;
			while ((line = br.readLine()) != null) {
				employees.add(Employee.csvStringToEmployee(line));
//				EmployeeData.addEmployee();
			}
			br.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		EmployeeData.setEmployees(employees);
//		EmployeeData.rebuildDataStructures();
	}

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

	public static synchronized void readBugReportData() {

		List<BugReport> bugReportList = new ArrayList<>();
//		EmployeeData.setEmployees(new ArrayList<>());

		try {
			BufferedReader br = new BufferedReader(new FileReader(Config.bugReportDataFile));

			String line;
			while ((line = br.readLine()) != null) {
				bugReportList.add(BugReport.csvStringToBugReport(line));
//				EmployeeData.addEmployee();
			}
			br.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
//		EmployeeData.setEmployees(employees);
		BugReportData.setBugReportList(bugReportList);
//		EmployeeData.rebuildDataStructures();
	}

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
