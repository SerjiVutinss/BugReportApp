package org.serji.sw.server.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.serji.sw.server.models.Employee;

/**
 * 
 * 
 * @author Justin
 *
 */
public abstract class EmployeeData {

	private volatile static List<Employee> employeeList = new ArrayList<>();
	private volatile static Set<Integer> empIdSet = new HashSet<>();
	private volatile static Set<String> emailSet = new HashSet<>();

	public synchronized static List<Employee> getEmployees() {
		return employeeList;
	}

	public synchronized static void setEmployees(List<Employee> empList) {
		EmployeeData.employeeList = empList;
		rebuildDataStructures();
	}

	/**
	 * Rebuild all data structures - empIdSet, emailSet and employees List
	 * 
	 * Before an employee may be added to the employeeList, we must ensure that the
	 * ID and email have not already been added, i.e. they do not exist in the
	 * respective sets
	 * 
	 * Once it has been verified that both the new employee id and email are unique,
	 * add each to the sets and then add the employee to the list
	 * 
	 * This method is called after reloading data from the data file, therefore,
	 * each employee should already have an ID, i.e. a new ID should not be assigned
	 */
	public synchronized static void rebuildDataStructures() {

		// reinitialize these sets
		empIdSet = new HashSet<>();
		emailSet = new HashSet<>();

		// clone the list to avoid ConcurrentModificationException
		List<Employee> employeesClone = new ArrayList<>();
		for (Employee e : employeeList) {
			employeesClone.add(e);
		}

		// now reinitialize the static list to add the new employees to
		employeeList = new ArrayList<>();
		// try to add each employee to the employee list
		for (Employee e : employeesClone) {
			// ensure that this employee's ID and email are unique within the system
			if (!empIdSet.contains(e.getId()) && !emailSet.contains(e.getEmail())) {
				empIdSet.add(e.getId()); // add id to set
				emailSet.add(e.getEmail()); // add email to set
				employeeList.add(e); // finally add the employee object to the list
				System.out.println("New employee added");
			}
		}
	}

	/**
	 * Method used to register a new employee and assign it an ID
	 * 
	 * @param e the employee object to be added to the static list of employees
	 * @return true if employee was successfully added to the list
	 */
	public synchronized static boolean addEmployee(Employee e) {

		if (e.getId() < 0) {
			e.setId(getValidEmpId()); // set a unique id for this employee
		}

		if (!empIdSet.contains(e.getId()) && !emailSet.contains(e.getEmail())) {
			empIdSet.add(e.getId());
			emailSet.add(e.getEmail());
			employeeList.add(e);
			System.out.println("New employee added");
			return true;
		}
		return false; // employee ID or email was not unique
	}

	/**
	 * Returns the employee with the supplied ID if that employee exists within the
	 * static list
	 * 
	 * TODO: optimize to first check the set?
	 * 
	 * @param id the unique ID of the employee to be returned
	 * @return the employee with the supplied ID if it exists, else return null
	 */
	public synchronized static Employee getEmployee(int id) {
		System.out.println(employeeList.size());
		for (Employee e : employeeList) {
			if (e.getId() == id) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Returns the employee with the supplied email if that employee exists within
	 * the static list
	 * 
	 * TODO: optimize to first check the set?
	 * 
	 * @param email the unique email of the employee to be returned
	 * @return the employee with the supplied email if it exists, else return null
	 */
	public synchronized static Employee getEmployee(String email) {
		for (Employee e : employeeList) {
			if (e.getEmail().equals(email)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Method to return a new, unique ID (integer) to be assigned to a newly
	 * registered employee
	 * 
	 * @return a new valid employee ID
	 */
	private synchronized static int getValidEmpId() {
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

	/**
	 * Returns true if the suppled ID already exists in the system
	 * 
	 * @param id the ID to check
	 * @return true if ID exists in the system, else false
	 */
	private synchronized static boolean empIdExists(int id) {
		return empIdSet.contains(id);
	}

	/**
	 * Returns true if the suppled email already exists in the system
	 * 
	 * @param email the email to check
	 * @return true if email exists in the system, else false
	 */
	public synchronized static boolean emailExists(String email) {
		return emailSet.contains(email);
	}
}
