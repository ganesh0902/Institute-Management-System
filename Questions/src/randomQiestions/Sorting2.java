package randomQiestions;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


class SortingByName implements Comparator<Employee2323>
{

	@Override
	public int compare(Employee2323 o1, Employee2323 o2) {
		
		return o1.getEmpName().compareTo(o2.getEmpName());
	}
	}

class Employee2323 implements Comparable<Employee2323>{

	private int empId;
	private String empName;
	private String city;
	private int salary;

	Employee2323(int empId, String empName, String city, int salary) {
		this.empId = empId;
		this.empName = empName;
		this.city = city;
		this.salary = salary;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getEmpId() {
		return this.empId;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpName() {
		return this.empName;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return this.city;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getSalary() {
		return this.salary;
	}

	public String toString()
	{
		return "Employee: [empId = '"+empId+"' empName = '"+empName+"' city ='"+city+"' salary = '"+salary+"' ]";
	}

	@Override
	public int compareTo(Employee2323 o) {		
		
		return Integer.compare(this.salary, o.salary);
	}	
}

public class Sorting2 {

	public static void main(String[] str) {
		
		List<Employee2323> emps = Arrays.asList(
				new Employee2323(12,"Ganesh","pune",200),
				new Employee2323(13,"Shubham","pune",400),
				new Employee2323(14,"Ajay","Pune",1100),
				new Employee2323(15,"Tusahr","Mumbai",600));
		
		
		Collections.sort(emps,new SortingByName());
		
		System.out.println(emps);
		
		
	}					
}
