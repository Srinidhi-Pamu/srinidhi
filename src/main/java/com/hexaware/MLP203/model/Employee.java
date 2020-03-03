package com.hexaware.MLP203.model;

import com.hexaware.MLP203.persistence.DbConnection;
import com.hexaware.MLP203.persistence.EmployeeDAO;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.sql.Date;
import java.util.List;

/**
 * Employee class to store employee personal details.
 * @author hexware
 */
public class Employee {

  /**
   * empId to store employee id.
   */
  private int empId;
  private String f_name;
  private String l_name;
  private String dept;
  private String email;
  private long contact;
  private String password;

  //default constructor
  public Employee(){}

  //parameterized constructor
  public Employee(final int EmpId, final String f_name, final String l_name, final String dept, final String email, final long contact, final String pwd){
    this.empId=EmpId;
    this.f_name=f_name;
    this.l_name=l_name;
    this.dept=dept;
    this.email=email;
    this.contact=contact;
    this.password=pwd;
  }

  //getters

   /**
   * Gets the EmployeeId.
   * @return this Employee's ID.
   */
  public final int getEmpId() {
    return empId;
  }
   
  public final String getf_name(){
    return f_name;
  }

  public final String getl_name(){
    return l_name;
  }

  public final String getdept(){
    return dept;
  }

  public final String getemail(){
     return email;
  }

  public final long getcontact(){
    return contact;
  }

  public final String getpassword(){
    return password;
  }

  //setters

  /**
   *
   * @param argEmpId to set employee id.
   */
  public final void setEmpId(final int argEmpId) {
    this.empId = argEmpId;
  }

  public final void setf_name(final String f_name){
    this.f_name=f_name;
  }

  public final void setl_name(final String l_name){
     this.l_name=l_name;
  }

  public final void setdept(final String dept){
    this.dept=dept;
  }

  public final void setemail(final String email){
    this.email=email;
  }

  public final void setcontact(final long contact){
    this.contact=contact;
  }

  public final void setpassword(final String password){
    this.password=password;
  }

  //equals
  @Override
  public final boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Employee emp = (Employee) obj;
    if (Objects.equals(empId, emp.empId) && Objects.equals(f_name, emp.f_name) && Objects.equals(l_name, emp.l_name) && Objects.equals(dept, emp.dept) && Objects.equals(email, emp.email) && Objects.equals(contact, emp.contact) && Objects.equals(password, emp.password)) {
      return true;
    }
    return false;
  }

  //toString
  @Override
  public String toString() {
    return "empid: " + empId + "first name: " + f_name + "last name: " + l_name + "department: " + dept + "email: " + email + "contact: " + contact + "password: " + password;
  }

  
  //hashcode
  @Override
  public final int hashCode() {
    return Objects.hash(empId);
  }

  /**
   * @param argEmpId to initialize employee id.
   */
  public Employee(final int argEmpId) {
    this.empId = argEmpId;
  }




  /**
   * The dao for employee.
   */
  private static EmployeeDAO dao() {
    DbConnection db = new DbConnection();
    return db.getConnect().onDemand(EmployeeDAO.class);
  }

  /**
   * list all employee details.
   * @return all employees' details
   */
  public static Employee[] listAll() {

    List<Employee> es = dao().list();
    return es.toArray(new Employee[es.size()]);
  }

  /**
   * list employee details by id.
   * @param empID id to get employee details.
   * @return Employee
   */
  public static Employee listById(final int empID) {
    return dao().find(empID);
  }

}
