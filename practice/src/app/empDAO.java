package com.hexaware.MLP203.persistence;

import java.util.ArrayList;
import java.util.List;

import com.hexaware.MLP203.model.Employee;

public class EmployeeDAO {

private static List<Employee> employees = new ArrayList<>();

static {
loadEmployees();
}

public Employee find(int empId) {
Employee emp = null;
for (Employee employee : employees) {
if (employee.getEmpId() == empId) {
emp = employee;
}
}
return emp;
}

public void updateEmployee(Employee emp) {
for (Employee employee : employees) {
if (employee.getEmpId() == emp.getEmpId()) {
if(emp.getFname() != null && !emp.getFname().isEmpty()) {
employee.setFname(emp.getFname());
} else if(emp.getContactno() != 0) {
employee.setContactno(emp.getContactno());
}
// employee = emp;
}
}
}

public List<Employee> getEmployees() {
loadEmployees();
return employees;
}

private static void loadEmployees() {
employees.clear();
employees.add(new Employee(1, "Brian", "Hall", "Java", "brian@hexaware.com", 111111111));
employees.add(new Employee(2, "Shyam", "krishna", "Testing", "shyamk@hexaware.com", 123456789));
employees.add(new Employee(3, "Raj", "sekhar", "Testing", "rajs@hexaware.com", 234567890));
employees.add(new Employee(4, "Eswar", "karthik", "Java", "eswark@hexaware.com", 345678901));
employees.add(new Employee(5, "Sai", "kiran", "Admin", "saik@hexaware.com", 456789012));
employees.add(new Employee(6, "Saurav", "jain", "HR", "sauravj@hexaware.com", 567890123));

}

}
