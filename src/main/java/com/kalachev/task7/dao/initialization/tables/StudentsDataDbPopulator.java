package com.kalachev.task7.dao.initialization.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.ExceptionsUtil;
import com.kalachev.task7.utilities.JdbcUtil;

public class StudentsDataDbPopulator {

  public void populateStudents(Map<String, List<String>> groupsWithItsStudents)
      throws DaoException {

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.createStatement();
      for (Entry<String, List<String>> entry : groupsWithItsStudents
          .entrySet()) {
        String sql = "SELECT(group_id) FROM Groups WHERE group_name = '"
            + entry.getKey() + "'";
        rs = statement.executeQuery(sql);
        String groupId = null;
        if (rs.next()) {
          groupId = rs.getString(1);
        }
        for (String student : entry.getValue()) {
          String insertStudentsSql = "INSERT INTO Students ("
              + "group_id,first_name, last_name) " + "VALUES (" + "'" + groupId
              + "','" + retrieveFirstName(student) + ("','")
              + retrieveLastName(student) + "')";
          statement.executeUpdate(insertStudentsSql);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
  }

  private String retrieveFirstName(String student) {
    int spaceIndex = student.indexOf(' ');
    return student.substring(0, spaceIndex);
  }

  private String retrieveLastName(String student) {
    int spaceIndex = student.indexOf(' ');
    return student.substring(spaceIndex + 1);
  }
}
