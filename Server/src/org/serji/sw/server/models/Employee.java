package org.serji.sw.server.models;

import java.io.Serializable;

public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3709446814518181321L;

	private int id; // must be unique
	private String email; // must be unique
	private String name;
	private String department;

	public Employee() {

	}

	public Employee(String name, String email, String department) {
//		this.id = id;
		this.name = name;
		this.email = email;
		this.department = department;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Employee csvStringToEmployee(String line) {
		String[] data = line.split(";");
		Employee e = new Employee();

		e.setId(Integer.parseInt(data[0]));
		e.setEmail(data[1].trim());
		e.setName(data[2].trim());
		e.setDepartment(data[3].trim());

		return e;
	}

	public static String employeeToCsvString(Employee e) {
		StringBuilder result = new StringBuilder();
		result.append(e.getId() + ";");
		result.append(e.getEmail() + ";");
		result.append(e.getName() + ";");
		result.append(e.getDepartment());
		result.append("\n");

		return result.toString();
	}

}
