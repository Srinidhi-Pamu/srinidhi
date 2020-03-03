package com.hexaware.MLP203.model;

import java.util.List;

import com.hexaware.MLP203.persistence.DbConnection;
import com.hexaware.MLP203.persistence.EmployeeDAO;

/**
 * Employee class to store employee personal details.
 *
 * @author hexware
 */
public class Employee {

public static final String Leave_Id = null;
/**
* empId to store employee id.
*/
private int empId;
private String fname;
private String lname;
private String dept;
private String mail;
private int contactno;
private int mgrId;
private String pw;

public Employee(int empId) {
super();
this.empId = empId;
}

public Employee(int empId, String fname, String lname, String dept, String mail, int contactno) {
super();
this.empId = empId;
this.fname = fname;
this.lname = lname;
this.dept = dept;
this.mail = mail;
this.contactno = contactno;
}

/**
* @return the empId
*/
public int getEmpId() {
return empId;
}

/**
* @param empId the empId to set
*/
public void setEmpId(int empId) {
this.empId = empId;
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
return lname;
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
return dept;
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
return mail;
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
return contactno;
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
public String getPw() {
return pw;
}

/**
* @param pw the pw to set
*/
public void setPw(String pw) {
this.pw = pw;
}

/**
* The dao for employee.
*/

private static EmployeeDAO dao() {
DbConnection db = new DbConnection();
// return db.getConnect().onDemand(EmployeeDAO.class); return new
return new EmployeeDAO();
}

/**
* list all employee details.
*
* @return all employees' details
*/
public static Employee[] listAll() {
List<Employee> es = dao().getEmployees();
return es.toArray(new Employee[es.size()]);
}

/**
* list employee details by id.
*
* @param empID id to get employee details.
* @return Employee
*/
public static Employee listById(final int empID) {
return dao().find(empID);
}

/*
* (non-Javadoc)
*
* @see java.lang.Object#hashCode()
*/
@Override
public int hashCode() {
final int prime = 31;
int result = 1;
result = prime * result + contactno;
result = prime * result + ((dept == null) ? 0 : dept.hashCode());
result = prime * result + empId;
result = prime * result + ((fname == null) ? 0 : fname.hashCode());
result = prime * result + ((lname == null) ? 0 : lname.hashCode());
result = prime * result + ((mail == null) ? 0 : mail.hashCode());
result = prime * result + ((pw == null) ? 0 : pw.hashCode());
return result;
}

/*
* (non-Javadoc)
*
* @see java.lang.Object#equals(java.lang.Object)
*/
@Override
public boolean equals(Object obj) {
if (this == obj)
return true;
if (obj == null)
return false;
if (getClass() != obj.getClass())
return false;
Employee other = (Employee) obj;
if (contactno != other.contactno)
return false;
if (dept == null) {
if (other.dept != null)
return false;
} else if (!dept.equals(other.dept))
return false;
if (empId != other.empId)
return false;
if (fname == null) {
if (other.fname != null)
return false;
} else if (!fname.equals(other.fname))
return false;
if (lname == null) {
if (other.lname != null)
return false;
} else if (!lname.equals(other.lname))
return false;
if (mail == null) {
if (other.mail != null)
return false;
} else if (!mail.equals(other.mail))
return false;
if (pw == null) {
if (other.pw != null)
return false;
} else if (!pw.equals(other.pw))
return false;
return true;
}

/*
* (non-Javadoc)
*
* @see java.lang.Object#toString()
*/
@Override
public String toString() {
return "Employee [empId=" + empId + ", fname=" + fname + ", lname=" + lname + ", dept=" + dept + ", mail="
+ mail + ", contactno=" + contactno + "]";
}

/*
* //getter
*
* int getid(){ return empId; }
*
* String getfname(){ return e_fname; } String getlname(){ return e_lname; }
*
* String getdept() { return e_dept; } String getmail() { return e_mail; } int
* getcontactno(){ return e_contactno; }
*
* String getpw(){ return e_pw; }
*
*
* //setter
*
* void setid(int empId) { this.empId= empId; } void setfname(String fname) {
* this.e_fname= fname; } void setLname(String lname) { this.e_lname=lname;
*
* } void setdept(String dept) { this.e_dept=dept; } void setmail(String mail) {
* this.e_mail=mail;
*
* } void setcontactno(int contactno) { this.e_contactno=contactno; } void
* setpw(String pw) { this.e_pw=pw; }
*
* @Override public String toString() { return "empId:"+
* empId+"e_fname:"+e_fname+"e_lname:"+e_lname+"e_dept:"+e_dept+
* "e_mail:"+e_mail+"e_contactno:"+e_contactno+"e_pw:"+e_pw; }
*
*
*
* //equals
*
* @Override public final boolean equals(final Object obj) { if (obj == null) {
* return false; } if (getClass() != obj.getClass()) { return false; } Employee
* emp = (Employee) obj; if (Objects.equals(empId, emp.empId) &&
* Objects.equals(e_fname, emp.e_fname) && Objects.equals(e_lname, emp.e_lname)
* && Objects.equals(e_dept, emp.e_dept) && Objects.equals(e_mail, emp.e_mail)
* && Objects.equals(e_contactno, emp.e_contactno) && Objects.equals(e_pw,
* emp.e_pw)) { return true; } return false; }
*
* @Override public final int hashCode() { return Objects.hash(empId); }
*
*//**
* @param argEmpId to initialize employee id.
*/
/*
* public Employee(final int argEmpId) { this.empId = argEmpId; }
*
*//**
* Gets the EmployeeId.
*
* @return this Employee's ID.
*/
/*
* public final int getEmpId() { return empId; }
*
*//**
*
* @param argEmpId to set employee id.
*/
/*
* public final void setEmpId(final int argEmpId) { this.empId = argEmpId; }
*
*//**
* The dao for employee.
*/
/*
* private static EmployeeDAO dao() { DbConnection db = new DbConnection();
* //return db.getConnect().onDemand(EmployeeDAO.class); return new
* EmployeeDAO(); }
*
*//**
* list all employee details.
*
* @return all employees' details
*/
/*
* public static Employee[] listAll() {
*
* List<Employee> es = dao().getEmployees(); return es.toArray(new
* Employee[es.size()]); }
*
*//**
* list employee details by id.
*
* @param empID id to get employee details.
* @return Employee
*//*
* public static Employee listById(final int empID) { return dao().find(empID);
* }
*/

}
