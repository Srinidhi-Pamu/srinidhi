package com.hexaware.MLP203.model;


import com.hexaware.MLP203.persistence.DbConnection;
import com.hexaware.MLP203.persistence.EmployeeDAO;
import com.hexaware.MLP203.persistence.LeaveDAO;

import java.util.Objects;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Leave{

    private int leave_id;
    private int empId;
    private Date leave_from;
    private Date leave_to;
    private int no_ldays;
    private Date lapply_date;
    private EnumLeaveTypes leave_type;
    private String leave_reason;
    private EnumLeaveStatus l_status;

    //default constructor
    public Leave(){ }

    //parameterized constructor
    public Leave(final int l_Id,final int e_id,final Date l_from,final Date l_to,final int l_nd,final Date l_ad,final EnumLeaveStatus leaveStatus,final String l_rs,final EnumLeaveTypes leaveTypes) {
        this.leave_id = l_Id;
        this.empId = e_id;
        this.leave_from = l_from;
        this.leave_to = l_to;
        this.no_ldays = l_nd;
        this.lapply_date = l_ad;
        this.leave_type = leaveTypes;
        this.leave_reason = l_rs;
        this.l_status = leaveStatus;
      }

    //getters
    public final int getLeaveId(){
        return leave_id;
    }  
    public final int getEmployeeId(){
      return empId;
    }
    public final Date getLeaveFrom(){
        return leave_from;
    }  
    public final Date getLeaveTo(){
        return leave_to;
    }  
    public final int getNoLeaveDays(){
        return no_ldays;
    }  
    public final Date getLeaveApplyDate(){
        return lapply_date;
    }  
    public final EnumLeaveTypes getLeaveType(){
      return leave_type;
    }
    public final String getLeaveReason(){
      return leave_reason;
    }
    public final EnumLeaveStatus getLeaveStatus(){
        return l_status;
    }
   
    //setters

    public final void setLeaveId(final int L_id){
        this.leave_id = L_id;
    }  
    public final void setEmployeeId(final int e_id){
      this.empId = e_id;
    }  
    public final void setLeaveFrom(final Date L_from){
        this.leave_from = L_from;
    }  
    public final void setLeaveTo(final Date L_to){
        this.leave_to = L_to;
    }  
    public final void setNoLeaveDays(final int L_nodays){
        this.no_ldays = L_nodays;
    }  
    public final void setLeaveApplyDate(final Date L_applydate){
        this.lapply_date = L_applydate;
    }  
    public final void setLeaveType(final EnumLeaveTypes L_type){
      this.leave_type = L_type;
    }  
    public final void setLeaveReason(final String L_rs){
      this.leave_reason = L_rs;
    }  
    public final void setLeaveStatus(final EnumLeaveStatus L_status){
        this.l_status = L_status;
    }


      //method to apply for leave//
     
      public static String apply_for_leave(int empId,String start_date, String end_date, int no_ldays,String leave_type, String leave_reason)
      throws ParseException {
        String s=null;
        Employee e = Employee.listById(empId);
        if (e != null) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date leave_from = sdf.parse(start_date);
          Date leave_to = sdf.parse(end_date);
          Calendar start = Calendar.getInstance();
          start.setTime(leave_from);
          Calendar end = Calendar.getInstance();
          end.setTime(leave_to);
          int count = 0;
          for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
          Calendar c = Calendar.getInstance();
          c.setTime(date);
          int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
          if (dayOfWeek == 1 || dayOfWeek == 7) {
          count++;
        }
      }
      System.out.println(count);
      long diff = leave_to.getTime() - leave_from.getTime();
      System.out.println(diff);
      long days = diff / (1000 * 60 * 60 * 24);
      Date today = new Date();
      String appliedOn = sdf.format(today);
      days = days + 1;
      long availBal = 0;
      long dif = 0;
      long updLeave = 0;
      String leaveStatus = null;
     // int overl = Leave.countNo(empId, start_date, end_date);
      availBal = e.getavailleaves();
      dif = availBal - days;
      updLeave = days - count;
      int bal = (int) updLeave;
      if (days <= 0) {
        s="Start Date Must be Greater than EndDate...";
      } else if (dif < 0) {
        s="insufficient leav balance";
      } else if (no_ldays != days) {
        s="NO Of Days Should be right";
      } else if (leave_from.compareTo(sdf.parse(appliedOn)) < 0) {
        s="Start should be greater or equal to current date";
      // } else if (overl > 0) {
      //   s="already applied on given date";
      } else {
        if (e.getMgr_id() == 0) {
          leaveStatus = "APPROVED";
          dao().apply_for_leave(empId, start_date, end_date, bal, leave_type, leaveStatus, leave_reason, appliedOn);
          s="Leave Applied Successfully...";
        } else {
          leaveStatus = "PENDING";
          dao().apply_for_leave(empId, start_date, end_date, bal,
              leave_type, leaveStatus, leave_reason, appliedOn);
          edao().decrement(empId, bal);
          s = "Leave Applied Successfully For "  + (days - count) + " Days.";
        }
      }
    } else {
      s="invalid employee";
    }
    return s;
  }


  //method to update leave status//
 
  public static String update_leave(int empId,int leave_id,String start_date,String end_date,int no_ldays,String leave_type,String leave_reason )
  throws ParseException {
    Employee emp = Employee.listById(empId);
    Leave leavedetails = Leave.listById(leave_id);
    String s=null;
    int prevDays = leavedetails.getNoLeaveDays();
    if (emp != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date leave_from = sdf.parse(start_date);
      Date leave_to = sdf.parse(end_date);
      Calendar start = Calendar.getInstance();
      start.setTime(leave_from);
      Calendar end = Calendar.getInstance();
      end.setTime(leave_to);
      int counts = 0;
      for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1 || dayOfWeek == 7) {
          counts++;
}
      }
      System.out.println(counts);
      long diff1 = leave_to.getTime() - leave_from.getTime();
      System.out.println(diff1);
      long day1 = diff1 / (1000 * 60 * 60 * 24);
      Date today = new Date();
      String appliedOn = sdf.format(today);
      day1 = day1 + 1;
      long availBalance = 0;
      long diff = 0;
      long updLeave = 0;
      String leaveStatus = null;
     // int overl = Leave.countNo(empId,start_date,end_date);
      availBalance = emp.getavailleaves();
      diff = availBalance - day1;
      updLeave = day1 - counts;
      int bal = (int) updLeave;
      if (empId != Leave.listById(leave_id).getEmployeeId()) {
        s="You are not authorised to update this leave.";
      } else if (day1 <= 0) {
       s="Start Date Must be Greater than EndDate...";
      } else if (diff < 0) {
        s="insufficient leave balance";
      } else if (no_ldays != day1) {
        s="NO Of day Should be right";
      } else if (leave_from.compareTo(sdf.parse(appliedOn)) < 0) {
        s="Start should be greater or equal to current date";
      } else {
        if (emp.getMgr_id() == 0) {
          leaveStatus = "APPROVED";
          dao().update_leave(start_date, end_date, bal,
              leave_type, leaveStatus, leave_reason, leave_id);
              s="Leave updated Successfully...";
        } else {
          leaveStatus = "PENDING";
          dao().update_leave(start_date, end_date, bal,
          leave_type, leaveStatus, leave_reason, leave_id);
          if (bal - prevDays > 0) {
            bal = bal - prevDays;
            edao().decrement(empId, bal);
          } else if (bal - prevDays < 0) {
            bal = prevDays - bal;
            edao().increment(empId, bal);
          } else {
            bal = bal - prevDays;
            edao().decrement(empId, bal);
          }
          System.out.println("Leave updated Successfully For "  + (day1 - counts) + " day.");
        }
      }
    } else {
      s="invalid employee";
    }
    return s;
  }

  //method to approve or deny leave//
 
  public static String approveDeny(int leave_id,int empId,EnumLeaveStatus l_status) {
   Leave ld = Leave.listById(leave_id);
   String s=null;
   if (ld != null) {
     int noOfDays = ld.getNoLeaveDays();
     int emplId = ld.getEmployeeId();
     int empdId = Leave.showManager(leave_id);
     String dbStatus = null;
     System.out.println(l_status);
     System.out.println(ld.getLeaveStatus());
     if (empId != empdId) {
       s="You are not authorised to access this employee.";
       return s;
     }
     if (l_status.equals("approved") && ld.getLeaveStatus().equals(l_status.pending)) {
       dbStatus = "approved";
       edao().decrement(emplId, noOfDays);
      dao().comment( dbStatus, leave_id);
       s="Leave Approved Successfully";
       
     } else if (l_status.equals("denied") && ld.getLeaveStatus().equals(l_status.approved)) {
       dbStatus = "denied";
       edao().increment(emplId, noOfDays);
       dao().comment( dbStatus, leave_id);
       s="Leave Rejected";
     } else if (l_status.equals("approved") && ld.getLeaveStatus().equals(l_status.denied)) {
       dbStatus = "approved";
       edao().decrement(emplId, noOfDays);
       dao().comment(dbStatus, leave_id);
       s="Leave Approved Successfully";
     } else {
       if (l_status.equals("denied") && ld.getLeaveStatus().equals(l_status.pending)) {
         dbStatus = "denied";
         edao().increment(emplId, noOfDays);
        dao().comment( dbStatus, leave_id);
         s="Leave Rejected";
       } else {
         s="Already on given status";
       }
     }
   } else {
     s="Invalid LeaveId";
   }
   return s;
  }






  



  





  //    Equals      //

