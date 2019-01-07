package org.serji.sw.server.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.serji.sw.server.models.Employee;

public class DataBackup {

	public static synchronized void writeData() {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(Config.employeeDataFile));

			for (Employee e : EmployeeData.getEmployees()) {

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
//		EmployeeData.setEmployees(new ArrayList<>());

		try {
			BufferedReader br = new BufferedReader(new FileReader(Config.employeeDataFile));

			String line;
			while ((line = br.readLine()) != null) {
				employees.add(csvStringToEmployee(line));
//				EmployeeData.addEmployee();
			}
			br.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		EmployeeData.setEmployees(employees);
//		EmployeeData.rebuildDataStructures();
	}

	private static Employee csvStringToEmployee(String line) {
		String[] data = line.split(";");
		Employee e = new Employee();

		e.setId(Integer.parseInt(data[0]));
		e.setEmail(data[1].trim());
		e.setName(data[2].trim());
		e.setDepartment(data[3].trim());

		return e;
	}

	private static String employeeToCsvString(Employee e) {
		StringBuilder result = new StringBuilder();
		result.append(e.getId() + ";");
		result.append(e.getEmail() + ";");
		result.append(e.getName() + ";");
		result.append(e.getDepartment());
		result.append("\n");

		return result.toString();
	}

}
