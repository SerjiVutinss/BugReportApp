package org.serji.sw.server;

import java.util.ArrayList;
import java.util.List;

import org.serji.sw.server.models.Employee;

public class ServerData {

	private static List<Employee> employees = new ArrayList<>();

	public static List<Employee> getEmployees() {
		return employees;
	}

	public static void setEmployees(List<Employee> employees) {
		ServerData.employees = employees;
	}

	public static void addEmployee(Employee e) {
		employees.add(e);
	}

	public static Employee checkEmailExists(String email) {
		System.out.println(employees.size());
		for (Employee e : employees) {
			if (e.getEmail().equals(email)) {
				return e;
			}
		}
		return null;
	}

}
