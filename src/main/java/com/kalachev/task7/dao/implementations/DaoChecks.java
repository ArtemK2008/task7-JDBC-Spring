package com.kalachev.task7.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.ExceptionsUtil;
import com.kalachev.task7.utilities.JdbcUtil;

public class DaoChecks {

  private static final String CHECK_COURSE_IF_EXISTS = "SELECT course_name "
      + "FROM studentscoursesdata " + "WHERE course_name = (?)";

  private static final String CHECK_STUDENT_IF_EXISTS_IN_GROUP = "SELECT * FROM Students "
      + "WHERE first_name = (?) AND last_name = (?) AND group_id = (?)";

  private static final String CHECK_STUDENT_ID_IF_EXISTS = "SELECT * FROM Students "
      + "WHERE student_id = (?)";

  private static final String CHECK_IF_STUDENT_IN_COURSE = "SELECT * "
      + "FROM studentscoursesdata " + "WHERE student_id ="
      + " (?) AND course_name = (?)";

  public boolean checkCourseIfExists(String course) throws DaoException {
    boolean isExist = false;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.prepareStatement(CHECK_COURSE_IF_EXISTS);
      statement.setString(1, course);
      rs = statement.executeQuery();
      if (rs.next()) {
        isExist = true;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return isExist;
  }

  public boolean checkStudntIfExistsInGroup(String firstName, String lastName,
      int groupId) throws DaoException {
    boolean isExists = false;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.prepareStatement(CHECK_STUDENT_IF_EXISTS_IN_GROUP);
      statement.setString(1, firstName);
      statement.setString(2, lastName);
      statement.setInt(3, groupId);
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

  public boolean checkStudentIdIfExists(int id) throws DaoException {
    boolean isExists = false;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.prepareStatement(CHECK_STUDENT_ID_IF_EXISTS);
      statement.setInt(1, id);
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

  public boolean checkIfStudentInCourse(int studentId, String course)
      throws DaoException {
    boolean isExists = false;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
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
