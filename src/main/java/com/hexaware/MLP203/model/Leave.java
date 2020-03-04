package com.hexaware.MLP203.model;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

import com.hexaware.MLP203.persistence.DbConnection;
import com.hexaware.MLP203.persistence.LeaveDAO;

public class Leave{
     private int leave_id;
     private Date leave_from;
     private Date leave_to;
     private int no_ldays;
     private Date lapply_date;
     private String l_status;
     private String leave_reason;
     private String leave_type;

    
    //default constructor
    public Leave(){}

    //parameterized constructor
    public Leave(final int l_id, final Date l_from, final Date l_to, final int no_ldays, final Date lapply_date, final String l_status, final String l_reason, final String l_type){
        this.leave_id=l_id;
        this.leave_from=l_from;
        this.leave_to=l_to;
        this.no_ldays=no_ldays;
        this.lapply_date=lapply_date;
        this.l_status=l_status;
        this.leave_reason=l_reason;
        this.leave_type=l_type;
    }

    //getters

    public final int getleave_id() {
        return leave_id;
    }

    public final Date getleave_from() {
        return leave_from;
    }  

    public final Date getleave_to() {
        return leave_to;
    }

    public final int getno_ldays(){
        return no_ldays;
    }

    public final Date getlapply_date() {
        return lapply_date;
    }

    public final String getl_status(){
        return l_status;
    }

    public final String getl_reason(){
        return leave_reason;
    }

    public final String getl_type(){
        return leave_type;
    }

    //setters

    public final void setleave_id(int l_id){
        this.leave_id=l_id;
    }

    public final void setleave_from(Date l_from) {
        this.leave_from=l_from;
    }

    public final void setleave_to(Date l_to) {
        this.leave_to=l_to;
    }

    public final void setno_days(int no_days){
        this.no_ldays=no_days;
    }

    public final void setlapply_date(Date lapply_date)
    {
        this.lapply_date=lapply_date;
    }

    public final void setl_status(String l_status){
        this.l_status=l_status;
    }

    public final void setl_reason(String l_reason){
        this.leave_reason=l_reason;
    }
     
    public final void setl_type(String l_type){
        this.leave_type=l_type;
    }
    

    //equals
    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(getClass() !=obj.getClass()){
            return false;
        }
        Leave l = (Leave) obj;
        if(Objects.equals(leave_id, l.leave_id) && Objects.equals(leave_from, l.leave_from) && Objects.equals(leave_to, l.leave_to) && Objects.equals(no_ldays, l.no_ldays) && Objects.equals(lapply_date, l.lapply_date) && Objects.equals(l_status, l.l_status)){
            return true;
        }
        return false;
    }

    //toString

    @Override
    public String toString() {
        return "leaveid: " + leave_id + "from date: " + leave_from + "to date: " + leave_to + "no of leave days: " + no_ldays + "leave apply date: " + lapply_date + "leave status: " + l_status;
    }

    //Dao for leave
    private static LeaveDAO dao(){
        DbConnection db=new DbConnection();
        return db.getConnect().onDemand(LeaveDAO.class);
    }

    public static Leave listById(final int leave_id){
       return dao().list_EmployeesID(leave_id);
    }
    
    public static Leave[] leaveHistory(final int empId){
        List<LeaveDAO> es = dao().leaveHistory(empId);
        return es.toArray(new Leave[es.size()]);
    }

}
