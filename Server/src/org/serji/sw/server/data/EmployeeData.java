package org.serji.sw.server.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.serji.sw.server.models.Employee;

public class EmployeeData {

	private volatile static List<Employee> employees = new ArrayList<>();
	private volatile static Set<Integer> empIdSet = new HashSet<>();
	private volatile static Set<String> emailSet = new HashSet<>();

	public synchronized static List<Employee> getEmployees() {
		return employees;
	}

	public synchronized static void setEmployees(List<Employee> empList) {
		EmployeeData.employees = empList;
		rebuildDataStructures();
	}

	public synchronized static void rebuildDataStructures() {

		empIdSet = new HashSet<>();
		emailSet = new HashSet<>();

		List<Employee> employeesClone = new ArrayList<>();
		for (Employee e : employees) {
			employeesClone.add(e);
		}

		employees = new ArrayList<>();

		for (Employee e : employeesClone) {

			if (!empIdSet.contains(e.getId()) && !emailSet.contains(e.getEmail())) {
				empIdSet.add(e.getId());
				emailSet.add(e.getEmail());
				employees.add(e);
				System.out.println("New employee added");
			}
		}
	}

	public synchronized static boolean addEmployee(Employee e) {

//		System.out.println("Trying to add employee");

		if (e.getId() < 0) {
			e.setId(getValidEmpId()); // set a unique id for this employee
		}
//		System.out.println(e.getId());
//		System.out.println(e.getEmail());

		if (!empIdSet.contains(e.getId()) && !emailSet.contains(e.getEmail())) {
			empIdSet.add(e.getId());
			emailSet.add(e.getEmail());
			employees.add(e);
			System.out.println("New employee added");
			return true;
		}
		return false;
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

	private static int getValidEmpId() {
		boolean isValidId = false;
		int uniqueId = 0;
		while (!isValidId) {
			if (EmployeeData.empIdExists(uniqueId)) {
				uniqueId++;
			} else {
				isValidId = true;
			}
		}
		return uniqueId;
	}

	public synchronized static boolean empIdExists(int id) {
		return empIdSet.contains(id);
	}

	public synchronized static boolean bugIdExists(int id) {
		return empIdSet.contains(id);
	}

	public synchronized static boolean emailExists(String email) {
		return emailSet.contains(email);
	}
}
