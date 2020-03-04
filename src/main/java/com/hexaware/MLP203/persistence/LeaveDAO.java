package com.hexaware.MLP203.persistence;

import java.sql.Date;
import java.util.List;

import com.hexaware.MLP203.model.Leave;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface LeaveDAO {

  @SqlUpdate("insert into leaves values(:leave_id, :leave_from, :leave_to, : no_ldays, : lapply_date, : l_status)")
      void insertLeave(@Bind("leave_id") int leave_id, @Bind("leave_from") Date leave_from, @Bind("leave_to") Date leave_to,
                   @Bind("no_ldays") int no_ldays, @Bind("lapply_date") Date lapply_date, @Bind("l_status") String l_status);
  
  //to list all leaves
  @SqlQuery("Select * from Leave")
  List<LeaveDAO> listAllLeaves();

  //return all the leave details from leave table                 
  @SqlQuery("select * from Leave")
    @Mapper(LeaveMapper.class)
    List<Leave> list();
       
  //list all leave ids 
  @SqlQuery("select * from Leave = :leave_id")
    @Mapper(LeaveMapper.class)
       Leave list_EmployeesID(@Bind("leave_id") int leave_id);
  
  //counting number of leaves where leave_from or leave_to lies between start date and end date
  @SqlQuery("SELECT COUNT(*) FROM Leave "
      +    " WHERE empId = :empId AND (leave_from BETWEEN :leave_from AND :leave_to "
      +    " OR leave_to BETWEEN :leave_from AND :leave_to)")
    int count(@Bind("empID") int empID, @Bind("leave_from") Date leave_from,
       @Bind("leave_to") Date leave_to);
    
  //list the managers from all employees
  @SqlQuery("SELECT e1.empId FROM Employee e1  "
       + " JOIN Employee e2 ON e1.empId=e2.MgrId WHERE e2.empId =(SELECT empId FROM Leave "
       + "   WHERE leave_id=:leave_id)")
    int showManager(@Bind("leave_id") int leave_id);
  
  //list all pending leaves
  @SqlQuery("SELECT * FROM Leave WHERE "
       + " empId IN "
       + " (SELECT e2.empId FROM "
       + " Employee e1 INNER JOIN "
       + " Employee e2 ON e1.empId = e2.MgrId"
       + " WHERE e1.empId = :empId)  AND "
       + " l_status ='PENDING' ")
    @Mapper(LeaveMapper.class)
    List<LeaveDAO> pending(@Bind("empId") int empId);
 
  //list the leave history
  @SqlQuery("select * from Leave where empId= :empId")
  @Mapper(LeaveMapper.class)
  List<LeaveDAO> leaveHistory(@Bind("empId") int empId);
  
  //update leave table
  @SqlUpdate("UPDATE Leave SET leave_from = :leave_from, leave_to = :leave_to," + " no_ldays = :no_ldays, leave_type= :leave_type, leave_reason= :leave_reason WHERE " + "leave_id = :leave_id")
    void updateLeave(@Bind("leave_from") Date leave_from, @Bind("leave_to") Date leave_to,@Bind("no_ldays") int no_ldays,
                     @Bind("leave_type") String leave_type,@Bind("l_status") String l_status, @Bind("leave_reason") String leave_reason, 
                     @Bind("leave_id") int leave_id);

  //apply leave
  @SqlUpdate("insert into Leave values(: empId, : leave_from, : leave_to, : no_ldays, : lapply_date, :leave_type, :leave_reason, : l_status)")
    void apply_for_leave(@Bind("empId") int empId, @Bind("leave_from") String start_date, @Bind("leave_to") String end_date,
      @Bind("no_ldays") int no_ldays, @Bind("lapply_date") String lapply_date, @Bind("leave_type") String leave_type, @Bind("leave_reason") String leave_reason, @Bind("l_status") String l_status);
   
  //list employees history
  @SqlQuery("SELECT * FROM Leave = :empId")
  @Mapper(LeaveMapper.class)
  List<Leave> empHistory(@Bind("empId") int empId);

  //update leave statusa
  @SqlUpdate("UPDATE Leave SET leave_status: leave_status WHERE " + "leave_id = :leave_id")
    void comment(@Bind("leave_status") String leave_status, @Bind("leave_id") int leave_id);
}
