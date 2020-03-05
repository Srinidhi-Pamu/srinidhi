package com.hexaware.MLP203.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;

import com.hexaware.MLP203.model.Employee;
/**
 * Mapper class to map from result set to employee object.
 */
public class EmployeeMapper implements ResultSetMapper<Employee> {
  /**
   * @param idx the index
   * @param rs the resultset
   * @param ctx the context
   * @return the maspped employee object
   * @throws SQLException in case there is an error in fetching data from the resultset
   */
  public final Employee map(final int idx, final ResultSet rs, final StatementContext ctx) throws SQLException {
    /**
     * @return Employee
     */

    return new Employee(rs.getInt("empId"), rs.getInt("mgr_id"), rs.getString("fname"), rs.getString("lname"), rs.getString("dept"),
        rs.getString("mail"), rs.getInt("contactno"), rs.getInt("avail_leaves"), rs.getDate("doj"));

  }
}