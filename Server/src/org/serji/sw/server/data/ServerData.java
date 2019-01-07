//package org.serji.sw.server.data;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.serji.sw.server.models.BugReport;
//import org.serji.sw.server.models.Employee;
//
//public class ServerData {
//
////	private volatile static List<Employee> employees = new ArrayList<>();
////	private volatile static Set<Integer> empIdSet = new HashSet<>();
////	private volatile static Set<String> emailSet = new HashSet<>();
////
////	private volatile static List<BugReport> bugReports = new ArrayList<>();
////	private volatile static Set<Integer> bugIdSet = new HashSet<>();
//////	private static Map<Integer, String> employeeEmailIdMap = new HashMap<Integer, String>();
////
////	public synchronized static List<Employee> getEmployees() {
////		return employees;
////	}
////
////	public synchronized static List<BugReport> getBugReports() {
////		return bugReports;
////	}
////
////	public synchronized static void setEmployees(List<Employee> empList) {
////		ServerData.employees = empList;
////
////		for (Employee e : ServerData.employees) {
////
////			if (!empIdSet.contains(e.getId()) && !emailSet.contains(e.getEmail())) {
////				empIdSet.add(e.getId());
////				emailSet.add(e.getEmail());
////				employees.add(e);
////				System.out.println("New employee added");
////			}
////		}
////	}
////
////	public synchronized static boolean addEmployee(Employee e) {
////
//////		System.out.println("Trying to add employee");
////
////		if (e.getId() < 0) {
////			e.setId(getValidEmpId()); // set a unique id for this employee
////		}
//////		System.out.println(e.getId());
//////		System.out.println(e.getEmail());
////
////		if (!empIdSet.contains(e.getId()) && !emailSet.contains(e.getEmail())) {
////			empIdSet.add(e.getId());
////			emailSet.add(e.getEmail());
////			employees.add(e);
////			System.out.println("New employee added");
////			return true;
////		}
////		return false;
////	}
////
////	public synchronized static boolean addBugReport(BugReport br) {
////
////		br.setId(getValidBugId()); // set a unique id for this employee
////
////		if (!bugIdSet.contains(br.getId())) {
////			bugIdSet.add(br.getId());
////			bugReports.add(br);
////			System.out.println("New Bug Report added");
////			return true;
////		}
////		return false;
////	}
////
////	public synchronized static boolean empIdExists(int id) {
////		return empIdSet.contains(id);
////	}
////
////	public synchronized static boolean bugIdExists(int id) {
////		return empIdSet.contains(id);
////	}
////
////	public synchronized static boolean emailExists(String email) {
////		return emailSet.contains(email);
////	}
////
////	public synchronized static Employee getEmployee(int id) {
////		System.out.println(employees.size());
////		for (Employee e : employees) {
////			if (e.getId() == id) {
////				return e;
////			}
////		}
////		return null;
////	}
////
////	public synchronized static Employee getEmployee(String email) {
////		System.out.println(employees.size());
////		for (Employee e : employees) {
////			if (e.getEmail().equals(email)) {
////				return e;
////			}
////		}
////		return null;
////	}
////
////	private static int getValidEmpId() {
////		boolean isValidId = false;
////		int uniqueId = 0;
////		while (!isValidId) {
////			if (ServerData.empIdExists(uniqueId)) {
////				uniqueId++;
////			} else {
////				isValidId = true;
////			}
////		}
////		return uniqueId;
////	}
////
////	private static int getValidBugId() {
////		boolean isValidId = false;
////		int uniqueId = 0;
////		while (!isValidId) {
////			if (ServerData.bugIdExists(uniqueId)) {
////				uniqueId++;
////			} else {
////				isValidId = true;
////			}
////		}
////		return uniqueId;
////	}
//
//}
