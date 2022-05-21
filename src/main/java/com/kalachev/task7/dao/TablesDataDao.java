package com.kalachev.task7.dao;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TablesDataDao {

  private static final String INSERT_GROUPS = 
      "INSERT INTO Groups (group_name) VALUES (?)";
  private static final String INSERT_COURSES = 
      "INSERT INTO Courses (course_name,course_description) VALUES (?,?)";

  private static final String URL = "jdbc:postgresql://localhost/comkalachevtasksqljdbc";
  private static final String USERNAME = "kalachevartemsql";
  private static final String PASSWORD = "1234";

  public void createTables() throws DaoException {
    Connection connection = null;
    Statement statement = null;
    try {
      java.net.URL url = TablesDataDao.class.getClassLoader()
          .getResource("StartupSqlData.sql");
      List<String> tableData = Files.readAllLines(Paths.get(url.toURI()));
      String sql = tableData.stream().collect(Collectors.joining());
      connection = getDbConnection();
      statement = connection.createStatement();
      statement.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      closeAll(statement, connection);
    }
  }

  public void populateGroups(List<String> groups) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = getDbConnection();
      statement = connection.prepareStatement(INSERT_GROUPS);
      connection.setAutoCommit(false);
      for (String group : groups) {
        statement.setString(1, group);
        statement.addBatch();
      }
      statement.executeBatch();
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      closeAll(statement, connection);
    }
  }

  public void pupulateStudents(List<String> students,
      Map<String, List<String>> groupsWithItsStudents) throws DaoException {

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      connection = getDbConnection();
      statement = connection.createStatement();
      for (String student : students) {
        String curGroup = findGroupByStudent(student, groupsWithItsStudents);
        String sql = "SELECT(group_id) FROM Groups WHERE group_name = '"
            + curGroup + "'";
        rs = statement.executeQuery(sql);
        String groupId = null;
        if (rs.next()) {
          groupId = rs.getString(1);
        }
        String insertStudentsSql = "INSERT INTO Students ("
            + "group_id,first_name, last_name) "
            + "VALUES (" + "'" + groupId + "','"
            + retrieveFirstName(student) + ("','") + retrieveLastName(student)
            + "')";
        statement.executeUpdate(insertStudentsSql);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      closeAll(rs, statement, connection);
    }
  }

  public void populateCourses(Map<String, String> courses) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = getDbConnection();
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
      e.printStackTrace();
      throw new DaoException();
    } finally {
      closeAll(statement, connection);
    }
  }

  public void createManyToManyTable(Map<String, List<String>> coursesOfStudent)
      throws DaoException {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      connection = getDbConnection();
      statement = connection.createStatement();
      for (Entry<String, List<String>> entry : coursesOfStudent.entrySet()) {
        List<String> courses = entry.getValue();
        for (String course : courses) {
          String findIdSql = 
              "SELECT(course_id) FROM Courses "
              + "WHERE course_name = '"
              + course + "'";
          rs = statement.executeQuery(findIdSql);
          String courseId = null;
          if (rs.next()) {
            courseId = rs.getString(1);
          }
          String insertStudentsCourses = 
              "INSERT INTO Students_Courses (student_id, course_id) "
              + "VALUES ('" + entry.getKey() + "','" + courseId + "')";
          statement.executeUpdate(insertStudentsCourses);
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      closeAll(rs, statement, connection);
    }
  }

  public void createStudentsCoursesFullTable() throws DaoException {
    Connection connection = null;
    Statement statement = null;
    try {
      connection = getDbConnection();
      statement = connection.createStatement();
      String sql = 
          "CREATE TABLE StudentsCoursesData" + " AS "
          + "( SELECT s.student_id, s.first_name, s.last_name ,c.course_name,c.course_description "
          + "FROM Students as s" + " INNER JOIN Students_courses as sc"
          + " ON s.student_id = sc.student_id " + "INNER JOIN Courses as c "
          + "ON sc.course_id = c.course_id " + "ORDER BY s.student_id)";
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      closeAll(statement, connection);
    }
  }

  public Map<String, String> retrieveStudentsId() throws DaoException {
    Map<String, String> idsOfEachStuden = new LinkedHashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      connection = getDbConnection();
      statement = connection.createStatement();
      rs = statement.executeQuery(
          "SELECT student_id,first_name,last_name  FROM Students");
      while (rs.next()) {
        idsOfEachStuden.put(rs.getString("student_id"),
            rs.getString("first_name") + " " + rs.getString("last_name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      closeAll(rs, statement, connection);
    }
    return idsOfEachStuden;
  }

  private String findGroupByStudent(String student,
      Map<String, List<String>> groupsWithItsStudents) throws DaoException {
    String groupOfStudent = "";
    for (Entry<String, List<String>> entry : groupsWithItsStudents.entrySet()) {
      if (entry.getValue().contains(student)) {
        groupOfStudent = entry.getKey();
      }
    }
    if (groupOfStudent.equals("")) {
      throw new DaoException();
    }
    return groupOfStudent;
  }

  private String retrieveFirstName(String student) {
    int spaceIndex = student.indexOf(' ');
    return student.substring(0, spaceIndex);
  }

  private String retrieveLastName(String student) {
    int spaceIndex = student.indexOf(' ');
    return student.substring(spaceIndex + 1);
  }

  private void closeAll(ResultSet rs, Statement statement,
      Connection connection) throws DaoException {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new DaoException();
      }
    }
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new DaoException();
      }
    }
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new DaoException();
      }
    }
  }

  private void closeAll(Statement statement, Connection connection)
      throws DaoException {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new DaoException();
      }
    }
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new DaoException();
      }
    }
  }

  private static Connection getDbConnection() throws DaoException {
    Connection connection = null;
    try {
      DriverManager.registerDriver(new org.postgresql.Driver());
      connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      return connection;
    } catch (Exception e) {
      e.printStackTrace();
      throw new DaoException();
    }
  }
}
