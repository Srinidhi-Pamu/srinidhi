package com.hexaware.MLP203.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;

import com.hexaware.MLP203.model.Leave;
/**
 * Mapper class to map from result set to leave object.
 */
public class LeaveMapper implements ResultSetMapper<Leave> {

    public final Leave map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Leave(r.getInt("leave_id"),r.getDate("leave_from"),r.getDate("leave_to"),r.getInt("no_ldays"),r.getDate("lapply_date"),r.getString("l_status"),r.getString("leave_type"),r.getString("leave_reason"));
    }
}
