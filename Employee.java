package com.hexaware.MLP203.model;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

import com.hexaware.MLP203.persistence.DbConnection;
import com.hexaware.MLP203.persistence.EmployeeDAO;

import java.util.Objects;
import java.util.List;

import lombok.EqualsAndHashCode;
/*
 * @author hexware
 */
public class Employee {

  public static final String Leave_Id = null;
  /**
  * empId to store employee id.
  */
  private int empId;
  private int mgr_id;
  private String fname;
  private String lname;
  private String dept;
  private String mail;
  private int contactno;
  private int avail_leaves;
  private Date doj; 

  // public Employee(int empId) {
  
  // this.empId = empId;
  // }

  public Employee(int empId,int mgr_id, String fname, String lname, String dept, String mail, int contactno,int avail_leaves,Date doj) {
  
  this.empId = empId;
  this.mgr_id=mgr_id;
  this.fname = fname;
  this.lname = lname;
  this.dept = dept;
  this.mail = mail;
  this.contactno = contactno;
  this.avail_leaves=avail_leaves;
  this.doj=doj;
  }

  public Employee(int i) {
}

/**
  * @return the empId
  */
  public int getEmpId() {
  return this.empId;
  }

  /**
  * @param empId the empId to set
  */
  public void setEmpId(int empId) {
  this.empId = empId;
  }

  public int getMgr_id() {
    return mgr_id;
  }

  public void setMgr_id(int mgr_id) {
    this.mgr_id = mgr_id;
  }

  /**
  * @return the fname
  */
  public String getFname() {
  return fname;
  }

  /**
  * @param fname the fname to set
  */
  public void setFname(String fname) {
  this.fname = fname;
  }

  /**
  * @return the lname
  */
  public String getLname() {
  return this.lname;
  }

  /**
  * @param lname the lname to set
  */
  public void setLname(String lname) {
  this.lname = lname;
  }

  /**
  * @return the dept
  */
  public String getDept() {
  return this.dept;
  }

  /**
  * @param dept the dept to set
  */
  public void setDept(String dept) {
  this.dept = dept;
  }

  /**
  * @return the mail
  */
  public String getMail() {
  return this.mail;
  }

  /**
  * @param mail the mail to set
  */
  public void setMail(String mail) {
  this.mail = mail;
  }

  /**
  * @return the contactno
  */
  public int getContactno() {
  return this.contactno;
  }

  /**
  * @param contactno the contactno to set
  */
  public void setContactno(int contactno) {
  this.contactno = contactno;
  }

  /**
  * @return the pw
  */
  public void setavailleaves(int avail_leaves)
  {
    this.avail_leaves=avail_leaves;
  }

  public int getavailleaves()
  {
    return this.avail_leaves;
  }

  public void setdoj(Date doj)
  {
    this.doj=doj;
  }

  public Date getdoj()
  {
  return this.doj;
  }


  /**
  * The dao for employee.
  */



  /*
  * (non-Javadoc)
  *
  * @see java.lang.Object#hashCode()
  */

  /*
  * (non-Javadoc)
  *
  * @see java.lang.Object#toString()
  */
  @Override
  public String toString() {
  return "Employee [empId=" + empId +" " + "mgrId="+ mgr_id + ", fname=" + fname + ", lname=" + lname + ", dept=" + dept + ", mail="
  + mail + ", contactno=" + contactno + "avail_leaves="+avail_leaves + "doj=" + doj+ "]";
  }

  //equals
  @Override 
  public final boolean equals(final Object obj) { if (obj == null) 
    {
  return false; } if (getClass() != obj.getClass()) { return false; } Employee
  emp = (Employee) obj; if (Objects.equals(empId, emp.empId) 
  &&Objects.equals(mgr_id,emp.mgr_id)
  &&Objects.equals(fname, emp.fname) 
  && Objects.equals(lname, emp.lname)
  && Objects.equals(dept, emp.dept)
  && Objects.equals(mail, emp.mail)
  && Objects.equals(contactno, emp.contactno)
   && Objects.equals(avail_leaves,emp.avail_leaves)
  &&Objects.equals(doj,emp.doj))

  { return true; } return false; }

  @Override 
  public final int hashCode() { return Objects.hash(empId,mgr_id,fname,lname,dept,mail,contactno,avail_leaves,doj); }

 
  private static EmployeeDAO dao() { 
    DbConnection db = new DbConnection();
  return db.getConnect().onDemand(EmployeeDAO.class); 
  }


  public static Employee[] listAll(){
    List<Employee> es=dao().list();
    return es.toArray(new Employee [es.size()]);
  }

  public static Employee listById(final int empId) {
    return dao().find(empId);
  }

  public static Employee[] listManagerById(final int mgrId) {
    List<Employee> es = dao().findManager(mgrId);
    return es.toArray(new Employee[es.size()]);
  }


//   public static void updateEmployee(int contact,String email,int emp_id){
//     dao().updateEmployee(contact, email ,emp_id);
//  }
 


//  public static void EmployeeInfo(int emp_id)
//  {
//    dao().EmployeeInfo(emp_id);
//  }

}
