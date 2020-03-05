package com.hexaware.MLP203.persistence;

import com.hexaware.MLP203.model.Employee;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

/**
 * The DAO class for employee.
 */
public interface EmployeeDAO  {


 /**
   * return all the details of all the employees.
   * @return the employee array
   */
  @SqlQuery("SELECT * FROM EMPLOYEE")
  @Mapper(EmployeeMapper.class)
  List<Employee> list();

  // Fetch PArticular Employee Information //
  @SqlQuery("SELECT * FROM EMPLOYEE where empId = :empId")
  @Mapper(EmployeeMapper.class)
  Employee find(@Bind("empId") int empId);



 // Fetch Manager Details//
  @SqlQuery("select * from employee e, employee f where e.empId = f.mgr_id and e.mgr_id = :mgrid")
  @Mapper(EmployeeMapper.class)
  List<Employee> findManager(@Bind("mgrid") int mgrId);
 
  //increment ///
 
  @SqlUpdate("UPDATE Employee SET avail_leaves = avail_leaves +:no_ldays WHERE empId = :empId")
  @Mapper(EmployeeMapper.class)
  void increment(@Bind("empId") int empId, @Bind("no_ldays") int no_ldays);

 
  //decrement //
 
  @SqlUpdate("UPDATE Employee SET avail_leaves = avail_leaves -:no_ldays WHERE empId = :empID")
  @Mapper(EmployeeMapper.class)
  void decrement(@Bind("empID") int empID, @Bind("no_ldays") int leaveTaken);
 
 
 

  /**
  * close with no args is used to close the connection.
  */
  void close();


}