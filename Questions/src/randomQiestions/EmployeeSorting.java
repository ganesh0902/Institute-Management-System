package randomQiestions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SortByfirstName implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {		
		return Integer.compare(o1.getRoll(), o2.getRoll());
	}
}

class Employee{

	private int roll;
	private String firstName;
	private String lastName;
	private int salary;

	public Employee(int roll, String firstName, String lastName, int salary) {
		this.roll = roll;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
	}
	
	public void setRoll(int roll) {
		this.roll = roll;
	}

	public int getRoll() {
		return this.roll;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getSalary() {
		return this.salary;
	}

	public String toString() {
		return "employee=[ roll ='"+this.getRoll()+"' firstName = '" + this.firstName + "' lastName = '" + this.lastName + "' salary ='"
				+ this.salary + "']";
	}
}

public class EmployeeSorting {

	public static void main(String[] args) {

		List<Employee> employee = Arrays.asList(new Employee(10, "Ganesh", "Sakhare", 2000),
				new Employee(1, "Shubham", "Shinde", 2000), new Employee(12, "ajay", "patil", 2000),
				new Employee(9, "nandi", "kishor", 2000), new Employee(4, "rohit", "mar", 2000));

		Collections.sort(employee,new SortByfirstName());

		System.out.println(employee);
	}
}