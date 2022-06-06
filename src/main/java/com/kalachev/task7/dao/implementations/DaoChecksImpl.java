package com.kalachev.task7.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kalachev.task7.dao.interfaces.DaoChecks;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.ExceptionsUtil;
import com.kalachev.task7.utilities.JdbcUtil;

public class DaoChecksImpl implements DaoChecks {

  private static final String CHECK_IF_STUDENT_IN_COURSE = "SELECT * "
      + "FROM studentscoursesdata " + "WHERE student_id ="
      + " (?) AND course_name = (?)";

  @Override
  public boolean checkIfStudentInCourse(int studentId, String course)
      throws DaoException {
    boolean isExists = false;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnection();
      statement = connection.prepareStatement(CHECK_IF_STUDENT_IN_COURSE);
      statement.setInt(1, studentId);
      statement.setString(2, course);
      rs = statement.executeQuery();
      if (rs.next()) {
        isExists = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }

    return isExists;
  }

}
