package com.hexaware.MLP203.persistence;

import java.sql.Date;
import java.util.List;

import com.hexaware.MLP203.model.Leave;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface LeaveDAO {

    // @SqlUpdate("insert into leaves values(: leave_id, : empId, : leave_from, : leave_to, : no_ldays, : lapply_date, :leave_type, :leave_reason, : l_status)")
    //   void insertLeave(@Bind("leave_id") int leave_id, @Bind("empId") int empId, @Bind("leave_from") Date leave_from, @Bind("leave_to") Date leave_to,
    //                @Bind("no_ldays") int no_ldays, @Bind("lapply_date") Date lapply_date, @Bind("leave_type") String leave_type, @Bind("leave_reason") String leave_reason, @Bind("l_status") String l_status);

    @SqlQuery("select * from leaves")
      List<LeaveDAO> listAllLeaves();
               
    @SqlQuery("select * from leaves where leave_id = :leave_id")
    @Mapper(LeaveMapper.class)
    Leave list_EmployeesID(@Bind("leave_id") int leave_id);


    @SqlQuery("SELECT * FROM leaves")
    @Mapper(LeaveMapper.class)
    List<Leave> list();

    @SqlQuery("select * from leaves where empId = :empId")
    @Mapper(LeaveMapper.class)
    List<Leave> empHistory(@Bind("empId") int empId);

    @SqlQuery("SELECT * FROM leaves WHERE "
      + " empId IN "
      + " (SELECT e2.empId FROM "
      + " Employee e1 INNER JOIN "
      + " Employee e2 ON e1.empId = e2.mgr_id "
      + " WHERE e2.empId = :empId)  AND "
      + " leave_status = 'pending' ")

    @Mapper(LeaveMapper.class)
    List<Leave> pending(@Bind("empId") int empId);


     /**
   * return Manager the details of the Leave Id.
   * @param leave_id the id of the employee
   * @return the ManagerID value
   */

    @SqlQuery("SELECT e1.empId FROM Employee e1  "
    + " JOIN Employee e2 ON e1.empId=e2.mgrId WHERE e2.empId =(SELECT empId FROM leaves "
    + "   WHERE leave_id =:leave_id)")
    int showManager(@Bind("leave_id") int leave_id);


    @SqlUpdate("UPDATE leaves SET leave_status: leave_status WHERE leave_id = :leave_id")
    void comment(@Bind("leave_status") String leave_status, @Bind("leave_id") int leave_id);


    @SqlQuery("SELECT COUNT(*) FROM leaves "
    +    " WHERE empId= :empId AND (leave_from BETWEEN :leave_from AND :leave_to "
    +    " OR leave_to BETWEEN :leave_from AND :leave_to)"
    )
    // int count(@Bind("empId") int empId, @Bind("leave_from") String start_date,
    // @Bind("leave_to") String end_date);


    @SqlUpdate("UPDATE leaves SET leave_from = :leave_from, leave_to = :leave_to,"
        + " no_ldays = :no_ldays, leave_type= :leave_type, leave_reason= :leave_reason WHERE "
        + "leave_id = :leave_id")
    String update_leave(
        @Bind("leave_from") String start_date,
        @Bind("leave_to") String end_date,
        @Bind("no_ldays") int no_ldays,
        @Bind("leave_type") String leave_type,
        @Bind("l_status") String l_status,
        @Bind("leave_reason") String leave_reason,
        @Bind("leave_id") int leave_id);




    @SqlUpdate("insert into leaves values(:empId, :leave_from, :leave_to, :no_ldays, :leave_type, :l_status,  :leave_reason, :lapply_date)")
    void apply_for_leave(@Bind("empId") int empId, @Bind("leave_from") String start_date, @Bind("leave_to") String end_date,
      @Bind("no_ldays") int no_ldays, @Bind("leave_type") String leave_type, @Bind("l_status") String l_status, @Bind("leave_reason") String leave_reason, @Bind("lapply_date") String lapply_date);
}