@Override

    public final boolean equals(final Object obj) {
        if (obj == null) {
          return false;
        }
        if (getClass() != obj.getClass()) {
          return false;
        }
        Leave l = (Leave) obj;

        if (Objects.equals(leave_id, l.leave_id) && Objects.equals(empId, l.empId) && Objects.equals(leave_from,l.leave_from) && Objects.equals(leave_to,l.leave_to) && Objects.equals(no_ldays,l.no_ldays) && Objects.equals(lapply_date,l.lapply_date) && Objects.equals(leave_type, l.leave_type) && Objects.equals(leave_reason, l.leave_reason) && Objects.equals(l_status,l.l_status)) {

          return true;
        }
        return false;
      }

// hashcode Overide//
      @Override
  public final int hashCode() {
    return Objects.hash(leave_id, empId, leave_from, leave_to, no_ldays, leave_type, l_status,leave_reason, lapply_date);
  }


    //toString Override//
  @Override
  public String toString() {
   
    return "Leave Id: " + leave_id + "Employee ID: " +empId+  "Leave From: " + leave_from + "Leave to: " + leave_to + "No. of Leave Days: " + no_ldays + "Leave Apply date: " + lapply_date + "Leave type: " + leave_type + "Leave Reason: " + leave_reason + "Leave Status " + l_status;
  }

  //DAO for Leave
  private static LeaveDAO dao() {
    DbConnection db =new DbConnection();
    return db.getConnect().onDemand(LeaveDAO.class);
  }
 
 
  //DAO for employee
    private static EmployeeDAO edao() {
    DbConnection db = new DbConnection();
    return db.getConnect().onDemand(EmployeeDAO.class);
  }

  /**
   * list all leave details.
   */
  public static Leave[] listAll(){
    List<Leave> es=dao().list();
    return es.toArray(new Leave[es.size()]);
  }
  /**
   * list all employee leave details.
   * @param mgrId id to get employee leave Details.
   * @return all employee leave details
   */
  public static Leave[] listPending(final int mgrId) {

    List<Leave> e = dao().pending(mgrId);
    return e.toArray(new Leave[e.size()]);
  }

  /**
   * list employee details by id.
   * @param leaveId id to get employee details.
   * @return Employee
   */
  public static Leave listById(final int leave_id) {
    return dao().list_EmployeesID(leave_id);
  }
 
  /**
  * returns ManagerId for. LeaveId value
  * @param leaveId id to get employee manager details.
  * @return int.
  */



  public static int showManager(final int leaveId) {
    return dao().showManager(leaveId);
  }
 
  /**
Leave History
   */
  public static Leave[] leaveHis(final int empId){
    List<Leave> es = dao().empHistory(empId);
    return es.toArray(new Leave[es.size()]);
  }

  // public static int countNo(final int empId, final String start_date, final String end_date) {
  //   int count = dao().count(empId, start_date, end_date);
  //   return count;
  // }

public static String approve_denyLeave(int leaveId, int mgrId, String ch) {
	return null;
}

public int getlapply_date() {
	return 0;
}

 
}








