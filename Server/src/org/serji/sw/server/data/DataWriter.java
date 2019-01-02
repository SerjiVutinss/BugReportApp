package org.serji.sw.server.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.serji.sw.server.menus.EmployeeMenu;
import org.serji.sw.server.models.Employee;

public class DataWriter {

	private static final String filepath = System.getProperty("user.dir") + "/resources/";

	private static final String employeeDataFile = filepath + "employees.csv";
	private static final String bugReportDataFile = filepath + "bugreports.csv";

	public static synchronized void writeData() {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(employeeDataFile));

			for (Employee e : ServerData.getEmployees()) {

				// write a single line to the file
				bw.write(employeeToCsvString(e));
			}
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static synchronized void readData() {

		List<Employee> employees = new ArrayList<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(employeeDataFile));

			String line;
			while ((line = br.readLine()) != null) {
				ServerData.addEmployee(csvStringToEmployee(line));
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
//		ServerData.setEmployees(employees);
//		System.out.println("Added " + employees.size() + " employees to data");

	}

	public static Employee csvStringToEmployee(String line) {
		String[] data = line.split(";");
		Employee e = new Employee();

		e.setId(Integer.parseInt(data[0]));
		e.setEmail(data[1]);
		e.setName(data[2]);
		e.setDepartment(data[3]);

		return e;
	}

	public static String employeeToCsvString(Employee e) {
		StringBuilder result = new StringBuilder();
		result.append(e.getId() + "; ");
		result.append(e.getEmail() + "; ");
		result.append(e.getName() + "; ");
		result.append(e.getDepartment());

		return result.toString();
	}

}
