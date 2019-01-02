package org.serji.sw.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.serji.sw.server.models.Employee;

public class ServerData {

	private volatile static List<Employee> employees = new ArrayList<>();
//	private static Map<Integer, String> employeeEmailIdMap = new HashMap<Integer, String>();

	private volatile static Set<Integer> idSet = new HashSet<>();
	private volatile static Set<String> emailSet = new HashSet<>();

	public synchronized static List<Employee> getEmployees() {
		return employees;
	}

	public synchronized static void setEmployees(List<Employee> employees) {
		ServerData.employees = employees;
	}

	public synchronized static boolean addEmployee(Employee e) {
		if (!idSet.contains(e.getId()) && !emailSet.contains(e.getEmail())) {
			idSet.add(e.getId());
			emailSet.add(e.getEmail());
			employees.add(e);
			System.out.println("New employee added");
			return true;
		}
		return false;
	}

	public synchronized static boolean idExists(int id) {
		return idSet.contains(id);
	}

	public synchronized static boolean emailExists(String email) {
		return emailSet.contains(email);
	}

	public synchronized static Employee getEmployee(int id) {
		System.out.println(employees.size());
		for (Employee e : employees) {
			if (e.getId() == id) {
				return e;
			}
		}
		return null;
	}

	public synchronized static Employee getEmployee(String email) {
		System.out.println(employees.size());
		for (Employee e : employees) {
			if (e.getEmail().equals(email)) {
				return e;
			}
		}
		return null;
	}

}
