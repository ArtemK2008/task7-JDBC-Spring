package com.kalachev.task7.dao.initialization.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.ExceptionsUtil;
import com.kalachev.task7.utilities.JdbcUtil;

public class CoursesDataDbPopulator {

  private static final String INSERT_COURSES = "INSERT INTO Courses (course_name,course_description) VALUES (?,?)";

  public void populateCourses(Map<String, String> courses) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.prepareStatement(INSERT_COURSES);
      connection.setAutoCommit(false);
      for (Entry<String, String> entry : courses.entrySet()) {
        statement.setString(1, entry.getKey());
        statement.setString(2, entry.getValue());
        statement.addBatch();
      }
      statement.executeBatch();
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(statement, connection);
    }
  }

}
