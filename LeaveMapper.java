package com.hexaware.MLP203.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;

import com.hexaware.MLP203.model.EnumLeaveStatus;
import com.hexaware.MLP203.model.EnumLeaveTypes;
import com.hexaware.MLP203.model.Leave;
/**
 * Mapper class to map from result set to leave object.
 */
public class LeaveMapper implements ResultSetMapper<Leave>
 {
 
    public final Leave map(int index, ResultSet r, StatementContext ctx) throws SQLException 
    {
        String leave_type = r.getString("leave_type");
        String l_status = r.getString("leave_status");
        EnumLeaveTypes LeaveTypes = EnumLeaveTypes.valueOf(leave_type);
        EnumLeaveStatus LeaveStatus = EnumLeaveStatus.valueOf(l_status);

        return new Leave(r.getInt("leave_id"),r.getInt("empId"),r.getDate("leave_from"),r.getDate("leave_to"),r.getInt("no_leavedays"),r.getDate("leave_applydate"),LeaveStatus,r.getString("leave_reason"),LeaveTypes);
    }
}
