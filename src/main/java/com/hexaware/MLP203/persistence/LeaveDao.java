package com.hexaware.MLP203.persistence;

import java.sql.Date;
import java.util.List;

import com.hexaware.MLP203.model.Leave;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface LeaveDao {

  @SqlUpdate("insert into leaves values(:leave_id, :leave_from, :leave_to, : no_ldays, : lapply_date, : l_status)")
      void insertLeave(@Bind("leave_id") int leave_id, @Bind("leave_from") Date leave_from, @Bind("leave_to") Date leave_to,
                   @Bind("no_ldays") int no_ldays, @Bind("lapply_date") Date lapply_date, @Bind("l_status") String l_status);

  //return all the leave details from leave table                 
  @SqlQuery("select * from leave")
    @Mapper(LeaveMapper.class)
    List<Leave> list();
       
  //list all leave ids 
  @SqlQuery("select * from Leave = :leave_id")
    @Mapper(LeaveMapper.class)
       Leave list_EmployeesID(@Bind("leave_id") int leave_id);
  
  //counting number of leaves where leave_from or leave_to lies between start date and end date
  @SqlQuery("SELECT COUNT(*) FROM Leave "
      +    " WHERE empId = :empID AND (leave_from BETWEEN :leave_from AND :leave_to "
      +    " OR leave_to BETWEEN :leave_from AND :leave_to)")
    int count(@Bind("empID") int empID, @Bind("leave_from") Date leave_from,
       @Bind("leave_to") Date leave_to);
    
  //list the managers from all employees
  @SqlQuery("SELECT E1.empId FROM Employee E1  "
       + " JOIN Employee E2 ON E1.empId=E2.MgrId WHERE E2.empId =(SELECT empId FROM Leave "
       + "   WHERE leave_id=:leave_id)")
    int showManager(@Bind("leave_id") int leave_id);
  
  //list all pending leaves
  @SqlQuery("SELECT * FROM Leave WHERE "
       + " empId IN "
       + " (SELECT E2.empId FROM "
       + " Employee E1 INNER JOIN "
       + " Employee E2 ON E1.empId = E2.MgrId"
       + " WHERE E1.empId = :empid)  AND "
       + " l_status ='PENDING' ")
    @Mapper(LeaveMapper.class)
    List<LeaveDao> pending(@Bind("empId") int empid);
 
  //list the leave history
  @SqlQuery("select * from Leave where empId= :empId")
  @Mapper(LeaveMapper.class)
  List<LeaveDao> leaveHistory(@Bind("empId") int empId);
  
  //update leave table
  @SqlUpdate("UPDATE Leave SET leave_from = :leave_from, leave_to = :leave_to," + " no_ldays = :no_ldays, leave_type= :leave_type, leave_reason= :leave_reason WHERE " + "leave_id = :leave_id")
    void updateLeave(@Bind("leave_from") Date leave_from, @Bind("leave_to") Date leave_to,@Bind("no_ldays") int no_ldays,
                     @Bind("leave_type") String leave_type,@Bind("l_status") String l_status, @Bind("leave_reason") String leave_reason, 
                     @Bind("leave_id") int leave_id);
}